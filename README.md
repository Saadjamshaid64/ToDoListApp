# ✅ **TaskMate – A Simple To-Do List App**

Welcome to TaskMate! This is a lightweight and efficient To-Do List app built using **Java** and **XML** in **Android Studio**. The app allows users to add, edit, delete, and search tasks while ensuring data persistence using internal storage.
***

## 📌 **Project Overview**

TaskMate is designed to help users manage their **daily tasks** easily. It features a smooth UI, a splash screen, and an intuitive task management system. The app stores all tasks using internal storage, eliminating the need for an external database.

### 🛠 **Tech Stack**

- **Android Studio** – Development environment
- **Java** – Core programming language
- **XML** – UI design
- **Internal Storage (File Handling)** – For data persistence
***

## 🎯 **Features**

- **Task Management** – Add, edit, delete, and search tasks
- **Data Persistence** – Uses internal storage (no external database required)
- **Splash Screen** – Automatically transitions to the main task screen
- **User-Friendly Interface** – Simple and lightweight design
- **Efficient Performance** – Works smoothly without lag
***

## 🚀 **Setup Instructions**
### 1️⃣ **Clone the Repository**
```bash
git clone https://github.com/your-username/ToDoListApp
cd ToDoListApp
```

### 2️⃣ **Open in Android Studio**
1. Open **Android Studio**
2. Click Open an **Existing Project**
3. Select the **ToDoListApp** folder

### 3️⃣ **Run the App**
- **Connect an Android device** (or use an emulator)
- Click **Run** ▶️ to start the app
***

## 📂 **Project Structure**
```plaintext
TaskMate/
├── app/src/main/java/com/example/taskmate/
│   ├── MainActivity.java  # Main screen where tasks are managed
│   ├── SplashScreen.java  # Splash screen before launching the app
│   ├── TaskAdapter.java   # Adapter for displaying tasks in RecyclerView
│   ├── TaskModel.java     # Model class for tasks
│   ├── StorageHelper.java # Handles reading/writing tasks to internal storage
│
├── app/src/main/res/layout/
│   ├── activity_main.xml  # UI layout for the main screen
│   ├── activity_splash.xml # UI layout for the splash screen
│   ├── item_task.xml      # UI layout for individual task items
│
├── app/src/main/res/drawable/
│   ├── icon.png           # App icon
│
├── README.md              # Project documentation
└── .gitignore             # Files to ignore in Git
```
***
## 🏗 **How It Works**
1. **Launch the app** – The splash screen appears
2. **Manage tasks** – Add, edit, delete, or search tasks
3. **Data is saved** – Tasks remain stored even after closing the app

