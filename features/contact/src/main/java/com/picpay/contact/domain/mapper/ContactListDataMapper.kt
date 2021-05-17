package com.picpay.contact.domain.mapper

import com.picpay.contact.data.remote.model.dto.ContactDto
import com.picpay.contact.domain.model.ContactModel

internal fun List<ContactDto>.toModel() = this.map {
    ContactModel(
        imgUrl = it.img, name = it.name, id = it.id, username = it.username
    )
}

