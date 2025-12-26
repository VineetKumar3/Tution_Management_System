package com.example.tutionmanagementsystem.ui.attendance

import android.app.DatePickerDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tutionmanagementsystem.R
import com.example.tutionmanagementsystem.ui.theme.OnPrimary
import com.example.tutionmanagementsystem.ui.theme.Poppins
import com.example.tutionmanagementsystem.ui.theme.Primary
import com.example.tutionmanagementsystem.ui.theme.TutionManagementSystemTheme
import com.example.tutionmanagementsystem.ui.theme.black
import com.example.tutionmanagementsystem.ui.theme.grey
import com.example.tutionmanagementsystem.ui.theme.white
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// Data class to represent a single student
data class Student(val id: Int, val name: String, val className: String, val imageRes: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendancePage() {
    // A sample list of students. In a real app, this would come from a ViewModel.
    val students = remember {
        listOf(
            Student(1, "Rahul Sharma", "Class 10", R.drawable.logo),
            Student(2, "Aman Singh", "Class 10", R.drawable.logo),
            Student(3, "Neha Verma", "Class 10", R.drawable.logo),
            Student(4, "Rahul Sharma", "Class 10", R.drawable.logo),
            Student(5, "Neha Verma", "Class 10", R.drawable.logo)
        )
    }

    // State for managing the date and the date picker dialog
    val context = LocalContext.current
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("EEE, dd MMM yyyy") }

    // State to hold the attendance status (present/absent) for each student
    var attendance by remember { mutableStateOf(students.associate { it.id to true }) }

    // This is the Date Picker Dialog that will be shown when `showDatePicker` is true
    if (showDatePicker) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            },
            selectedDate.year,
            selectedDate.monthValue - 1,
            selectedDate.dayOfMonth
        )
        datePickerDialog.setOnDismissListener { showDatePicker = false }
        datePickerDialog.show()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Attendance", fontFamily = Poppins, fontWeight = FontWeight.SemiBold, color = black) },
                navigationIcon = { IconButton(onClick = {})
                { Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = black) } },
                actions = {
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            Box(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = { /* Handle save attendance */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Primary, contentColor = Color.White),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Save Attendance",
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontFamily = Poppins,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
            }
        },
        // Set the background color for the entire page to white
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            // The date selector card is now clickable and displays the selected date
            DateSelector(
                selectedDate = selectedDate.format(dateFormatter),
                onDateClick = { showDatePicker = true }
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(students) { student ->
                    StudentAttendanceRow(
                        student = student,
                        isPresent = attendance[student.id] ?: false,
                        onAttendanceChange = {
                            attendance = attendance.toMutableMap().apply { this[student.id] = it }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DateSelector(selectedDate: String, onDateClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDateClick() }, // Make the entire card clickable
        colors = CardDefaults.cardColors(containerColor = Primary)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date", tint = white)
                Spacer(modifier = Modifier.width(8.dp))
                // Display the selected date from the state
                Text(selectedDate, fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = white)
            }
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = white)
        }
    }
}

@Composable
fun StudentAttendanceRow(
    student: Student,
    isPresent: Boolean,
    onAttendanceChange: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth().padding(bottom = 3.dp, top = 3.dp),
        // Set card background to white and add elevation to make it stand out
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = student.imageRes),
                    contentDescription = student.name,
                    modifier = Modifier.size(48.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(student.name, fontFamily = Poppins, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text(student.className, fontFamily = Poppins, color = Color.Gray, fontSize = 14.sp)
                }
            }
            Switch(
                checked = isPresent,
                onCheckedChange = onAttendanceChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Primary,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant,
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AttendancePagePreview() {
    TutionManagementSystemTheme {
        AttendancePage()
    }
}
