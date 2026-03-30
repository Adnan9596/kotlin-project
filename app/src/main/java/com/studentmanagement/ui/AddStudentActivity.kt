package com.studentmanagement.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.studentmanagement.R
import com.studentmanagement.databinding.ActivityAddStudentBinding
import com.studentmanagement.model.Student
import com.studentmanagement.utils.Constants
import com.studentmanagement.viewmodel.StudentViewModel
import com.studentmanagement.viewmodel.StudentViewModelFactory
import kotlinx.coroutines.launch

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding
    private lateinit var viewModel: StudentViewModel

    private var editStudentId: Int = -1
    private var isEditMode = false

    private val departments = arrayOf(
        "Computer Science", "Information Technology", "Electronics",
        "Mechanical", "Civil", "Electrical", "Chemical", "BCA", "MCA", "MBA"
    )

    private val semesters = arrayOf(
        "Semester 1", "Semester 2", "Semester 3", "Semester 4",
        "Semester 5", "Semester 6", "Semester 7", "Semester 8"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = StudentViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]

        editStudentId = intent.getIntExtra(Constants.EXTRA_STUDENT_ID, -1)
        isEditMode = intent.getBooleanExtra(Constants.EXTRA_IS_EDIT_MODE, false)

        setupDropdowns()

        if (isEditMode && editStudentId != -1) {
            supportActionBar?.title = "Edit Student"
            binding.btnSave.text = getString(R.string.update_student)
            loadStudentData()
        }

        binding.btnSave.setOnClickListener { saveOrUpdateStudent() }
    }

    private fun setupDropdowns() {
        val deptAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, departments)
        binding.actvDepartment.setAdapter(deptAdapter)

        val semAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, semesters)
        binding.actvSemester.setAdapter(semAdapter)
    }

    private fun loadStudentData() {
        lifecycleScope.launch {
            val student = viewModel.getStudentById(editStudentId) ?: return@launch
            binding.etName.setText(student.name)
            binding.etRollNumber.setText(student.rollNumber)
            binding.etPhone.setText(student.phoneNumber)
            binding.etEmail.setText(student.email)
            binding.actvDepartment.setText(student.department, false)
            binding.actvSemester.setText(student.semester, false)
            binding.etMarks.setText(student.marks.toString())
            binding.etAttendance.setText(student.attendance.toString())
        }
    }

    private fun saveOrUpdateStudent() {
        if (!validateInputs()) return

        val name = binding.etName.text.toString().trim()
        val roll = binding.etRollNumber.text.toString().trim().uppercase()
        val phone = binding.etPhone.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val dept = binding.actvDepartment.text.toString().trim()
        val sem = binding.actvSemester.text.toString().trim()
        val marks = binding.etMarks.text.toString().trim().toDouble()
        val attendance = binding.etAttendance.text.toString().trim().toDouble()

        lifecycleScope.launch {
            // Check for duplicate roll number (skip if edit mode for same student)
            val existing = viewModel.getStudentByRollNumber(roll)
            if (existing != null && existing.id != editStudentId) {
                binding.tilRollNumber.error = getString(R.string.error_roll_duplicate)
                return@launch
            }

            val student = if (isEditMode) {
                Student(editStudentId, name, roll, dept, sem, phone, email, marks, attendance)
            } else {
                Student(name = name, rollNumber = roll, department = dept, semester = sem,
                    phoneNumber = phone, email = email, marks = marks, attendance = attendance)
            }

            if (isEditMode) viewModel.updateStudent(student)
            else viewModel.insertStudent(student)

            val msg = if (isEditMode) getString(R.string.student_updated) else getString(R.string.student_added)
            Toast.makeText(this@AddStudentActivity, msg, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun validateInputs(): Boolean {
        var valid = true

        val name = binding.etName.text.toString().trim()
        val roll = binding.etRollNumber.text.toString().trim()
        val phone = binding.etPhone.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val dept = binding.actvDepartment.text.toString().trim()
        val sem = binding.actvSemester.text.toString().trim()
        val marks = binding.etMarks.text.toString().trim()
        val attendance = binding.etAttendance.text.toString().trim()

        binding.tilName.error = null
        binding.tilRollNumber.error = null
        binding.tilPhone.error = null
        binding.tilEmail.error = null
        binding.tilDepartment.error = null
        binding.tilSemester.error = null
        binding.tilMarks.error = null
        binding.tilAttendance.error = null

        if (name.isEmpty()) { binding.tilName.error = getString(R.string.error_name_required); valid = false }
        if (roll.isEmpty()) { binding.tilRollNumber.error = getString(R.string.error_roll_required); valid = false }
        if (phone.isEmpty()) { binding.tilPhone.error = getString(R.string.error_phone_required); valid = false }
        else if (phone.length != 10) { binding.tilPhone.error = getString(R.string.error_phone_invalid); valid = false }
        if (email.isEmpty()) { binding.tilEmail.error = getString(R.string.error_email_required); valid = false }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = getString(R.string.error_email_invalid); valid = false
        }
        if (dept.isEmpty()) { binding.tilDepartment.error = getString(R.string.error_dept_required); valid = false }
        if (sem.isEmpty()) { binding.tilSemester.error = getString(R.string.error_semester_required); valid = false }
        if (marks.isEmpty()) { binding.tilMarks.error = getString(R.string.error_marks_required); valid = false }
        else {
            val m = marks.toDoubleOrNull()
            if (m == null || m < 0 || m > 100) { binding.tilMarks.error = getString(R.string.error_marks_invalid); valid = false }
        }
        if (attendance.isEmpty()) { binding.tilAttendance.error = getString(R.string.error_attendance_required); valid = false }
        else {
            val a = attendance.toDoubleOrNull()
            if (a == null || a < 0 || a > 100) { binding.tilAttendance.error = getString(R.string.error_attendance_invalid); valid = false }
        }

        return valid
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
