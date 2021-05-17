package com.picpay.contact.data.database.dao

import androidx.room.*
import com.picpay.contact.data.database.entity.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Query("SELECT * FROM contacts")
    fun getAll(): List<ContactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(list: List<ContactEntity>)

    @Query("SELECT COUNT(*) FROM contacts")
    fun getNumOfRows(): Int

    @Query("DELETE FROM contacts")
    fun deleteAll(): Int
}