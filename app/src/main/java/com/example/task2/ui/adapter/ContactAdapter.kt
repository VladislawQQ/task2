package com.example.task2.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.task2.R
import com.example.task2.data.model.Contact
import com.example.task2.databinding.ContactItemBinding
import com.example.task2.ui.adapter.diffUtil.ContactDiffUtil
import com.example.task2.ui.utils.ext.setContactPhoto

interface ContactActionListener {   //todo interface -> separate file
    fun onContactDelete(contact: Contact)
}

class ContactAdapter(
    private val contactActionListener: ContactActionListener
)
    : ListAdapter<Contact, ContactAdapter.ContactViewHolder>(ContactDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding = ContactItemBinding.inflate(inflater, parent, false)
        //binding.imageViewBucket.setOnClickListener(this)    //todo 0_o
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size     //todo delete

    inner class ContactViewHolder(
        private val binding: ContactItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(contact: Contact) {
            with(binding) {
                textViewName.text = contact.name
                textViewCareer.text = contact.career.replaceFirstChar { it.titlecase() }

                imageViewBucket.setOnClickListener{ contactActionListener.onContactDelete(contact) }
//                imageViewBucket.tag = contact

                if (contact.photo.isNotBlank()) {
                    profileImage.setContactPhoto(contact.photo)
                } else {
//                Glide.with(profileImage.context)
//                    .clear(profileImage)

                profileImage.setImageResource(R.drawable.profile_photo)
                }
            }
        }
    }



//    override fun onClick(view: View) {
//        val contact = view.tag as Contact
//        when(view.id) {
//            R.id.image_view_bucket -> contactActionListener.onContactDelete(contact)
//        }
//    }

}