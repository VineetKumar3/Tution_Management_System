package com.example.tutionmanagementsystem

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tutionmanagementsystem.ui.attendance.AttendancePage
import com.example.tutionmanagementsystem.ui.backup.BackupRestorePage
import com.example.tutionmanagementsystem.ui.students.AddStudentPage
import com.example.tutionmanagementsystem.ui.dashboard.Dashboard
import com.example.tutionmanagementsystem.ui.notification.NotificationPage
import com.example.tutionmanagementsystem.ui.payments.AddPaymentPage
import com.example.tutionmanagementsystem.ui.splash.SplashScreen
import com.example.tutionmanagementsystem.ui.students.StudentList
import com.example.tutionmanagementsystem.ui.students.StudentProfile

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onSplashFinished = { navController.navigate("dashboard")
            { popUpTo("splash") { inclusive = true } } })
        }
        composable("dashboard") {
            Dashboard(onNavigate = { route -> navController.navigate(route) })
        }
        composable("students") {
            StudentList(
                onNavigateToProfile = { navController.navigate("student_profile") },
                onNavigateToAddStudent = { navController.navigate("add_student") },
                onBack = { navController.popBackStack() }
            )
        }
        composable("student_profile") {
            StudentProfile(
                onEditStudent = { navController.navigate("add_student")},
                onBack = { navController.popBackStack() }
            )
        }
        composable("add_student") {
            AddStudentPage(onBack = { navController.popBackStack() })
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
