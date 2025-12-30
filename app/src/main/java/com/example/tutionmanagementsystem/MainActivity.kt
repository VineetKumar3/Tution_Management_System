package com.example.tutionmanagementsystem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tutionmanagementsystem.ui.attendance.AttendancePage
import com.example.tutionmanagementsystem.ui.dashboard.AddStudentPage
import com.example.tutionmanagementsystem.ui.payments.AddPaymentPage
import com.example.tutionmanagementsystem.ui.payments.FeeHistoryPage
import com.example.tutionmanagementsystem.ui.splash.SplashScreen
import com.example.tutionmanagementsystem.ui.students.StudentList
import com.example.tutionmanagementsystem.ui.students.StudentProfile
import com.example.tutionmanagementsystem.ui.theme.TutionManagementSystemTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TutionManagementSystemTheme {
                //AttendancePage()
                //SplashScreen()
                //AddStudentPage()
                //StudentProfile()
                //StudentList()
                //FeeHistoryPage()
                AddPaymentPage()
            }
        }
    }
}

