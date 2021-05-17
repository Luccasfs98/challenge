package com.picpay.contact.di

import android.content.Context
import android.content.SharedPreferences
import com.picpay.contact.data.remote.ContactApi
import com.picpay.contact.repository.ContactRepository
import com.picpay.contact.repository.ContactRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class, AbstractDataModule::class])
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    internal fun provideContactsService(retrofit: Retrofit): ContactApi = retrofit.create(ContactApi::class.java)

    @Provides
    fun provideContactPreferences(context : Context): SharedPreferences = context.getSharedPreferences("contact", Context.MODE_PRIVATE)
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ContactAnnotation


@Module
@InstallIn(SingletonComponent::class)
internal abstract class AbstractDataModule {
    @Binds
    abstract fun bindsRepository(implementation: ContactRepositoryImpl): ContactRepository
}
