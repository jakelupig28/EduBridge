package com.example.edubridge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edubridge.ui.theme.BrandGreen


@Composable
fun CourseCatalogScreen(modifier: Modifier = Modifier, onOpenCourse: (Course) -> Unit = {}) {
    val courses = listOf(
        Course("Advanced Commercial Wiring Techniques", "Electrical", "Advanced", "40 Hours", progress = 45, imageRes = R.drawable.wiring),
        Course("Modern EV Diagnostics & Repair", "Automotive", "Intermediate", "60 Hours", progress = 0, imageRes = R.drawable.ev_diagnostic_course),
        Course("Residential Pipe Systems Fundamentals", "Plumbing", "Beginner", "32 Hours", progress = 0, imageRes = R.drawable.pipe_course),
        Course("Structural Framing Mastery", "Carpentry", "Advanced", "48 Hours", progress = 0, imageRes = R.drawable.framing_course)
    )

    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background)) {
        TopBrandBar()
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Course Catalog", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Explore and enroll in technical certifications.", color = Color(0xFF475569))
            Spacer(modifier = Modifier.height(16.dp))

            // Search and Filter Bar
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Search courses...") },
                    leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
                    modifier = Modifier.weight(1f).height(50.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(unfocusedContainerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface, focusedContainerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {}, shape = RoundedCornerShape(8.dp), modifier = Modifier.size(50.dp), colors = ButtonDefaults.buttonColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface), elevation = ButtonDefaults.buttonElevation(2.dp)) {
                    // Filter icon placeholder
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Chips
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)) {
                    Text("All Courses")
                }
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE2E8F0), contentColor = Color.Black)) {
                    Text("Electrical")
                }
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE2E8F0), contentColor = Color.Black)) {
                    Text("Automotive")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(courses) { course ->
                    CourseCard(course = course, onClick = { onOpenCourse(course) })
                }
                item { Spacer(modifier = Modifier.height(24.dp)) }
            }
        }
    }
}

@Composable
fun LessonScreen(lessonTitle: String, modifier: Modifier = Modifier, onBack: () -> Unit = {}) {
    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.surface)) {
        Box(modifier = Modifier.fillMaxWidth().height(200.dp).background(Color.Black)) {
            // Video placeholder
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Module 3 • 15 Min Read", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = lessonTitle, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Card(colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.background), shape = RoundedCornerShape(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Core Concept", fontWeight = FontWeight.SemiBold)
                    Text(text = "Centralizing application state prevents prop-drilling.", fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "When building complex applications, managing how data flows between different parts...", fontSize = 15.sp, lineHeight = 24.sp)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
            ) {
                Text("Next Lesson ->", fontSize = 16.sp)
            }
        }
    }
}
