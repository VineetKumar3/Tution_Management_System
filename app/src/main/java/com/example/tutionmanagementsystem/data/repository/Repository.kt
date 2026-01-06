package com.example.tutionmanagementsystem.data.repository

import com.example.tutionmanagementsystem.data.dao.AttendanceDAO
import com.example.tutionmanagementsystem.data.dao.PaymentDAO
import com.example.tutionmanagementsystem.data.dao.StudentDAO
import com.example.tutionmanagementsystem.data.entity.AttendanceEntity
import com.example.tutionmanagementsystem.data.entity.PaymentEntity
import com.example.tutionmanagementsystem.data.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

class Repository(
    private val studentDAO: StudentDAO,
    private val paymentDAO: PaymentDAO,
    private val attendanceDAO: AttendanceDAO
){
    // --- Student Functions ---
    val all_students: Flow<List<StudentEntity>> = studentDAO.getAllStudents()

    fun getStudentsById(id: Int): Flow<StudentEntity?>{
        return studentDAO.getStudentById(id)
    }

    suspend fun insert(student: StudentEntity){
        studentDAO.insertStudent(student)
    }

    suspend fun update(student: StudentEntity){
        studentDAO.updateStudent(student)
    }

    suspend fun delete(student: StudentEntity){
        studentDAO.deleteStudent(student)
    }

    // --- Payment Functions ---
    suspend fun insertPayment(payment: PaymentEntity) {
        paymentDAO.insertPayment(payment)
    }

    fun getPaymentsForStudent(studentId: Int): Flow<List<PaymentEntity>> {
        return paymentDAO.getPaymentsForStudent(studentId)
    }


    // --- Attendance Functions ---
    suspend fun upsertAttendance(attendance: AttendanceEntity) {
        // FIX 1: Corrected typo from "upsertAttendence" to "upsertAttendance"
        attendanceDAO.upsertAttendance(attendance)
    }

    fun getAttendanceForStudent(studentId: Int): Flow<List<AttendanceEntity>> {
        // FIX 2: Corrected typo from "getAttendenceForStudent" to "getAttendanceForStudent"
        return attendanceDAO.getAttendanceForStudent(studentId)
    }

    fun getAllAttendance(): Flow<List<AttendanceEntity>> {
        return attendanceDAO.getAllAttendance()
    }
}
