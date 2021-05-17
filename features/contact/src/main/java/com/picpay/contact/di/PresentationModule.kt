package com.picpay.contact.di

import androidx.lifecycle.ViewModel
import com.picpay.core.di.key.ViewModelKey
import com.picpay.contact.presentation.viewmodel.ContactListViewModel
import com.picpay.contact.presentation.viewmodel.ContactListViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class PresentationModule {

      @Binds
      @IntoMap
      @ViewModelKey(ContactListViewModel::class)
      abstract fun provideContactListViewModel(contactListViewModelImpl: ContactListViewModelImpl)
      : ViewModel

}