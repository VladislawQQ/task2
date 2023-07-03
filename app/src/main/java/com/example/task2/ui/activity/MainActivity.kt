package com.example.task2.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.task2.R
import com.example.task2.databinding.ActivityMainBinding
import com.example.task2.model.Contact
import com.example.task2.ui.adapter.ContactActionListener
import com.example.task2.ui.adapter.ContactAdapter
import com.example.task2.ui.fragment.AddContactDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), AddContactDialogFragment.ConfirmationListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ContactAdapter

    private val contactViewModel : ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindRecycleView()
        observeViewModel()
        addSwipeToDelete()

        binding.addContactTextView.setOnClickListener {
            val addContactDialogFragment = AddContactDialogFragment()
            addContactDialogFragment.show(supportFragmentManager, AddContactDialogFragment.TAG)
        }
    }

    private fun bindRecycleView() {
        adapter = ContactAdapter(contactActionListener = object : ContactActionListener {
            override fun onContactDelete(contact: Contact) {
                val index = contactViewModel.deleteContact(contact)
                Log.d("delete index", index.toString())
                showDeleteMessage(index, contact)
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                contactViewModel.contacts.collect {
                    adapter.submitList(it.toMutableList())
                }
            }
        }
    }

    private fun addSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.END or ItemTouchHelper.START) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder)
            : Boolean = false

            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val index = viewHolder.adapterPosition
                val contact: Contact = contactViewModel.getContact(index)

                contactViewModel.deleteContact(contact)
                showDeleteMessage(index, contact)
            }
        }).attachToRecyclerView(binding.recyclerView)
    }

    private fun showDeleteMessage(index: Int, contact: Contact) {
        Snackbar.make(binding.root, R.string.message_delete, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.snackbar_action).uppercase()) {
                contactViewModel.addContact(index, contact)
                Log.d("add index", index.toString())
            }.setActionTextColor(ContextCompat.getColor(applicationContext, R.color.purple_500))
            .setTextColor(ContextCompat.getColor(applicationContext, R.color.light_gray))
            .show()
    }

    override fun onConfirmButtonClicked(contact: Contact) {
        contactViewModel.addContact(index = adapter.currentList.size, contact)
        Snackbar.make(binding.root, R.string.message_add_contact, Snackbar.LENGTH_SHORT)
            .show()
        Log.d("index", adapter.currentList.toString())
    }
}
