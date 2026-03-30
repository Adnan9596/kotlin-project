package com.studentmanagement.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.studentmanagement.databinding.ActivityMainBinding
import com.studentmanagement.utils.Constants
import com.studentmanagement.utils.PdfGenerator
import com.studentmanagement.viewmodel.StudentViewModel
import com.studentmanagement.viewmodel.StudentViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: StudentViewModel
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences(Constants.PREFS_NAME, MODE_PRIVATE)

        // Restore dark mode preference
        val isDark = prefs.getBoolean(Constants.PREF_DARK_MODE, false)
        binding.switchDarkMode.isChecked = isDark

        // ViewModel
        val factory = StudentViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]

        observeStats()
        setupClickListeners()
    }

    private fun observeStats() {
        viewModel.studentCount.observe(this) { count ->
            binding.tvTotalStudents.text = (count ?: 0).toString()
        }
        viewModel.averageMarks.observe(this) { avg ->
            binding.tvAvgMarks.text = if (avg != null) "%.1f".format(avg) else "—"
        }
        viewModel.topPerformer.observe(this) { student ->
            binding.tvTopPerformer.text = student?.name ?: "—"
        }
    }

    private fun setupClickListeners() {
        binding.cardAddStudent.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }

        binding.cardViewStudents.setOnClickListener {
            startActivity(Intent(this, StudentListActivity::class.java))
        }

        binding.cardSearch.setOnClickListener {
            val intent = Intent(this, StudentListActivity::class.java)
            intent.putExtra("open_search", true)
            startActivity(intent)
        }

        binding.cardStatistics.setOnClickListener {
            startActivity(Intent(this, StatisticsActivity::class.java))
        }

        binding.cardExport.setOnClickListener {
            exportPdf()
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean(Constants.PREF_DARK_MODE, isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES
                else AppCompatDelegate.MODE_NIGHT_NO
            )
        }
    }

    private fun exportPdf() {
        lifecycleScope.launch {
            val students = viewModel.getAllStudentsSync()
            if (students.isEmpty()) {
                Toast.makeText(this@MainActivity, "No students to export.", Toast.LENGTH_SHORT).show()
                return@launch
            }
            val success = PdfGenerator.generateStudentReport(this@MainActivity, students)
            val message = if (success) "PDF exported to Downloads!" else "Export failed. Try again."
            Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        }
    }
}
