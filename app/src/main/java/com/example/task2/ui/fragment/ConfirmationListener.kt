package com.example.task2.ui.fragment

import com.example.task2.data.model.Contact

interface ConfirmationListener {
    fun onConfirmButtonClicked(contact: Contact)
}