package com.example.tutionmanagementsystem.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val clas: String,
    val phNumber: String,
    val admissionDate: String,
    val courseFee: String,
    val actualFee: String
)
