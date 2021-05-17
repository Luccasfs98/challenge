package com.picpay.contact.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.contact.data.database.dao.ContactDao
import com.picpay.contact.data.database.mapper.toDto
import com.picpay.contact.data.database.mapper.toEntity
import com.picpay.contact.data.remote.model.dto.ContactDto
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ContactDatabaseTest {
    private lateinit var contactDao: ContactDao
    private lateinit var db: ContactDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ContactDatabase::class.java).build()
        contactDao = db.contactDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeContactAndReadInList() {
        val list = listOf(ContactDto(
            username = "Teste",
            img = "Teste",
            name = "Teste",
            id = 0,
        ))
        contactDao.insertList(list.toEntity())
        val listEntity = contactDao.getAll().toDto()
        assert(listEntity.size == list.size && contactDao.getNumOfRows() != 0)
    }
}