package com.example.tutionmanagementsystem.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tutionmanagementsystem.data.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDAO {

    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudents(): Flow<List<StudentEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: StudentEntity)

    @Query("SELECT * FROM students WHERE id = :id")
    fun getStudentById(id: Int): Flow<StudentEntity?>

    @Update
    suspend fun updateStudent(student: StudentEntity)

    @Delete
    suspend fun deleteStudent(student: StudentEntity)

}