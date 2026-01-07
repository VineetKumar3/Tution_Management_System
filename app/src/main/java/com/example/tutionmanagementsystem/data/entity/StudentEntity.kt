package com.example.tutionmanagementsystem.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val studentId: Int = 0,
    val studentName: String,
    val studentClass: String,
    val mobileNumber: String,
    val admissionDate: String,
    val courseFee: String,
    val actualFee: String
)
