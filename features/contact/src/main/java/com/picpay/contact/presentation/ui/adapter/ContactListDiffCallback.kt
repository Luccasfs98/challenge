package com.picpay.contact.presentation.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.picpay.contact.domain.model.ContactModel

class ContactListDiffCallback(
    private val oldList: List<ContactModel>,
    private val newList: List<ContactModel>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].username == newList[newItemPosition].username
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return true
    }
}