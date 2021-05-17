package com.picpay.contact.repository

import android.content.SharedPreferences
import com.picpay.core.data.base.BaseRepository
import com.picpay.contact.data.database.dao.ContactDao
import com.picpay.contact.data.database.mapper.toEntity
import com.picpay.contact.data.model.toDto
import com.picpay.contact.data.remote.model.dto.ContactDto
import com.picpay.contact.data.remote.ContactApi
import com.picpay.contact.data.remote.model.mapper.toDto
import com.picpay.contact.repository.ContactRepositoryConstants.CONTACT_LIST_LAST_UPDATED_TIME
import com.picpay.core.data.exceptions.EmptyDataException
import java.util.*
import javax.inject.Inject

/**
 * Implementation of [ContactRepository]
 */
internal class ContactRepositoryImpl @Inject constructor(
    private val dao: ContactDao,
    private val api: ContactApi,
    private val preferences : SharedPreferences?
) : ContactRepository, BaseRepository() {

    // 5 minutes of cache
    private val cacheLimit = 5 * 1000 * 60

    override suspend fun getContacts(forceRefresh : Boolean) : List<ContactDto> {
        try {
            val now = Calendar.getInstance().timeInMillis
            val lastUpdatedTime = preferences?.getLong(CONTACT_LIST_LAST_UPDATED_TIME,0L)
            if(dao.getNumOfRows() == 0 || forceRefresh || now > lastUpdatedTime?.plus(cacheLimit)?:0L){
                val response = api.getContacts()
                when(response.code()){
                    200 -> dao.insertList(response.body()?.toDto()?.toEntity().orEmpty())
                    204 -> throw EmptyDataException()
                }
                preferences?.edit()?.putLong(CONTACT_LIST_LAST_UPDATED_TIME,now)?.apply()
            }
            return dao.getAll().toDto()
        } catch (ex: Exception) {
            throw parseErrorResponse(ex)
        }
    }
}