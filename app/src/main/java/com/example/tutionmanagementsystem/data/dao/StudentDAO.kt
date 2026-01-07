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

    @Query("SELECT * FROM students ORDER BY studentName ASC")
    fun getAllStudents(): Flow<List<StudentEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: StudentEntity)

    @Query("SELECT * FROM students WHERE studentId = :id")
    fun getStudentByIdFlow(id: Int): Flow<StudentEntity?>

    @Query("SELECT * FROM students WHERE studentId = :id")
    suspend fun getStudentById(id: Int): StudentEntity?

    @Update
    suspend fun updateStudent(student: StudentEntity)

    @Delete
    suspend fun deleteStudent(student: StudentEntity)

}
