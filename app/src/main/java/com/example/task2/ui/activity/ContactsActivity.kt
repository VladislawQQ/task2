package com.example.task2.ui.activity

import android.Manifest.permission.READ_CONTACTS
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.task2.R
import com.example.task2.data.model.Contact
import com.example.task2.databinding.ActivityContactsBinding
import com.example.task2.ui.adapter.ContactActionListener
import com.example.task2.ui.adapter.ContactAdapter
import com.example.task2.ui.fragment.AddContactDialogFragment
import com.example.task2.ui.fragment.ConfirmationListener
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ContactsActivity : AppCompatActivity(), ConfirmationListener {

    private lateinit var binding: ActivityContactsBinding
    private lateinit var contactAdapter: ContactAdapter
//    private var READ_CONTACTS_GRANTED = false

    private val contactViewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        askPermission()

//        if (READ_CONTACTS_GRANTED) {
//            bindRecycleView()
//            observeViewModel()
//            addSwipeToDelete()
//            setListeners()
//        } else {
//            showToastPermission()
//        }
    }

    private fun showToastPermission() {
        Toast
            .makeText(this, "You allow access to contacts.", Toast.LENGTH_LONG)
            .show()
    }

    private fun askPermission() {
        val hasReadContactPermission = ContextCompat.checkSelfPermission(this, READ_CONTACTS)

        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            bindRecycleView()
            observeViewModel()
            addSwipeToDelete()
            setListeners()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_CONTACTS), 1
            )
            showToastPermission()
        }
    }

    private fun setListeners() {
        binding.textViewAddContact.setOnClickListener { startDialogAddContact() }
        binding.activityContactsImageViewBack.setOnClickListener { imageViewBackListener() }
    }

    private fun imageViewBackListener() {
        finish()
    }

    private fun startDialogAddContact() {
        val addContactDialogFragment = AddContactDialogFragment()
        addContactDialogFragment.show(supportFragmentManager, AddContactDialogFragment.TAG)
    }

    private fun bindRecycleView() {
        contactAdapter = ContactAdapter(contactActionListener = object : ContactActionListener {
            override fun onContactDelete(contact: Contact) {
                val index = contactViewModel.getContactIndex(contact)
                contactViewModel.deleteContact(contact)
                showDeleteMessage(index, contact)
            }
        })

        with(binding.recyclerViewContacts) {
            layoutManager = LinearLayoutManager(
                this@ContactsActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = contactAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                contactViewModel.contacts.collect {
                    contactAdapter.submitList(it)
                }
            }
        }
    }

    private fun addSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.END or ItemTouchHelper.START
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            )
                    : Boolean = false

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val index = viewHolder.adapterPosition
                val contact: Contact = contactViewModel.getContact(index)

                contactViewModel.deleteContact(contact)
                showDeleteMessage(index, contact)
            }
        }).attachToRecyclerView(binding.recyclerViewContacts)
    }

    private fun showDeleteMessage(index: Int, contact: Contact) {
        Snackbar.make(binding.root, R.string.message_delete, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.snackbar_action).uppercase()) {
                contactViewModel.addContact(index, contact)
            }.setActionTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.contacts_activity_class_snackbar_action_color
                )
            )
            .setTextColor(
                ContextCompat.getColor(
                    applicationContext,
                    R.color.contacts_activity_class_snackbar_text_color
                )
            )
            .show()
    }

    override fun onConfirmButtonClicked(contact: Contact) {
        contactViewModel.addContact(contact)
        Snackbar.make(binding.root, R.string.message_add_contact, Snackbar.LENGTH_SHORT)
            .show()
    }
}
