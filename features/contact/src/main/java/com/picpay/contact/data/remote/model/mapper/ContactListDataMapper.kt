package com.picpay.contact.data.remote.model.mapper

import com.picpay.contact.data.remote.model.dto.ContactDto
import com.picpay.contact.data.remote.model.response.ContactListResponse

internal fun List<ContactListResponse>.toDto() = this.map {
    ContactDto(
        img = it.img.orEmpty(), name = it.name.orEmpty(), id = it.id, username = it.username.orEmpty()
    )
}
