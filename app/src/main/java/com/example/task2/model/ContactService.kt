package com.example.task2.model

import androidx.lifecycle.MutableLiveData
import com.example.task2.data.Contact
import com.example.task2.data.ContactGenerator

class ContactService {

    private var contacts = MutableLiveData<List<Contact>>(emptyList())
    private val contactProvider = ContactGenerator()

    init {
        if (contacts.value.isNullOrEmpty()) {
            contacts = contactProvider.generateContacts()
        }
    }

    fun deleteContact(contact: Contact): Int {
        val indexToDelete = contacts.value!!.indexOf(contact)

        contacts.value = contacts.value!!.toMutableList().apply {
            remove(contact)
        }

        return indexToDelete
    }

    fun addContact(contact: Contact) {
        contacts.value = contacts.value!!.toMutableList().apply {
            add(contact)
        }
    }

    fun addContact(index: Int, contact: Contact) {
        contacts.value = contacts.value!!.toMutableList().apply {
            add(index, contact)
        }
    }

    fun getContact(index: Int): Contact {
        return contacts.value!![index]
    }

    fun getContacts(): MutableList<Contact> {
        return contacts.value!!.toMutableList()
    }
}