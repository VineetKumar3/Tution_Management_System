package com.example.tutionmanagementsystem.ui.payments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutionmanagementsystem.ui.theme.Poppins
import com.example.tutionmanagementsystem.ui.theme.TutionManagementSystemTheme

data class Student(val id: String, val name: String)
data class StudentPayment(
    val student: Student,
    val date: String,
    val amount: String,
    val status: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeeHistoryPage() {
    val students = listOf(
        Student("1", "Student A"),
        Student("2", "Student B"),
    )
    val payments = listOf(
        StudentPayment(students[0], "2024-07-15", "₹5000", "Paid"),
        StudentPayment(students[0], "2024-06-15", "₹5000", "Paid"),
        StudentPayment(students[1], "2024-07-15", "₹5000", "Pending"),
        StudentPayment(students[1], "2024-06-15", "₹5000", "Paid"),
    )

    val paymentsByStudent = payments.groupBy { it.student }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Fee History",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back */ }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Payment")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(paymentsByStudent.entries.toList()) { (student, studentPayments) ->
                StudentPaymentsItem(student, studentPayments)
            }
        }
    }
}

@Composable
fun StudentPaymentsItem(student: Student, payments: List<StudentPayment>) {
    var expanded by remember { mutableStateOf(false) }
    val paymentsByMonth = payments.groupBy { it.date.substring(0, 7) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = student.name,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            if (expanded) {
                paymentsByMonth.entries.forEach { (month, monthPayments) ->
                    MonthPaymentsItem(month, monthPayments)
                }
            }
        }
    }
}

@Composable
fun MonthPaymentsItem(month: String, payments: List<StudentPayment>) {
    Column(modifier = Modifier.padding(top = 8.dp)) {
        Text(
            text = "Month: $month",
            fontFamily = Poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        payments.forEach { payment ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = payment.date,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
                Text(
                    text = payment.amount,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
                Text(
                    text = payment.status,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = if (payment.status == "Paid") MaterialTheme.colorScheme.primary else Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeeHistoryPagePreview() {
    TutionManagementSystemTheme {
        FeeHistoryPage()
    }
}