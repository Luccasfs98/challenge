package com.picpay.contact.presentation.viewState

sealed class ContactListViewState {

    object Loading : ContactListViewState()
    object Refreshing : ContactListViewState()
    object Empty : ContactListViewState()
    object Loaded : ContactListViewState()
    object Error : ContactListViewState()

    fun isRefreshing() = this is Refreshing
    fun isLoaded() = this is Loaded
    fun isLoading() = this is Loading
    fun isEmpty() = this is Empty
    fun isError() = this is Error

}
