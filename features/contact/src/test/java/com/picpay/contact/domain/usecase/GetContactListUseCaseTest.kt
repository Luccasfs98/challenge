package com.picpay.contact.domain.usecase

import br.com.cea.account.MainCoroutineRule
import com.google.common.truth.ExpectFailure.assertThat
import com.google.common.truth.Truth
import com.picpay.contact.data.remote.model.dto.ContactDto
import com.picpay.contact.data.remote.model.response.ContactListResponse
import com.picpay.contact.repository.ContactRepository
import com.picpay.core.data.exceptions.EmptyDataException
import com.picpay.core.utils.Loading
import com.picpay.core.utils.Success
import junit.framework.Assert.assertEquals
import junit.framework.Assert.fail
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class GetContactListUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val coroutineDispatcher = TestCoroutineDispatcher()

    @Mock
    internal lateinit var repository : ContactRepository

    private lateinit var useCase : GetContactListUseCase
    lateinit var repositorySuccessResponse : List<ContactDto>

    @Before
    fun setup () {
        useCase = GetContactListUseCaseImpl(repository)

        repositorySuccessResponse = listOf(
            ContactDto(
                img = "teste",
                name = "teste",
                username = "teste",
                id = 0,
            )
        )
    }

    @Test
    internal fun `given get contact list when process started then emits a loading resource` () = coroutineDispatcher.runBlockingTest {
        useCase.invoke(false).collect {
            when (it){
                is Loading -> return@collect
            }
        }
    }

    @Test
    fun `given get contact list when process successfully finished then emits a success resource` () = coroutineDispatcher.runBlockingTest {
        `when`(repository.getContacts(true)).thenAnswer { repositorySuccessResponse }

        useCase.invoke(true).collect {
            when (it){
                is Success -> {
                    assertEquals(it.data.first().id, repositorySuccessResponse.first().id)
                }
            }
        }
    }

    @Test()
    fun `given get contact list when process returns a throwable then emits a error resource`() = coroutineDispatcher.runBlockingTest {

        val response = Error(Exception())

        `when`(repository.getContacts(true))?.thenAnswer {
            response
        }

        useCase.invoke(true).collect {
            when(it){
                is Error -> {
                    assertEquals(it.cause, response.cause)
                }
            }
        }
    }

}