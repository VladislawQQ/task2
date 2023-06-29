package com.example.task2.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.task2.R
import com.example.task2.ui.adapter.ContactActionListener
import com.example.task2.ui.adapter.ContactAdapter
import com.example.task2.model.Contact
import com.example.task2.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ContactAdapter

    private val contactViewModel : ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeViewModel()
        bindRecycleView()
        addSwipeToDelete()
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
//                adapter.notifyItemRemoved(index)

                showDeleteMessage(index, contact)
            }
        }).attachToRecyclerView(binding.recyclerView)
    }

    private fun bindRecycleView() {
        adapter = ContactAdapter(contactActionListener = object : ContactActionListener {
            override fun onContactDelete(contact: Contact) {
                val index = contactViewModel.deleteContact(contact)

                adapter.notifyItemRemoved(index)
                showDeleteMessage(index, contact)
            }
        })

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun showDeleteMessage(index: Int, contact: Contact) {
        Snackbar.make(binding.root, R.string.message_delete, Snackbar.LENGTH_LONG)
            .setAction(R.string.snackbar_action) {
                contactViewModel.addContact(index, contact)
//                adapter.notifyItemInserted(index)
            }
            .show()
    }

    private fun observeViewModel() {
        contactViewModel.contacts.observe(this) {
            adapter.submitList(it)
        }
    }
}