package com.studentmanagement.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.core.content.ContextCompat
import com.studentmanagement.R
import com.studentmanagement.databinding.ItemStudentBinding
import com.studentmanagement.model.Student

class StudentAdapter(
    private val onViewClick: (Student) -> Unit,
    private val onEditClick: (Student) -> Unit,
    private val onDeleteClick: (Student) -> Unit
) : ListAdapter<Student, StudentAdapter.StudentViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val binding = ItemStudentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StudentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class StudentViewHolder(private val binding: ItemStudentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(student: Student) {
            binding.apply {
                tvStudentName.text = student.name
                tvRollNumber.text = "Roll: ${student.rollNumber}"
                tvDepartment.text = student.department
                tvMarks.text = "Marks: ${"%.1f".format(student.marks)}"

                // Avatar initials
                tvAvatar.text = student.name.take(2).uppercase()

                // Performance chip color
                val chipColor = ContextCompat.getColor(binding.root.context, when (student.performanceStatus) {
                    "Excellent" -> R.color.success
                    "Good" -> R.color.primary
                    "Average" -> R.color.warning
                    else -> R.color.error
                })
                tvPerformanceBadge.text = student.performanceStatus
                tvPerformanceBadge.backgroundTintList = ColorStateList.valueOf(chipColor)

                // Avatar background color based on performance
                val avatarColor = ContextCompat.getColor(binding.root.context, when (student.performanceStatus) {
                    "Excellent" -> R.color.primary
                    "Good" -> R.color.card_teal
                    "Average" -> R.color.warning
                    else -> R.color.error
                })
                cardAvatar.backgroundTintList = ColorStateList.valueOf(avatarColor)

                // Attendance indicator
                tvAttendance.text = "${"%.1f".format(student.attendance)}%"
                progressAttendance.progress = student.attendance.toInt()

                btnView.setOnClickListener { onViewClick(student) }
                btnEdit.setOnClickListener { onEditClick(student) }
                btnDelete.setOnClickListener { onDeleteClick(student) }
                root.setOnClickListener { onViewClick(student) }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Student>() {
        override fun areItemsTheSame(oldItem: Student, newItem: Student) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Student, newItem: Student) = oldItem == newItem
    }
}
