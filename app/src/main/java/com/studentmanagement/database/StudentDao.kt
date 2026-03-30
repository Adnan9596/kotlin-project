package com.studentmanagement.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.studentmanagement.model.Student

@Dao
interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudents(): LiveData<List<Student>>

    @Query("SELECT * FROM students WHERE id = :id")
    suspend fun getStudentById(id: Int): Student?

    @Query("SELECT * FROM students WHERE name LIKE '%' || :query || '%' OR rollNumber LIKE '%' || :query || '%' OR department LIKE '%' || :query || '%'")
    fun searchStudents(query: String): LiveData<List<Student>>

    @Query("SELECT * FROM students WHERE rollNumber = :rollNumber LIMIT 1")
    suspend fun getStudentByRollNumber(rollNumber: String): Student?

    @Query("SELECT COUNT(*) FROM students")
    fun getStudentCount(): LiveData<Int>

    @Query("SELECT AVG(marks) FROM students")
    fun getAverageMarks(): LiveData<Double?>

    @Query("SELECT MAX(marks) FROM students")
    fun getHighestMarks(): LiveData<Double?>

    @Query("SELECT MIN(marks) FROM students")
    fun getLowestMarks(): LiveData<Double?>

    @Query("SELECT * FROM students ORDER BY marks DESC LIMIT 1")
    fun getTopPerformer(): LiveData<Student?>

    @Query("SELECT * FROM students ORDER BY name ASC")
    fun getAllStudentsSortedByName(): LiveData<List<Student>>

    @Query("SELECT * FROM students ORDER BY marks DESC")
    fun getAllStudentsSortedByMarks(): LiveData<List<Student>>

    @Query("SELECT * FROM students ORDER BY rollNumber ASC")
    fun getAllStudentsSortedByRoll(): LiveData<List<Student>>

    @Query("SELECT * FROM students")
    suspend fun getAllStudentsSync(): List<Student>
}
