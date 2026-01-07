package com.example.tutionmanagementsystem.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tutionmanagementsystem.data.repository.Repository

/**
 * ViewModelProvider.Factory that knows how to create a StudentViewModel.
 * This is necessary because our StudentViewModel has a constructor that accepts a Repository,
 * and the default ViewModelProvider doesn't know how to supply it.
 */
class StudentViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {

    /**
     * The create method is called by the system when it needs a ViewModel instance.
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the requested ViewModel is our StudentViewModel
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            // If it is, create and return an instance of it, passing the repository.
            // The Suppress("UNCHECKED_CAST") is safe here because we've already checked the class type.
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(repository) as T
        }
        // If it's a different ViewModel, throw an exception.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
