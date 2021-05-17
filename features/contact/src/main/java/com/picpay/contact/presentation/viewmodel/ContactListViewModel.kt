package com.picpay.contact.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.picpay.contact.domain.model.ContactModel
import com.picpay.contact.presentation.viewState.ContactListViewState

/**
 * Abstract class inherited from [ViewModel] responsible to communicate with the use cases and
 * manage the UI related data
 */
abstract class ContactListViewModel : ViewModel() {

    abstract val list : LiveData<List<ContactModel>>
    abstract val stateList : LiveData<ContactListViewState>
    abstract val errorMessage : LiveData<String>
    abstract fun fetchContacts(forceRefresh : Boolean)

}