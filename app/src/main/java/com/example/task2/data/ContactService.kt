package com.example.task2.data

import android.util.Log
import com.example.task2.data.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ContactService {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts

    private val contactProvider = ContactGenerator()

    init {
        if (contacts.value.isEmpty()) {
            val contactsPhone = try {
                contactProvider.getPhoneContacts()
            } catch (e: Exception) {
                Log.d("myLog", "Catch! ${e.message}")
                emptyList()
            }

            _contacts.value = contactsPhone.ifEmpty {
                contactProvider.generateContacts()
            }
        }
    }

    fun deleteContact(contact: Contact): Int {
        val indexToDelete: Int

        _contacts.value = contacts.value.toMutableList().apply {
            indexToDelete = indexOf(contact)
            remove(contact)
        }

        return indexToDelete
    }

    fun addContact(contact: Contact) {
        _contacts.value = contacts.value.toMutableList().apply {
            add(contact)
        }
    }

    fun addContact(index: Int, contact: Contact) {
        _contacts.value = contacts.value.toMutableList().apply {
            add(index, contact)
        }
    }

    fun getContactIndex(contact: Contact): Int = contacts.value.indexOf(contact)

    fun getContact(index: Int): Contact {
        return contacts.value[index]
    }
}