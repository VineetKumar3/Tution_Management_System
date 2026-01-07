package com.example.tutionmanagementsystem

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType // 1. IMPORT NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument // 2. IMPORT navArgument
import com.example.tutionmanagementsystem.application.TuitionApplication
import com.example.tutionmanagementsystem.ui.attendance.AttendancePage
import com.example.tutionmanagementsystem.ui.backup.BackupRestorePage
import com.example.tutionmanagementsystem.ui.dashboard.Dashboard
import com.example.tutionmanagementsystem.ui.notification.NotificationPage
import com.example.tutionmanagementsystem.ui.payments.AddPaymentPage
import com.example.tutionmanagementsystem.ui.splash.SplashScreen
import com.example.tutionmanagementsystem.ui.students.AddStudentPage
import com.example.tutionmanagementsystem.ui.students.StudentList
import com.example.tutionmanagementsystem.ui.students.StudentProfile
import com.example.tutionmanagementsystem.viewmodel.StudentViewModel
import com.example.tutionmanagementsystem.viewmodel.StudentViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val application = context.applicationContext as TuitionApplication

    val viewModelFactory = StudentViewModelFactory(application.repository)

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onSplashFinished = { navController.navigate("dashboard")
            { popUpTo("splash") { inclusive = true } } })
        }
        composable("dashboard") {
            Dashboard(onNavigate = { route -> navController.navigate(route) })
        }
        composable("students") {
            val studentViewModel: StudentViewModel = viewModel(factory = viewModelFactory)
            StudentList(
                viewModel = studentViewModel,
                // 3. UPDATE to pass the student's ID when navigating
                onNavigateToProfile = { studentId ->
                    navController.navigate("student_profile/$studentId")
                },
                onNavigateToAddStudent = {
                    // Navigate to add_student without an ID for creating a new one
                    navController.navigate("add_student")
                },
                onBack = { navController.popBackStack() }
            )
        }

        // 4. UPDATE the student_profile route to accept a mandatory studentId
        composable(
            route = "student_profile/{studentId}",
            arguments = listOf(navArgument("studentId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract the ID and pass it to the Profile screen
            val studentId = backStackEntry.arguments?.getInt("studentId")
            if (studentId != null) {
                val studentViewModel: StudentViewModel = viewModel(factory = viewModelFactory)
                StudentProfile(
                    viewModel = studentViewModel,
                    studentId = studentId,
                    onEditStudent = { id ->
                        // Navigate to the edit screen with the correct ID
                        navController.navigate("add_student/$id")
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }

        // 5. UPDATE the add_student route to accept an optional studentId
        // This allows it to be used for both "add" (no ID) and "edit" (with ID)
        composable(
            route = "add_student?studentId={studentId}",
            arguments = listOf(navArgument("studentId") {
                type = NavType.IntType
                defaultValue = -1 // -1 will signify a new student
            })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getInt("studentId")
            val studentViewModel: StudentViewModel = viewModel(factory = viewModelFactory)
            AddStudentPage(
                viewModel = studentViewModel,
                // Pass the ID, which will be -1 for new students
                studentId = studentId ?: -1,
                onBack = { navController.popBackStack() }
            )
        }

        composable("add_payment") {
            AddPaymentPage(onBack = { navController.popBackStack() })
        }
        composable("notifications") {
            NotificationPage(onBack = { navController.popBackStack() })
        }
        composable ("attendance") {
            AttendancePage(onBack = { navController.popBackStack() })
        }
        composable("backup_restore") {
            BackupRestorePage(onBack = { navController.popBackStack() })
        }
    }
}
