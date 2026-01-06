package com.example.tutionmanagementsystem.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "attendence",
    primaryKeys = ["studentId", "date"],
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class AttendanceEntity(
    val studentId: Int,
    val date: Long,
    val isPresent: Boolean
)
