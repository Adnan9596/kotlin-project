package com.studentmanagement.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.studentmanagement.R
import com.studentmanagement.adapter.StudentAdapter
import com.studentmanagement.databinding.ActivityStudentListBinding
import com.studentmanagement.model.Student
import com.studentmanagement.utils.Constants
import com.studentmanagement.viewmodel.StudentViewModel
import com.studentmanagement.viewmodel.StudentViewModelFactory

class StudentListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentListBinding
    private lateinit var viewModel: StudentViewModel
    private lateinit var adapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = StudentViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]

        setupRecyclerView()
        setupSearch()
        setupSortChips()
        observeStudents()

        binding.fabAddStudent.setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }

        // If opened from dashboard Search card
        if (intent.getBooleanExtra("open_search", false)) {
            binding.etSearch.requestFocus()
        }
    }

    private fun setupRecyclerView() {
        adapter = StudentAdapter(
            onViewClick = { student -> navigateToDetail(student) },
            onEditClick = { student -> navigateToEdit(student) },
            onDeleteClick = { student -> confirmDelete(student) }
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setSearchQuery(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupSortChips() {
        binding.chipGroupSort.setOnCheckedStateChangeListener { _: ChipGroup, checkedIds: List<Int> ->
            if (checkedIds.isEmpty()) return@setOnCheckedStateChangeListener
            when (checkedIds[0]) {
                binding.chipSortName.id -> viewModel.setSortMode(Constants.SORT_BY_NAME)
                binding.chipSortMarks.id -> viewModel.setSortMode(Constants.SORT_BY_MARKS)
                binding.chipSortRoll.id -> viewModel.setSortMode(Constants.SORT_BY_ROLL)
            }
        }
    }

    private fun observeStudents() {
        // Observe search and sorted results
        viewModel.studentList.observe(this) { students ->
            adapter.submitList(students)
            binding.layoutEmpty.visibility =
                if (students.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        }
    }

    private fun navigateToDetail(student: Student) {
        val intent = Intent(this, StudentDetailActivity::class.java)
        intent.putExtra(Constants.EXTRA_STUDENT_ID, student.id)
        startActivity(intent)
    }

    private fun navigateToEdit(student: Student) {
        val intent = Intent(this, AddStudentActivity::class.java)
        intent.putExtra(Constants.EXTRA_STUDENT_ID, student.id)
        intent.putExtra(Constants.EXTRA_IS_EDIT_MODE, true)
        startActivity(intent)
    }

    private fun confirmDelete(student: Student) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_confirm_title))
            .setMessage(getString(R.string.delete_confirm_message))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteStudent(student)
                Snackbar.make(binding.root, getString(R.string.student_deleted), Snackbar.LENGTH_LONG).show()
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
