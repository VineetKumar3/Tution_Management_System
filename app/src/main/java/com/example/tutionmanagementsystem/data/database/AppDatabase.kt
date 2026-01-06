package com.example.tutionmanagementsystem.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tutionmanagementsystem.data.dao.AttendanceDAO
import com.example.tutionmanagementsystem.data.dao.PaymentDAO
import com.example.tutionmanagementsystem.data.dao.StudentDAO
import com.example.tutionmanagementsystem.data.entity.AttendanceEntity
import com.example.tutionmanagementsystem.data.entity.PaymentEntity
import com.example.tutionmanagementsystem.data.entity.StudentEntity

@Database(entities = [
    StudentEntity::class,
    PaymentEntity::class,
    AttendanceEntity::class],
    version = 1,
    exportSchema = true)
abstract class StudentDatabase : RoomDatabase(){

    abstract fun studentDAO(): StudentDAO
    abstract fun paymentDAO(): PaymentDAO
    abstract fun attendenceDAO(): AttendanceDAO

    companion object{
        @Volatile
        private var INSTANCE: StudentDatabase? = null

        fun getDatabase(context: Context): StudentDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "tuition_management_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}