package com.picpay.contact.data.repository

import android.content.SharedPreferences
import com.google.common.truth.Truth
import com.picpay.contact.data.database.dao.ContactDao
import com.picpay.contact.data.database.entity.ContactEntity
import com.picpay.contact.data.database.mapper.toEntity
import com.picpay.contact.data.model.toDbEntity
import com.picpay.contact.data.remote.ContactApi
import com.picpay.contact.data.remote.model.dto.ContactDto
import com.picpay.contact.data.remote.model.response.ContactListResponse
import com.picpay.contact.domain.usecase.GetContactListUseCaseTest
import com.picpay.contact.repository.ContactRepositoryConstants
import com.picpay.contact.repository.ContactRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import java.util.*


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
internal class ContactRepositoryTest {

    lateinit var repository: ContactRepositoryImpl
    @Mock
    lateinit var api: ContactApi

    @Mock
    lateinit var preferences: SharedPreferences

    @Mock
    lateinit var dao: ContactDao

    @Mock
    lateinit var httpException: HttpException

    lateinit var getContactListUseCase: GetContactListUseCaseTest

    lateinit var contactListDto: List<ContactDto>
    lateinit var contactListEntity: List<ContactEntity>
    lateinit var apiSuccessResponse: List<ContactListResponse>

    @Before
    fun setup() {

        repository = ContactRepositoryImpl(
            dao,
            api,
            preferences
        )
        contactListDto = listOf(ContactDto(
            username = "Teste",
            img = "Teste",
            name = "Teste",
            id = 0,
        ))

        contactListEntity = contactListDto.toEntity()

        apiSuccessResponse = listOf(
            ContactListResponse(
                name = "teste",
                img = "teste",
                username = "teste",
                id = 0,
            )
        )

    }
    @Test
    fun `given get contact list when no have cache then get from api`() = runBlockingTest {
        `when`(
            dao.getNumOfRows()
        ).thenAnswer { 0 }

        `when`(
            dao.getAll()
        ).thenAnswer { contactListEntity }

        `when`(
            dao.insertList(contactListEntity)
        ).thenAnswer { doNothing() }

        `when`(
            api.getContacts()
        ).thenAnswer { apiSuccessResponse }

        repository.getContacts(false)

        verify(dao, times(1)).getAll()
        verify(dao, times(1)).insertList(apiSuccessResponse.toDbEntity())
    }

    @Test
    fun `given get contact list when have cache then get from db`() = runBlockingTest {
        `when`(
            dao.getNumOfRows()
        ).thenAnswer { 1 }

        `when`(
            dao.getAll()
        ).thenAnswer { contactListEntity }

        val availableTime = Calendar.getInstance().timeInMillis.plus(200000)
        `when`(
            preferences.getLong(ContactRepositoryConstants.CONTACT_LIST_LAST_UPDATED_TIME,0L)
        ).thenAnswer {  availableTime }

        repository.getContacts(false)

        verify(dao, times(1)).getAll()
        verify(api, never()).getContacts()
    }

    @Test
    fun `given get contact list when have cache but time was expired then get from api`() = runBlockingTest {
        `when`(
            dao.getNumOfRows()
        ).thenAnswer { 1 }

        `when`(
            dao.getAll()
        ).thenAnswer { contactListEntity }

        `when`(
            api.getContacts()
        ).thenAnswer { apiSuccessResponse }

        val expiredTime = (5 * 1000 * 60 * 60).toLong()

        `when`(
            preferences.getLong(ContactRepositoryConstants.CONTACT_LIST_LAST_UPDATED_TIME,0L)
        ).thenAnswer { expiredTime }

        repository.getContacts(false)

        verify(dao, times(1)).getAll()
        verify(dao, times(1)).insertList(apiSuccessResponse.toDbEntity())
        verify(api, times(1)).getContacts()
    }

    @Test
    fun `given get contact list when have cache, time was ok but force refresh is true then get from api`() = runBlockingTest {
        `when`(
            dao.getNumOfRows()
        ).thenAnswer { 1 }

        `when`(
            dao.getAll()
        ).thenAnswer { contactListEntity }

        `when`(
            api.getContacts()
        ).thenAnswer { apiSuccessResponse }

        val availableTime = Calendar.getInstance().timeInMillis.plus(200000)

        `when`(
            preferences.getLong(ContactRepositoryConstants.CONTACT_LIST_LAST_UPDATED_TIME,0L)
        ).thenAnswer { availableTime }

        repository.getContacts(true)

        verify(dao, times(1)).getAll()
        verify(dao, times(1)).insertList(apiSuccessResponse.toDbEntity())
        verify(api, times(1)).getContacts()
    }

    @Test(expected = Throwable::class)
    fun `given get contact list when http exception then returns an throwable`() = runBlockingTest {

        `when`(
            api.getContacts()
        )
            .thenThrow(httpException)

        repository.getContacts(false)
    }

    @Test
    fun `given get contact list when success then returns ContactDto list`() = runBlockingTest {

        `when`(
            api.getContacts()
        )
            .thenAnswer {
                apiSuccessResponse
            }
        
        `when`(
            dao.getAll()
        ).thenAnswer { contactListEntity }


        val dto = repository.getContacts(true)

        Truth.assertThat(dto[0].id).isEqualTo(apiSuccessResponse[0].id)
    }

}