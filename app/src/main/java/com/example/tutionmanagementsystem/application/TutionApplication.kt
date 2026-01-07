package com.example.tutionmanagementsystem.application

import android.app.Application
import com.example.tutionmanagementsystem.data.database.StudentDatabase
import com.example.tutionmanagementsystem.data.repository.Repository

class TuitionApplication : Application() {
    // Using by lazy so the database and repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { StudentDatabase.getDatabase(this) }
    val repository by lazy { Repository(database.studentDAO(), database.paymentDAO(), database.attendenceDAO()) }
}
