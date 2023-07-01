package com.example.task2.ui.activity

import com.example.task2.model.Contact
import com.example.task2.ui.adapter.data.ContactGenerator
import kotlinx.coroutines.flow.MutableStateFlow

class ContactService {

    private var contacts = MutableStateFlow<List<Contact>>(emptyList())
    private val contactProvider = ContactGenerator()

    init {
        if (contacts.value.isEmpty()) {
            val contactsPhone = contactProvider.getPhoneContacts()

            contacts =
                if (contactsPhone.value.isNotEmpty()) contactsPhone
                    else contactProvider.generateContacts()
        }
    }

    fun deleteContact(contact: Contact): Int {
        val indexToDelete : Int

        contacts.value = contacts.value.toMutableList().apply {
            indexToDelete  = indexOf(contact)
            remove(contact)
        }

        return indexToDelete
    }

    fun addContact(contact: Contact) {
        contacts.value = contacts.value.toMutableList().apply {
            add(contact)
        }
    }

    fun addContact(index: Int, contact: Contact) {
        contacts.value = contacts.value.toMutableList().apply {
            add(index, contact)
        }
    }

    fun getContact(index: Int): Contact {
        return contacts.value[index]
    }

    fun getContacts(): MutableStateFlow<List<Contact>> {
        return contacts
    }


}