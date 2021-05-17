package com.picpay.contact.domain.usecase

import com.picpay.contact.domain.model.ContactModel
import com.picpay.core.utils.DataResult
import kotlinx.coroutines.flow.Flow

internal interface GetContactListUseCase {

    suspend fun invoke(forceRefresh : Boolean) : Flow<DataResult<List<ContactModel>>>
}