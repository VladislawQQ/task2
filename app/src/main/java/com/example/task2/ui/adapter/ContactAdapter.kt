package com.example.task2.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task2.data.model.Contact
import com.example.task2.databinding.ContactItemBinding
import com.example.task2.ui.adapter.diffUtil.ContactDiffUtil
import com.example.task2.ui.utils.ext.setContactPhoto

class ContactAdapter(
    private val contactActionListener: ContactActionListener
)
    : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater, parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(currentList[position])
        Log.d("myTag", currentList.size.toString())
    }

    inner class ContactViewHolder(
        private val binding: ContactItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                textViewName.text = contact.name
                textViewCareer.text = contact.career.replaceFirstChar { it.titlecase() }

                imageViewBucket.setOnClickListener{ contactActionListener.onContactDelete(contact) }

                with(imageViewContactItemPhoto) {
                    if (contact.photo.isNotBlank()) {
                        setContactPhoto(contact.photo)
                    } else {
                        setContactPhoto()
                    }
                }
            }
        }
    }
}