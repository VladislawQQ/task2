package com.example.task2.ui.adapter

import com.example.task2.data.model.Contact

interface ContactActionListener {
    fun onContactDelete(contact: Contact)
}