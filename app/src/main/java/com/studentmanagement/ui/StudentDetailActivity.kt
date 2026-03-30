package com.studentmanagement.ui

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.studentmanagement.R
import com.studentmanagement.databinding.ActivityStudentDetailBinding
import com.studentmanagement.model.Student
import com.studentmanagement.utils.Constants
import com.studentmanagement.viewmodel.StudentViewModel
import com.studentmanagement.viewmodel.StudentViewModelFactory
import kotlinx.coroutines.launch

class StudentDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailBinding
    private lateinit var viewModel: StudentViewModel
    private var studentId: Int = -1
    private var currentStudent: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        studentId = intent.getIntExtra(Constants.EXTRA_STUDENT_ID, -1)

        val factory = StudentViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]

        loadStudent()

        binding.btnEditStudent.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java).apply {
                putExtra(Constants.EXTRA_STUDENT_ID, studentId)
                putExtra(Constants.EXTRA_IS_EDIT_MODE, true)
            }
            startActivity(intent)
        }

        binding.btnDeleteStudent.setOnClickListener {
            confirmDelete()
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudent()
    }

    private fun loadStudent() {
        lifecycleScope.launch {
            val student = viewModel.getStudentById(studentId)
            if (student != null) {
                currentStudent = student
                populateUI(student)
            } else {
                finish()
            }
        }
    }

    private fun populateUI(student: Student) {
        val initials = student.name.trim().split(" ")
            .filter { it.isNotEmpty() }
            .take(2)
            .joinToString("") { it[0].uppercase() }

        binding.tvAvatarDetail.text = initials
        binding.tvDetailName.text = student.name
        binding.tvDetailRoll.text = "Roll: ${student.rollNumber}"
        binding.tvDetailMarks.text = "%.1f".format(student.marks)
        binding.tvDetailAttendance.text = "${"%.1f".format(student.attendance)}%"
        binding.tvDetailPerformance.text = student.performanceStatus

        // Avatar color
        val avatarColor = when (student.performanceStatus) {
            "Excellent" -> Color.parseColor("#1565C0")
            "Good" -> Color.parseColor("#00897B")
            "Average" -> Color.parseColor("#F57F17")
            else -> Color.parseColor("#C62828")
        }
        binding.cardAvatarDetail.backgroundTintList = ColorStateList.valueOf(avatarColor)

        // Populate detail rows
        setDetailRow(binding.rowDept.root, "Department", student.department)
        setDetailRow(binding.rowSem.root, "Semester", student.semester)
        setDetailRow(binding.rowEmail.root, "Email", student.email)
        setDetailRow(binding.rowPhone.root, "Phone", student.phoneNumber)
        setDetailRow(binding.rowAttStatus.root, "Attendance Status", student.attendanceStatus)
    }

    private fun setDetailRow(view: View, label: String, value: String) {
        view.findViewById<TextView>(R.id.tvLabel).text = label
        view.findViewById<TextView>(R.id.tvValue).text = value
    }

    private fun confirmDelete() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_confirm_title))
            .setMessage(getString(R.string.delete_confirm_message))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                currentStudent?.let {
                    viewModel.deleteStudent(it)
                    Snackbar.make(binding.root, getString(R.string.student_deleted), Snackbar.LENGTH_SHORT).show()
                    finish()
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
