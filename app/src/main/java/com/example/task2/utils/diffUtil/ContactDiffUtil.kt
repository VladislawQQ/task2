package com.example.task2.utils.diffUtil

import androidx.recyclerview.widget.DiffUtil
import com.example.task2.data.Contact

class ContactDiffUtil : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact)
        : Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact)
        : Boolean = oldItem == newItem
}