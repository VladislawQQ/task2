package com.example.task2.ui.activity

import androidx.lifecycle.ViewModel
import com.example.task2.data.ContactService
import com.example.task2.data.model.Contact
import kotlinx.coroutines.flow.StateFlow

class ContactViewModel : ViewModel() {

    private val contactService = ContactService()
    val contacts : StateFlow<List<Contact>> = contactService.contacts

    fun getContact(index: Int): Contact = contactService.getContact(index)

    fun getContactIndex(contact: Contact) : Int = contactService.getContactIndex(contact)

    fun deleteContact(contact: Contact): Int = contactService.deleteContact(contact)

    fun addContact(index: Int, contact: Contact) {
        contactService.addContact(index, contact)
    }

    fun addContact(contact: Contact) {
        contactService.addContact(contact)
    }
}