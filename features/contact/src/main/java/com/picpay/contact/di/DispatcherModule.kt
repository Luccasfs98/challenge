package com.picpay.contact.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Named

@Module
@InstallIn(ViewModelComponent::class)
class DispatcherModule {

    @Provides
    @Named("IO")
    fun provideCoroutinesDispatcherIO() : CoroutineDispatcher = Dispatchers.IO
}