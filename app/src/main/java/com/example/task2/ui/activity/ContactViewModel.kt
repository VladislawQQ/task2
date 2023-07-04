package com.example.task2.ui.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task2.data.ContactService
import com.example.task2.data.model.Contact
import com.example.task2.ui.fragment.AddContactDialogFragment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

//todo naming of viewModel!
class ContactViewModel : ViewModel() {

    private val contactService = ContactService()

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts = contactService.contacts //_contacts.asStateFlow()  //todo

    init {
//        viewModelScope.launch {
//            contactService.getContacts().collectLatest { contactList ->
//                _contacts.value = contactList
//            }
//        }
    }

    fun getContact(index: Int): Contact = contactService.getContact(index)

    fun deleteContact(contact: Contact): Int = contactService.deleteContact(contact)

    fun addContact(index: Int, contact: Contact) {
        contactService.addContact(index, contact)
    }

    private fun addContact(contact: Contact) {
        contactService.addContact(contact)
    }
}