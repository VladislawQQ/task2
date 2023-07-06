package com.example.task2.ui.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.task2.data.ContactGenerator
import com.example.task2.databinding.DialogAddContactBinding
import com.example.task2.ui.utils.ext.setContactPhoto

class AddContactDialogFragment : DialogFragment() {

    private lateinit var listener: ConfirmationListener
    private lateinit var _binding: DialogAddContactBinding
    private val _contactGenerator = ContactGenerator()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddContactBinding.inflate(layoutInflater)
        val builder = activity?.let { AlertDialog.Builder(it) }
            ?: throw IllegalStateException("Activity is null")

        setListeners()

        _binding.imageViewProfilePhoto.setContactPhoto()
        builder.setView(_binding.root)
        return builder.create()
    }

    private fun setListeners() {
        _binding.dialogImageViewBack.setOnClickListener { imageViewBackListener() }
        _binding.dialogButtonSave.setOnClickListener { saveButtonListener() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as ConfirmationListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement ConfirmationListener")
        }
    }

    private fun saveButtonListener() {
        listener.onConfirmButtonClicked(
            with(_binding) {
                _contactGenerator.createContact(
                    userName = editTextUsername.text.toString(),
                    career = editTextCareer.text.toString()
                )
            }
        )
        dismiss()
    }

    private fun imageViewBackListener() {
        dismiss()
    }


    companion object {
        const val TAG = "Add contact dialog"
    }

}