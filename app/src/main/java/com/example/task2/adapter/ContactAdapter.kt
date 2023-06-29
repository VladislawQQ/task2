package com.example.task2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task2.R
import com.example.task2.data.Contact
import com.example.task2.databinding.ContactItemBinding
import com.example.task2.utils.diffUtil.ContactDiffUtil
import com.example.task2.utils.ext.setContactPhoto

interface ContactActionListener {
    fun onContactDelete(contact: Contact)
}

class ContactAdapter(private val contactActionListener: ContactActionListener)
    : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffUtil()), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater, parent, false)
        binding.imageViewBucket.setOnClickListener(this)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)

        with(holder.binding) {
            imageViewBucket.tag = contact
            textViewName.text = contact.name
            textViewCareer.text = contact.career.replaceFirstChar { it.titlecase() }

            if (contact.photo.isNotBlank()) {
                profileImage.setContactPhoto(contact.photo)
            } else {
                Glide.with(profileImage.context)
                    .clear(profileImage)

                profileImage.setImageResource(R.drawable.profile_photo)
            }
        }
    }

    override fun getItemCount(): Int = currentList.size

    class ContactViewHolder(
        val binding: ContactItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(view: View) {
        val contact = view.tag as Contact
        when(view.id) {
            R.id.image_view_bucket -> contactActionListener.onContactDelete(contact)
        }
    }

}