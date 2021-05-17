package com.picpay.core.di

import com.picpay.core.di.factory.ViewModelFactoryModule
import com.picpay.core.di.modules.ContextModule
import com.picpay.core.di.modules.NetworkModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        NetworkModule::class,
        ViewModelFactoryModule::class,
        ContextModule::class
    ]
)
@InstallIn(SingletonComponent::class)
internal abstract class CoreModule
