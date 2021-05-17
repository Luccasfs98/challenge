package com.picpay.contact.presentation.ui.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.picpay.commons.ui.base.BaseViewHolder
import com.picpay.contact.databinding.ListItemUserBinding
import com.picpay.contact.domain.model.ContactModel

class ContactListItemViewHolder(
    inflater: LayoutInflater
) : BaseViewHolder<ListItemUserBinding>(
binding = ListItemUserBinding.inflate(inflater)
) {

    fun bind(user: ContactModel) {
        binding.data = user
        binding.executePendingBindings()
    }
}