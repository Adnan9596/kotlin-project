package com.studentmanagement.ui

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.studentmanagement.R
import com.studentmanagement.databinding.ActivityStatisticsBinding
import com.studentmanagement.viewmodel.StudentViewModel
import com.studentmanagement.viewmodel.StudentViewModelFactory

class StatisticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatisticsBinding
    private lateinit var viewModel: StudentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val factory = StudentViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory)[StudentViewModel::class.java]

        observeStatistics()
    }

    private fun observeStatistics() {
        viewModel.studentCount.observe(this) { count ->
            binding.tvStatTotal.text = (count ?: 0).toString()
        }

        viewModel.averageMarks.observe(this) { avg ->
            binding.tvStatAvg.text = if (avg != null) "%.1f".format(avg) else "—"
        }

        viewModel.highestMarks.observe(this) { high ->
            binding.tvStatHighest.text = if (high != null) "%.1f".format(high) else "—"
        }

        viewModel.lowestMarks.observe(this) { low ->
            binding.tvStatLowest.text = if (low != null) "%.1f".format(low) else "—"
        }

        viewModel.topPerformer.observe(this) { student ->
            binding.tvStatTopPerformer.text = student?.name ?: "—"
            binding.tvStatTopMarks.text = if (student != null) "Marks: ${"%.1f".format(student.marks)}" else "Marks: —"
        }

        viewModel.allStudents.observe(this) { students ->
            if (students.isNullOrEmpty()) return@observe

            // Calculate department breakdown
            val deptMap = students.groupBy { it.department }
            binding.layoutDepartmentStats.removeAllViews()

            val maxCount = deptMap.values.maxOf { it.size }

            deptMap.entries.sortedByDescending { it.value.size }.forEach { entry ->
                val dept = entry.key
                val count = entry.value.size
                val avgMarks = entry.value.map { it.marks }.average()
                val progress = if (maxCount > 0) (count * 100) / maxCount else 0

                // Department label row
                val rowLayout = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).also { it.bottomMargin = 12 }
                }

                // Dept name + count header
                val headerRow = LinearLayout(this).apply {
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER_VERTICAL
                }

                val tvDept = TextView(this).apply {
                    text = dept
                    textSize = 13f
                    setTextColor(ContextCompat.getColor(this@StatisticsActivity, R.color.text_primary))
                    layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                }

                val tvCount = TextView(this).apply {
                    text = "$count students • Avg: ${"%.1f".format(avgMarks)}"
                    textSize = 11f
                    setTextColor(ContextCompat.getColor(this@StatisticsActivity, R.color.text_secondary))
                }

                headerRow.addView(tvDept)
                headerRow.addView(tvCount)

                // Progress bar
                val progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal).apply {
                    max = 100
                    this.progress = progress
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 16
                    ).also { it.topMargin = 4 }
                    progressTintList = android.content.res.ColorStateList.valueOf(
                        ContextCompat.getColor(this@StatisticsActivity, R.color.primary)
                    )
                    progressBackgroundTintList = android.content.res.ColorStateList.valueOf(
                        ContextCompat.getColor(this@StatisticsActivity, R.color.divider)
                    )
                }

                rowLayout.addView(headerRow)
                rowLayout.addView(progressBar)
                binding.layoutDepartmentStats.addView(rowLayout)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}
