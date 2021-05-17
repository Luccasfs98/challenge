package com.picpay.contact.data.remote

import com.picpay.contact.data.remote.model.response.ContactListResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface representing API endpoints for contacts
 */
internal interface ContactApi {

    @GET("users")
    suspend fun getContacts(): Response<List<ContactListResponse>>

}