package com.studentmanagement.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.studentmanagement.model.Student
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object PdfGenerator {

    fun generateStudentReport(context: Context, students: List<Student>): Boolean {
        return try {
            val pdfDocument = PdfDocument()

            // Page size: A4 = 595 x 842 pts at 72 dpi
            val pageWidth = 595
            val rowHeight = 30
            val headerHeight = 60
            val topMargin = 80
            val leftMargin = 20f

            val studentsPerPage = 20
            val totalPages = maxOf(1, Math.ceil(students.size.toDouble() / studentsPerPage).toInt())

            for (pageNum in 0 until totalPages) {
                val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, 842, pageNum + 1).create()
                val page = pdfDocument.startPage(pageInfo)
                val canvas: Canvas = page.canvas

                drawPageContent(canvas, students, pageNum, studentsPerPage, pageWidth, rowHeight,
                    headerHeight, topMargin, leftMargin, totalPages)

                pdfDocument.finishPage(page)
            }

            val success = savePdf(context, pdfDocument)
            pdfDocument.close()
            success
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private fun drawPageContent(
        canvas: Canvas, students: List<Student>, pageNum: Int, studentsPerPage: Int,
        pageWidth: Int, rowHeight: Int, headerHeight: Int, topMargin: Int,
        leftMargin: Float, totalPages: Int
    ) {
        val primaryColor = Color.parseColor("#1565C0")
        val accentColor = Color.parseColor("#FF6D00")
        val lightGray = Color.parseColor("#F5F5F5")
        val darkGray = Color.parseColor("#424242")

        // Title Paint
        val titlePaint = Paint().apply {
            color = primaryColor
            textSize = 22f
            isFakeBoldText = true
        }
        // Subtitle Paint
        val subtitlePaint = Paint().apply {
            color = darkGray
            textSize = 12f
        }
        // Header Paint (table column titles)
        val headerPaint = Paint().apply {
            color = Color.WHITE
            textSize = 11f
            isFakeBoldText = true
        }
        // Cell Paint
        val cellPaint = Paint().apply {
            color = Color.BLACK
            textSize = 10f
        }
        val bgPaint = Paint()

        // Title Block
        bgPaint.color = primaryColor
        canvas.drawRect(0f, 0f, pageWidth.toFloat(), 55f, bgPaint)
        canvas.drawText("Smart Student Management System", leftMargin, 30f, titlePaint.apply { color = Color.WHITE; textSize = 18f })
        canvas.drawText("Student Report  |  Page ${pageNum + 1} of $totalPages", leftMargin, 50f, subtitlePaint.apply { color = Color.WHITE })

        // Table header row
        val tableTop = 65f
        bgPaint.color = accentColor
        canvas.drawRect(0f, tableTop, pageWidth.toFloat(), tableTop + rowHeight, bgPaint)

        val colWidths = intArrayOf(30, 145, 70, 100, 60, 75)
        val headers = arrayOf("#", "Name", "Roll No.", "Department", "Marks", "Attendance")
        var x = leftMargin
        for (i in headers.indices) {
            canvas.drawText(headers[i], x, tableTop + 20f, headerPaint)
            x += colWidths[i]
        }

        // Student rows
        val startIndex = pageNum * studentsPerPage
        val endIndex = minOf(startIndex + studentsPerPage, students.size)
        var y = tableTop + rowHeight

        for (i in startIndex until endIndex) {
            val s = students[i]
            val rowNum = i - startIndex

            // Alternate row background
            if (rowNum % 2 == 0) {
                bgPaint.color = lightGray
                canvas.drawRect(0f, y, pageWidth.toFloat(), y + rowHeight, bgPaint)
            }

            x = leftMargin
            val rowData = arrayOf(
                "${i + 1}",
                s.name.take(20),
                s.rollNumber,
                s.department.take(13),
                "%.1f".format(s.marks),
                "%.1f%%".format(s.attendance)
            )
            for (j in rowData.indices) {
                canvas.drawText(rowData[j], x, y + 20f, cellPaint)
                x += colWidths[j]
            }

            // Divider
            bgPaint.color = Color.parseColor("#E0E0E0")
            canvas.drawRect(0f, y + rowHeight - 1, pageWidth.toFloat(), y + rowHeight.toFloat(), bgPaint)
            y += rowHeight
        }

        // Footer
        val footerPaint = Paint().apply {
            color = Color.GRAY
            textSize = 9f
        }
        canvas.drawText("Generated by Smart Student Management System", leftMargin, 830f, footerPaint)
        canvas.drawText("Total Records: ${students.size}", pageWidth - 130f, 830f, footerPaint)
    }

    private fun savePdf(context: Context, pdfDocument: PdfDocument): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            saveToMediaStore(context, pdfDocument)
        } else {
            saveToExternalStorage(pdfDocument)
        }
    }

    private fun saveToMediaStore(context: Context, pdfDocument: PdfDocument): Boolean {
        val contentValues = ContentValues().apply {
            put(MediaStore.Downloads.DISPLAY_NAME, "StudentReport_${System.currentTimeMillis()}.pdf")
            put(MediaStore.Downloads.MIME_TYPE, "application/pdf")
            put(MediaStore.Downloads.IS_PENDING, 1)
        }

        val resolver = context.contentResolver
        val collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val itemUri = resolver.insert(collection, contentValues) ?: return false

        return try {
            resolver.openOutputStream(itemUri)?.use { outputStream ->
                pdfDocument.writeTo(outputStream)
            }
            contentValues.clear()
            contentValues.put(MediaStore.Downloads.IS_PENDING, 0)
            resolver.update(itemUri, contentValues, null, null)
            true
        } catch (e: Exception) {
            resolver.delete(itemUri, null, null)
            false
        }
    }

    private fun saveToExternalStorage(pdfDocument: PdfDocument): Boolean {
        val dir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            Constants.PDF_DIR_NAME
        )
        if (!dir.exists()) dir.mkdirs()
        val file = File(dir, "StudentReport_${System.currentTimeMillis()}.pdf")
        return try {
            FileOutputStream(file).use { pdfDocument.writeTo(it) }
            true
        } catch (e: Exception) {
            false
        }
    }
}
