package com.example.task2.ui.fragment

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.task2.databinding.DialogAddContactBinding
import com.example.task2.model.Contact
import com.example.task2.ui.adapter.data.ContactGenerator
import com.example.task2.utils.ext.setDefaultPhoto

class AddContactDialogFragment : DialogFragment() {

    interface ConfirmationListener {
        fun onConfirmButtonClicked(contact: Contact)
    }

    private lateinit var listener: ConfirmationListener
    private lateinit var _binding: DialogAddContactBinding
    private val _contactGenerator = ContactGenerator()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogAddContactBinding.inflate(layoutInflater)
        val builder = activity?.let { AlertDialog.Builder(it) }
            ?: throw IllegalStateException("Activity is null")

        icBackListener()
        saveButtonListener()


        _binding.profilePhotoImageView.setDefaultPhoto()
        builder.setView(_binding.root)
        return builder.create()
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
        _binding.saveButton.setOnClickListener {
            listener.onConfirmButtonClicked(
                with(_binding) {
                    _contactGenerator.createContact(
                        userName = usernameEditText.text.toString(),
                        career = careerEditText.text.toString()
                    )
                }
            )
            dismiss()
        }
    }

    private fun icBackListener() {
        _binding.icBack.setOnClickListener {
            dismiss()
        }
    }


    companion object {
        const val TAG = "Add contact dialog"
    }

}