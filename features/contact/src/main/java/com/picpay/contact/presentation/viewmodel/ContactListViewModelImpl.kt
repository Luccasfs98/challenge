package com.picpay.contact.presentation.viewmodel

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.picpay.contact.R
import com.picpay.core.data.exceptions.*
import com.picpay.contact.domain.model.ContactModel
import com.picpay.contact.domain.usecase.GetContactListUseCase
import com.picpay.contact.presentation.viewState.ContactListViewState
import com.picpay.core.utils.Error
import com.picpay.core.utils.Loading
import com.picpay.core.utils.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

/**
 * Implementation of [ContactListViewModel]
 */
internal class ContactListViewModelImpl @Inject constructor(
    private val getContactListUseCase: GetContactListUseCase,
    @Named("IO") private val ioDispatcher: CoroutineDispatcher,
    private val resources : Resources
    ) : ContactListViewModel() {

    private val _list = MutableLiveData<List<ContactModel>>()
    override val list: LiveData<List<ContactModel>>
        get() = _list

    private val _state = MutableLiveData<ContactListViewState>()
    override val stateList: LiveData<ContactListViewState>
        get() = _state

    private val _errorMessage = MutableLiveData<String>()
    override val errorMessage: LiveData<String>
        get() = _errorMessage

    override fun fetchContacts(forceRefresh : Boolean) {

        viewModelScope.launch {
            getContactListUseCase.invoke(forceRefresh).flowOn(ioDispatcher).collect {
                when(it){
                    is Loading -> {
                       _state.postValue(ContactListViewState.Loading)
                    }
                    is Success -> {
                        _list.postValue(it.data)
                        _state.postValue(ContactListViewState.Loaded)
                    }
                    is Error -> {
                        when(it.exception){
                            is EmptyDataException -> {
                                _state.postValue(ContactListViewState.Empty)
                            }
                            is InternetNotAvailableException -> {
                                _errorMessage.postValue(resources.getString(R.string.error_internet_not_available))
                                _state.postValue(ContactListViewState.Error)
                            }
                            is BadRequestException -> {
                                _errorMessage.postValue(it.exception?.message.orEmpty())
                                _state.postValue(ContactListViewState.Error)
                            }
                            is NotFoundException -> {
                                _errorMessage.postValue(resources.getString(R.string.not_found_error))
                                _state.postValue(ContactListViewState.Error)
                            }
                            is UnauthorizedException -> {
                                _errorMessage.postValue(resources.getString(R.string.unauthorized_error))
                                _state.postValue(ContactListViewState.Error)
                            }
                            is ServiceUnavailableException -> {
                                _errorMessage.postValue(resources.getString(R.string.service_unavailable_error))
                                _state.postValue(ContactListViewState.Error)
                            }
                            else -> {
                                _errorMessage.postValue(resources.getString(R.string.unknown_error))
                                _state.postValue(ContactListViewState.Error)
                            }
                        }
                    }
                }
            }
        }
    }
}