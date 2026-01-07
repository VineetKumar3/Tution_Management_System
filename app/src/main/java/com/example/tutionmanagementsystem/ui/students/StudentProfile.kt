package com.example.tutionmanagementsystem.ui.students

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfile(
    viewModel: StudentViewModel, // 1. Add ViewModel
    studentId: Int,              // 2. Add studentId
    onBack: () -> Unit,
    onEditStudent: (Int) -> Unit // 3. Update to pass the ID for editing
) {
    // 4. Fetch the specific student's data
    // LaunchedEffect ensures this runs once when studentId changes
    LaunchedEffect(studentId) {
        viewModel.getStudentById(studentId)
    }
    // Observe the LiveData that holds the single student
    val student by viewModel.selectedStudent.observeAsState()

    // Show a loading indicator while the student data is being fetched
    if (student == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        // Once the student data is loaded, display the profile content
        StudentProfileContent(
            student = student!!,
            onBack = onBack,
            onEditStudent = { onEditStudent(studentId) } // Pass the ID when edit is clicked
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileContent(
    student: StudentEntity,
    onBack: () -> Unit,
    onEditStudent: () -> Unit
) {
    val tabs = listOf("Profile", "Fees")
    val pagerState = rememberPagerState(pageCount = { tabs.size })
    val selectedTabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()
    var isActive by remember { mutableStateOf(true) } // You can bind this to the student entity later

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Student Profile",
                        fontFamily = Poppins,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onEditStudent) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit Profile",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Student Photo",
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        // 5. USE REAL DATA
                        Text(
                            text = student.studentName,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        // 6. USE REAL DATA
                        Text(
                            text = "Class ${student.studentClass}",
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (isActive) "Active" else "Inactive",
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp,
                                color = if (isActive) MaterialTheme.colorScheme.primary else Color.Gray,
                                modifier = Modifier.width(80.dp)
                            )
                            Switch(
                                checked = isActive,
                                onCheckedChange = { isActive = it },
                                modifier = Modifier.scale(0.7f),
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                                    uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
                                    uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                modifier = Modifier.padding(horizontal = 8.dp)
            ) {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary,
                    indicator = {},
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            modifier = if (selectedTabIndex == index) Modifier
                                .padding(4.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(12.dp)
                                ) else Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            text = {
                                Text(
                                    text = title,
                                    fontFamily = Poppins,
                                    color = if (selectedTabIndex == index) MaterialTheme.colorScheme.onPrimary else Color.Gray,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
                                )
                            }
                        )
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) { page ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    when (page) {
                        // 7. PASS REAL DATA to the sub-composables
                        0 -> ProfileAndAttendanceContent(student)
                        1 -> FeesContent()
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileAndAttendanceContent(student: StudentEntity) { // Accept student data
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // You would add admissionDate to your StudentEntity to show real data here
        ProfileInfoRow(label = "Admission Date", value = "05 Mar 2021")
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.LightGray.copy(alpha = 0.5f)
        )
        // 8. USE REAL DATA
        ProfileInfoRow(
            label = "Parent Phone", value = student.mobileNumber,
            trailingIcon = {
                IconButton(
                    onClick = { /* Handle call */ },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Call",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            })

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Attendance Details",
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        ProfileInfoRow(label = "Total Classes", value = "50")
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.LightGray.copy(alpha = 0.5f)
        )
        ProfileInfoRow(label = "Classes Attended", value = "45")
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Attendance Percentage",
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            LinearProgressIndicator(
                progress = { 0.9f },
                modifier = Modifier
                    .weight(1f)
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = Color.LightGray
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "90%",
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun FeesContent() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // You would add fee details to your StudentEntity to show real data here
        ProfileInfoRow(label = "Course Fee", value = "₹1500")
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.LightGray.copy(alpha = 0.5f)
        )
        ProfileInfoRow(label = "Actual Fee", value = "₹1200")
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.LightGray.copy(alpha = 0.5f)
        )
        ProfileInfoRow(label = "Pending Amount", value = "₹300")
    }
}

@Composable
fun ProfileInfoRow(
    label: String,
    value: String,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = value,
                fontFamily = Poppins,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            trailingIcon?.invoke()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentProfilePreview() {
    TutionManagementSystemTheme {
        // 9. UPDATE the Preview
        val sampleStudent = StudentEntity(
            studentId = 1,
            studentName = "Rahul Sharma (Preview)",
            studentClass = "10",
            mobileNumber = "9876543210",
            admissionDate = "",
            courseFee = "",
            actualFee = ""
        )
        StudentProfileContent(
            student = sampleStudent,
            onBack = {},
            onEditStudent = {}
        )
    }
}
