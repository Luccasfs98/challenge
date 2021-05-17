package com.picpay.contact.domain.usecase

import com.picpay.contact.repository.ContactRepository
import com.picpay.contact.domain.mapper.toModel
import com.picpay.core.utils.*
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class GetContactListUseCaseImpl @Inject constructor(
    private val repository: ContactRepository
) : GetContactListUseCase {

    override suspend fun invoke(forceRefresh : Boolean) = flow {
        try {
            emit(Loading())
            val response = repository.getContacts(forceRefresh)
            emit(Success(response.toModel()))
        } catch (throwable: Throwable){
            emit(Error(throwable))
        }
    }
}