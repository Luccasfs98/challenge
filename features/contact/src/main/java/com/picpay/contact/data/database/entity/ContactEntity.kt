package com.picpay.contact.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity (
        @PrimaryKey(autoGenerate = true)
        val id: Long,
        val imgUrl: String,
        val name: String,
        val username: String
)