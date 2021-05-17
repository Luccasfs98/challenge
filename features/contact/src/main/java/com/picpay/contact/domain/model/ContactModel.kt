package com.picpay.contact.domain.model

/**
 * Data class for encapsulating the contact data
 */
data class ContactModel(
    val imgUrl: String,
    val name: String,
    val id: Long,
    val username: String
)
