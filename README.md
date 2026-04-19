# 🎓 Smart Student Management System

A modern, feature-rich Android application built with **Kotlin** and **Jetpack** components to manage student records efficiently. The app features a sleek UI, real-time statistics, and powerful data management capabilities.

## 🚀 Key Features

- **🏠 Interactive Dashboard**: Get a quick overview of total students, average marks, and the top-performing student.
- **➕ Student Management**: Add, view, edit, and delete student records with ease.
- **📊 Advanced Statistics**: Detailed analytical views of student performance and grade distributions.
- **🔍 Smart Search & Filter**: Quickly find students by name or roll number.
- **📂 PDF Export**: Generate professional PDF reports of student data directly from the app.
- **🌓 Dark Mode Support**: seamless transition between light and dark themes for better accessibility.

## 🛠 Tech Stack

- **Language**: [Kotlin](https://kotlinlang.org/)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- **UI Components**: Material Design, ViewBinding, CardViews
- **Concurrency**: Kotlin Coroutines & Lifecycle-aware components
- **Reporting**: Custom PDF Generator

## 📱 App Screenshots

| Dashboard | Student List | Statistics |
| :---: | :---: | :---: |
| Overview of all data | Searchable list view | Analytical insights |

*(Note: Add actual screenshots to the `assets/` folder and link them here)*

## 🛠 Getting Started

### Prerequisites

- Android Studio Flamingo or later
- JDK 17
- Android SDK 24+ (Android 7.0 Nougat)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/SmartStudentManagementSystem.git
   ```
2. Open the project in **Android Studio**.
3. Let Gradle sync and download dependencies.
4. Run the app on an Emulator or Physical Device.

## 📁 Project Structure

```text
app/src/main/java/com/studentmanagement/
├── adapter/      # RecyclerView adapters
├── database/     # Room Database & DAOs
├── model/        # Data entities
├── repository/   # Data source management
├── ui/           # Activities & Fragments
├── utils/        # PDF Generator & Constants
└── viewmodel/    # ViewModel logic
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

Built with ❤️ by [Your Name]
