package com.example.tutionmanagementsystem.ui.students

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutionmanagementsystem.R
import com.example.tutionmanagementsystem.data.entity.StudentEntity
import com.example.tutionmanagementsystem.ui.theme.Poppins
import com.example.tutionmanagementsystem.ui.theme.TutionManagementSystemTheme
import com.example.tutionmanagementsystem.viewmodel.StudentViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentList(
    viewModel: StudentViewModel,
    onNavigateToProfile: (Int) -> Unit, // 1. UPDATE: Change signature to accept an Int
    onBack: () -> Unit,
    onNavigateToAddStudent: () -> Unit
) {
    val studentList by viewModel.allStudents.observeAsState(initial = emptyList())

    StudentListContent(
        studentList = studentList,
        onNavigateToProfile = onNavigateToProfile, // Pass the updated function along
        onBack = onBack,
        onNavigateToAddStudent = onNavigateToAddStudent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListContent(
    studentList: List<StudentEntity>,
    onNavigateToProfile: (Int) -> Unit, // 2. UPDATE: Change signature here too
    onBack: () -> Unit,
    onNavigateToAddStudent: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Student List",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
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
                onClick = { onNavigateToAddStudent() },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Student")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            items(studentList) { student ->
                StudentListItem(
                    student = student,
                    // 3. UPDATE: onItemClick now provides the student object
                    onItemClick = { studentEntity ->
                        // Call the navigation function with the student's ID
                        onNavigateToProfile(studentEntity.studentId)
                    }
                )
            }
        }
    }
}

@Composable
fun StudentListItem(student: StudentEntity, onItemClick: (StudentEntity) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onItemClick(student) }, // This is correct, it passes the whole student up
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Student Image",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = student.studentName,
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Class: ${student.studentClass}",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentListPreview() {
    TutionManagementSystemTheme {
        val sampleStudents = listOf(
            StudentEntity(studentId = 1, studentName = "Vineet Kumar", studentClass = "10th", mobileNumber = "12345", admissionDate = "", courseFee = "", actualFee = ""),
            StudentEntity(studentId = 2, studentName = "Priya Sharma", studentClass = "12th", mobileNumber = "67890", admissionDate = "", courseFee = "", actualFee = "")
        )
        StudentListContent(
            studentList = sampleStudents,
            onNavigateToProfile = {}, // Preview doesn't need to navigate, so an empty lambda is fine
            onNavigateToAddStudent = {},
            onBack = {}
        )
    }
}
