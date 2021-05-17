package com.picpay.contact.data.remote.model.response

import com.squareup.moshi.Json

/**
 * Class to get the user contacts API response
 */
internal data class ContactListResponse(
        @Json(name = "img") val img: String?,
        @Json(name = "name") val name: String?,
        @Json(name = "id") val id: Long,
        @Json(name = "username") val username: String?
)