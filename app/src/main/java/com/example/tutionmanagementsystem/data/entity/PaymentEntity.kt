package com.example.tutionmanagementsystem.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "payments",
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["studentId"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val paymentId: Int = 0,
    val studentId: Int,
    val amountPaid: Int,
    val paymentDate: String
)

