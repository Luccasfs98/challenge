package com.picpay.contact.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.contact.data.database.dao.ContactDao
import com.picpay.contact.data.database.entity.ContactEntity

@Database(
        entities = [ContactEntity::class],
        version = 1,
        exportSchema = false // Just to get rid of the warning generated at build time
)

abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

}
