package com.picpay.contact.repository

import com.picpay.contact.data.remote.model.dto.ContactDto


internal interface ContactRepository {

    suspend fun getContacts(forceRefresh : Boolean) : List<ContactDto>
}