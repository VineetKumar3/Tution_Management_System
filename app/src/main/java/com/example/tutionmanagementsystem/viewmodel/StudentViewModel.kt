package com.example.tutionmanagementsystem.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tutionmanagementsystem.data.entity.StudentEntity
import com.example.tutionmanagementsystem.data.repository.Repository
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.launch

class StudentViewModel (private val repository: Repository) : ViewModel() {
    val allStudents = repository.all_students.asLiveData()

    private val _selectedStudent = MutableLiveData<StudentEntity>()
    val selectedStudent: LiveData<StudentEntity> get() = _selectedStudent

    fun addStudent(student: StudentEntity){
        viewModelScope.launch {
            repository.insert(student)
        }
    }

    fun updateStudent(student: StudentEntity){
        viewModelScope.launch {
            repository.update(student)
        }
    }

    fun deleteStudent(student: StudentEntity){
        viewModelScope.launch {
            repository.delete(student)
        }
    }

    fun getStudentById(studentId: Int) {
        viewModelScope.launch {
            _selectedStudent.value = repository.getStudentById(studentId)
        }
    }
}
