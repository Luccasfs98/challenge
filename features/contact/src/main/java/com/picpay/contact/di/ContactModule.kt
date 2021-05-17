package com.picpay.contact.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        DataModule::class,
        DomainModule::class,
        PresentationModule::class,
        DispatcherModule::class
    ]
)
@InstallIn(SingletonComponent::class)
class ContactModule