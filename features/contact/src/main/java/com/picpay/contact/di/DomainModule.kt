package com.picpay.contact.di

import com.picpay.contact.domain.usecase.GetContactListUseCase
import com.picpay.contact.domain.usecase.GetContactListUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class DomainModule {

    @Binds
    abstract fun provideGetContactListUseCase(
        repository : GetContactListUseCaseImpl,
    ) : GetContactListUseCase
}