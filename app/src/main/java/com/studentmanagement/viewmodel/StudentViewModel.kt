package com.studentmanagement.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.studentmanagement.database.StudentDatabase
import com.studentmanagement.model.Student
import com.studentmanagement.repository.StudentRepository
import com.studentmanagement.utils.Constants
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: StudentRepository
    private val _sortMode = MutableLiveData(Constants.SORT_BY_NAME)
    private val _searchQuery = MutableLiveData("")

    val allStudents: LiveData<List<Student>>
    val studentCount: LiveData<Int>
    val averageMarks: LiveData<Double?>
    val highestMarks: LiveData<Double?>
    val lowestMarks: LiveData<Double?>
    val topPerformer: LiveData<Student?>

    val searchResults: LiveData<List<Student>>
    val sortedStudents: LiveData<List<Student>>

    private val _operationStatus = MutableLiveData<String>()
    val operationStatus: LiveData<String> get() = _operationStatus

    init {
        val dao = StudentDatabase.getDatabase(application).studentDao()
        repository = StudentRepository(dao)

        allStudents = repository.allStudents
        studentCount = repository.studentCount
        averageMarks = repository.averageMarks
        highestMarks = repository.highestMarks
        lowestMarks = repository.lowestMarks
        topPerformer = repository.topPerformer

        searchResults = _searchQuery.switchMap { query ->
            if (query.isBlank()) repository.allStudents
            else repository.searchStudents(query)
        }

        sortedStudents = _sortMode.switchMap { mode ->
            when (mode) {
                Constants.SORT_BY_MARKS -> repository.getAllStudentsSortedByMarks()
                Constants.SORT_BY_ROLL -> repository.getAllStudentsSortedByRoll()
                else -> repository.getAllStudentsSortedByName()
            }
        }
    }

    fun insertStudent(student: Student) = viewModelScope.launch {
        repository.insertStudent(student)
        _operationStatus.postValue("Student added successfully!")
    }

    fun updateStudent(student: Student) = viewModelScope.launch {
        repository.updateStudent(student)
        _operationStatus.postValue("Student updated successfully!")
    }

    fun deleteStudent(student: Student) = viewModelScope.launch {
        repository.deleteStudent(student)
        _operationStatus.postValue("Student deleted successfully!")
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSortMode(mode: Int) {
        _sortMode.value = mode
    }

    suspend fun getStudentById(id: Int): Student? = repository.getStudentById(id)

    suspend fun getStudentByRollNumber(rollNumber: String): Student? =
        repository.getStudentByRollNumber(rollNumber)

    suspend fun getAllStudentsSync(): List<Student> = repository.getAllStudentsSync()
}
