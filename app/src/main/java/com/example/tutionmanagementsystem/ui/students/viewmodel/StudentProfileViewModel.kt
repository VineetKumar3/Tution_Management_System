package com.example.tutionmanagementsystem.ui.students.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class StudentProfileUiState(
    val studentName: String = "Rahul Sharma",
    val className: String = "Class 10",
    val admissionDate: String = "05 Mar 2021",
    val parentPhone: String = "9876543210",
    val totalClasses: Int = 50,
    val classesAttended: Int = 45,
    val courseFee: Double = 1500.0,
    val actualFee: Double = 1200.0,
    val isActive: Boolean = true
) {
    val attendancePercentage: Float = if (totalClasses > 0) classesAttended.toFloat() / totalClasses else 0f
    val pendingAmount: Double = courseFee - actualFee
}

class StudentProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(StudentProfileUiState())
    val uiState: StateFlow<StudentProfileUiState> = _uiState.asStateFlow()

    fun onStatusChange(isActive: Boolean) {
        _uiState.update { it.copy(isActive = isActive) }
    }
}