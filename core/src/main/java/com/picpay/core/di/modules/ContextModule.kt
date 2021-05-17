package com.picpay.core.di.modules

import android.app.Application
import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContextModule {

    @Provides
    @Singleton
    fun providesApplicationContext(application : Application): Context = application

    @Provides
    @Singleton
    fun providesResource(context : Context): Resources = context.resources
}