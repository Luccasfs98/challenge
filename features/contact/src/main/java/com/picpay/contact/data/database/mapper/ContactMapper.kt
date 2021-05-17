package com.picpay.contact.data.database.mapper

import com.picpay.contact.data.database.entity.ContactEntity
import com.picpay.contact.data.remote.model.dto.ContactDto
import com.picpay.contact.data.remote.model.response.ContactListResponse
import com.picpay.contact.domain.model.ContactModel
import javax.inject.Inject

internal fun List<ContactEntity>.toDto() = this.map {
    ContactDto(
        img = it.imgUrl, name = it.name, id = it.id, username = it.username
    )
}
internal fun List<ContactDto>.toEntity() = this.map {
    ContactEntity(
        imgUrl = it.img, name = it.name, id = it.id, username = it.username
    )
}
