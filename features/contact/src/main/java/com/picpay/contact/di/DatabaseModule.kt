package com.picpay.contact.di

import android.content.Context
import androidx.room.Room
import com.picpay.contact.data.database.ContactDatabase
import com.picpay.contact.data.database.dao.ContactDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
internal object DatabaseModule {

    @Provides
    fun provideDatabase(context: Context): ContactDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ContactDatabase::class.java, "contact_db"
        ).build()
    }

    @Provides
    fun provideContactEntityDao(database: ContactDatabase): ContactDao = database.contactDao()
}