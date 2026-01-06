package com.example.tutionmanagementsystem.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tutionmanagementsystem.data.entity.AttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDAO {

    @Upsert
    suspend fun upsertAttendance(attendence: AttendanceEntity)

    @Query("SELECT * FROM attendence WHERE studentId = :studentId ORDER BY date DESC")
    fun getAttendanceForStudent(studentId: Int): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendence ORDER BY date DESC")
    fun getAllAttendance(): Flow<List<AttendanceEntity>>

}