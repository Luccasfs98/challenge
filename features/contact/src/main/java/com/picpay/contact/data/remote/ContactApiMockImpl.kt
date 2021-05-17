package com.picpay.contact.data.remote

import com.picpay.contact.data.remote.model.response.ContactListResponse
import com.picpay.contact.domain.model.ContactModel
import kotlinx.coroutines.delay
import retrofit2.Response

/**
 * A mock implementation of [ContactApi] to help with development tasks
 */
internal class ContactApiMockImpl : ContactApi {
    override suspend fun getContacts() : Response<List<ContactListResponse>> {
        delay(2000)

        return Response.success(listOf(
            ContactListResponse(
                id = 0L,
                name = "MOCK NAME",
                username = "MOCK USERNAME",
                img = ""
            ),
            ContactListResponse(
                id = 0L,
                name = "MOCK NAME",
                username = "MOCK USERNAME",
                img = ""
            ),
            ContactListResponse(
                id = 0L,
                name = "MOCK NAME",
                username = "MOCK USERNAME",
                img = ""
            )
        ))
    }
}