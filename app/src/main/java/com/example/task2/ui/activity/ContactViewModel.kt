package com.example.task2.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task2.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts : StateFlow<List<Contact>> = _contacts

    private val contactService = ContactService()

    init {
        viewModelScope.launch {
            contactService.getContacts().collectLatest { contactList ->
                _contacts.value = contactList
            }
        }
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