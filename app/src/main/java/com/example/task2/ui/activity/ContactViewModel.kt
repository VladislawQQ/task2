package com.example.task2.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task2.model.Contact

class ContactViewModel : ViewModel() {

    private val _contacts = MutableLiveData<List<Contact>>(emptyList())
    val contacts : LiveData<List<Contact>> = _contacts

    private val contactService = ContactService()

    init {
        _contacts.value = contactService.getContacts()
    }

    fun getContact(index: Int): Contact = contactService.getContact(index)

    fun deleteContact(contact: Contact): Int = contactService.deleteContact(contact)

    fun addContact(contact: Contact) {
        contactService.addContact(contact)
    }

    fun addContact(index: Int, contact: Contact) {
        contactService.addContact(index, contact)

    }
}