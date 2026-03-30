package com.studentmanagement.repository

import androidx.lifecycle.LiveData
import com.studentmanagement.database.StudentDao
import com.studentmanagement.model.Student

class StudentRepository(private val studentDao: StudentDao) {

    val allStudents: LiveData<List<Student>> = studentDao.getAllStudents()
    val studentCount: LiveData<Int> = studentDao.getStudentCount()
    val averageMarks: LiveData<Double?> = studentDao.getAverageMarks()
    val highestMarks: LiveData<Double?> = studentDao.getHighestMarks()
    val lowestMarks: LiveData<Double?> = studentDao.getLowestMarks()
    val topPerformer: LiveData<Student?> = studentDao.getTopPerformer()

    suspend fun insertStudent(student: Student) {
        studentDao.insertStudent(student)
    }

    suspend fun updateStudent(student: Student) {
        studentDao.updateStudent(student)
    }

    suspend fun deleteStudent(student: Student) {
        studentDao.deleteStudent(student)
    }

    fun searchStudents(query: String): LiveData<List<Student>> {
        return studentDao.searchStudents(query)
    }

    suspend fun getStudentById(id: Int): Student? {
        return studentDao.getStudentById(id)
    }

    suspend fun getStudentByRollNumber(rollNumber: String): Student? {
        return studentDao.getStudentByRollNumber(rollNumber)
    }

    fun getAllStudentsSortedByName(): LiveData<List<Student>> = studentDao.getAllStudentsSortedByName()
    fun getAllStudentsSortedByMarks(): LiveData<List<Student>> = studentDao.getAllStudentsSortedByMarks()
    fun getAllStudentsSortedByRoll(): LiveData<List<Student>> = studentDao.getAllStudentsSortedByRoll()

    suspend fun getAllStudentsSync(): List<Student> = studentDao.getAllStudentsSync()
}
