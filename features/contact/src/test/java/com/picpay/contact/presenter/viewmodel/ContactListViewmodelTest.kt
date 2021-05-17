package com.picpay.contact.presenter.viewmodel

import android.content.res.Resources
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.cea.account.MainCoroutineRule
import br.com.cea.account.getOrAwaitValueTest
import com.google.common.truth.Truth
import com.picpay.contact.R
import com.picpay.contact.domain.model.ContactModel
import com.picpay.contact.domain.usecase.GetContactListUseCase
import com.picpay.contact.presentation.viewState.ContactListViewState
import com.picpay.contact.presentation.viewmodel.ContactListViewModel
import com.picpay.contact.presentation.viewmodel.ContactListViewModelImpl
import com.picpay.core.data.exceptions.EmptyDataException
import com.picpay.core.utils.Loading
import com.picpay.core.utils.Success
import com.picpay.core.utils.Error
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ContactListViewmodelTest {

    private lateinit var errorFlow: Flow<Error>
    private lateinit var errorEmptyFlow: Flow<Error>
    private lateinit var successFlow: Flow<Success<List<ContactModel>>>
    private lateinit var loadingFlow: Flow<Loading<Any>>

    @get:Rule
    var instantTaskRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ContactListViewModel
    private lateinit var contactList : List<ContactModel>

    @Mock
    private lateinit var getContactListUseCase: GetContactListUseCase

    @Mock
    private lateinit var resources: Resources

    private val coroutineDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup(){
        viewModel = ContactListViewModelImpl(
            getContactListUseCase,
            coroutineDispatcher,
            resources)

        loadingFlow = flow {
            emit(Loading())
        }

        contactList = listOf(
            ContactModel(
                id = 0L,
                name = "MOCK NAME",
                username = "MOCK USERNAME",
                imgUrl = "MOCK URL"
            )
        )
        successFlow = flow {
            emit(Success(contactList))
        }

        errorFlow = flow {
            emit(Error(Exception()))
        }
        errorEmptyFlow = flow {
            emit(Error(EmptyDataException()))
        }

        Mockito.`when`(resources.getString(R.string.unknown_error))
            .thenReturn("test")
    }

    @After
    fun tearDown() {
        coroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `given get contact list when start then post Loading in stateList live data` () = coroutineDispatcher.runBlockingTest {
        Mockito.`when`(getContactListUseCase.invoke(true)).thenAnswer {
            loadingFlow
        }
        viewModel.fetchContacts(true)
        val value = viewModel.stateList.getOrAwaitValueTest()
        Truth.assertThat(value).isEqualTo(ContactListViewState.Loading)
    }

    @Test
    fun `given get contact list when success then post Loaded in stateList live data` () = coroutineDispatcher.runBlockingTest {
        Mockito.`when`(getContactListUseCase.invoke(true)).thenAnswer {
            successFlow
        }
        viewModel.fetchContacts(true)
        val value = viewModel.stateList.getOrAwaitValueTest()
        Truth.assertThat(value).isEqualTo(ContactListViewState.Loaded)
    }
    @Test
    fun `given get contact list when success then post Data in list live data` () = coroutineDispatcher.runBlockingTest {
        Mockito.`when`(getContactListUseCase.invoke(true)).thenAnswer {
            successFlow
        }
        viewModel.fetchContacts(true)
        val value = viewModel.list.getOrAwaitValueTest()
        Truth.assertThat(value.lastOrNull()?.id).isEqualTo(contactList.lastOrNull()?.id)
    }
    @Test
    fun `given get contact list when error then post Error in stateList live data` () = coroutineDispatcher.runBlockingTest {
        Mockito.`when`(getContactListUseCase.invoke(true)).thenAnswer {
            errorFlow
        }
        viewModel.fetchContacts(true)
        val value = viewModel.stateList.getOrAwaitValueTest()
        Truth.assertThat(value).isEqualTo(ContactListViewState.Error)
    }
    @Test
    fun `given get contact list when error is EmptyDataException then post Empty in stateList live data` () = coroutineDispatcher.runBlockingTest {
        Mockito.`when`(getContactListUseCase.invoke(true)).thenAnswer {
            errorEmptyFlow
        }
        viewModel.fetchContacts(true)
        val value = viewModel.stateList.getOrAwaitValueTest()
        Truth.assertThat(value).isEqualTo(ContactListViewState.Empty)
    }
    @Test
    fun `given get contact list when error  then post error message in errorMessage live data` () = coroutineDispatcher.runBlockingTest {
        Mockito.`when`(getContactListUseCase.invoke(true)).thenAnswer {
            errorFlow
        }
        viewModel.fetchContacts(true)
        val value = viewModel.errorMessage.getOrAwaitValueTest()
        Truth.assertThat(value).isNotEmpty()
    }

}