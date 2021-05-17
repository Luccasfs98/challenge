package com.picpay.contact.data.remote.model.dto

/**
 * A data transfer object to carries authentication data to other layers
 */
internal data class ContactDto(
   val img: String,
   val name: String,
   val id: Long,
   val username: String
)