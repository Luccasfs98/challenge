package com.picpay.contact.data.model

import com.picpay.contact.data.database.entity.ContactEntity
import com.picpay.contact.data.remote.model.dto.ContactDto
import com.picpay.contact.data.remote.model.response.ContactListResponse

internal fun List<ContactListResponse>.toDbEntity() = this.map {
    ContactEntity(
        id = it.id,
        imgUrl = it.img.orEmpty(),
        name = it.name.orEmpty(),
        username = it.username.orEmpty()
    )
}

internal fun List<ContactEntity>.toDto() = this.map {
    ContactDto(
        id = it.id,
        img = it.imgUrl,
        name = it.name,
        username = it.username
    )
}