package com.example.task2.data

import android.util.Log
import com.example.task2.data.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow

class ContactService {

    var contacts = MutableStateFlow<List<Contact>>(emptyList())
    private val contactProvider = ContactGenerator()

    init {
        if (contacts.value.isEmpty()) {
            var contactsPhone = MutableStateFlow<List<Contact>>(emptyList())
            try {
                contactsPhone = contactProvider.getPhoneContacts()
            } catch (e: Exception) {        //todo runtime permissions
                Log.d("myLog", "Catch! ${e.message}")
            }

            contacts =
                if (contactsPhone.value.isNotEmpty()) contactsPhone
                    else
                        contactProvider.generateContacts()
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

//    fun getContacts(): MutableStateFlow<List<Contact>> {
//        return contacts
//    }


}