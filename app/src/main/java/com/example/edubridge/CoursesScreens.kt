package com.example.edubridge

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import android.annotation.SuppressLint
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun CourseCatalogScreen(modifier: Modifier = Modifier, onOpenCourse: (Course) -> Unit = {}) {
    val courses = listOf(
        Course("Advanced Commercial Wiring Techniques", "Electrical", "Advanced", "40 Hours", progress = 45, imageRes = R.drawable.pinoy_wiring_basics),
        Course("Modern EV Diagnostics & Repair", "Automotive", "Intermediate", "60 Hours", progress = 0, imageRes = R.drawable.pinoy_automotive),
        Course("Residential Pipe Systems Fundamentals", "Plumbing", "Beginner", "32 Hours", progress = 0, imageRes = R.drawable.pinoy_plumbing),
        Course("Structural Framing Mastery", "Carpentry", "Advanced", "48 Hours", progress = 0, imageRes = R.drawable.pinoy_structural_framing_mastery)
    )

    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background)) {
        TopBrandBar()
        
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(16.dp))
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
            }

            items(courses) { course ->
                CourseCard(course = course, onClick = { onOpenCourse(course) })
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun VideoPlayer(videoUrl: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false
                webChromeClient = WebChromeClient()
                val html = """
                    <html>
                    <body style="margin:0;padding:0;background:black;display:flex;justify-content:center;align-items:center;">
                        <video width="100%" height="100%" controls>
                            <source src="$videoUrl" type="video/mp4">
                        </video>
                    </body>
                    </html>
                """.trimIndent()
                loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

data class LessonContent(
    val title: String,
    val coreConcept: String,
    val body: String,
    val videoUrl: String = "https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4"
)

fun getLesson(title: String): LessonContent {
    return when(title) {
        "Safety and Tools" -> LessonContent(
            title = title,
            coreConcept = "Electrical Safety First",
            body = "1. Always de-energize circuits before working on them.\n2. Use tested, insulated tools.\n3. Wear appropriate personal protective equipment (PPE)."
        )
        "Circuit Design" -> LessonContent(
            title = title,
            coreConcept = "Series & Parallel",
            body = "In a series circuit, components are connected in a single path, so the same current flows through all of them. In a parallel circuit, paths branch out, so voltage remains the same across branches but current differs."
        )
        "Engine Basics" -> LessonContent(
            title = title,
            coreConcept = "Combustion Cycle",
            body = "The four steps of the Otto cycle are Intake, Compression, Power, and Exhaust. Understanding this is key to all internal combustion engine repair."
        )
        else -> LessonContent(
            title = title,
            coreConcept = "Core Principles",
            body = "This module covers the essential concepts and theories needed to master the practical skills in this category. Make sure to review the video lecture and complete the assigned practical exercises."
        )
    }
}

@Composable
fun LessonScreen(lessonTitle: String, modifier: Modifier = Modifier, onBack: () -> Unit = {}) {
    val lesson = getLesson(lessonTitle)
    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.surface)) {
        Box(modifier = Modifier.fillMaxWidth().height(220.dp).background(Color.Black)) {
            VideoPlayer(videoUrl = lesson.videoUrl)
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Module Section • Lecture", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = lesson.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Card(colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.background), shape = RoundedCornerShape(8.dp)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Core Concept", fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = lesson.coreConcept, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = lesson.body, fontSize = 15.sp, lineHeight = 24.sp)
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
            ) {
                Text("Next Module ->", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Complete Lesson & Return")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetailScreen(
    category: String,
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onOpenCourse: (Course) -> Unit = {}
) {
    val allCourses = listOf(
        Course("Residential Wiring Basics", "Electrical", "Beginner", "12 Hours", rating = 4.8, imageRes = R.drawable.pinoy_wiring_basics),
        Course("Advanced Commercial Wiring Techniques", "Electrical", "Advanced", "40 Hours", rating = 4.7),
        Course("Electrical Grid Maintenance", "Electrical", "Intermediate", "20 Hours", rating = 4.9),

        Course("Automotive Diagnostics", "Automotive", "Intermediate", "24 Hours", rating = 4.9, imageRes = R.drawable.pinoy_automotive),
        Course("Modern EV Diagnostics & Repair", "Automotive", "Advanced", "60 Hours", rating = 4.9),
        Course("Engine Overhaul Mastery", "Automotive", "Advanced", "120 Hours", rating = 4.6),

        Course("Structural Welding Basics", "Welding", "Beginner", "24 Hours", rating = 4.8),
        Course("TIG Welding Fundamentals", "Welding", "Intermediate", "32 Hours", rating = 4.7),
        Course("Pipeline Welding Pro", "Welding", "Advanced", "60 Hours", rating = 4.9),

        Course("Network Cable Splicing", "ICT", "Beginner", "16 Hours", rating = 4.5),
        Course("Fiber Optic Installation", "ICT", "Intermediate", "40 Hours", rating = 4.9),
        Course("Cloud Native Architecture", "ICT", "Advanced", "48 Hours", rating = 4.8),
        Course("Server Room Maintenance", "ICT", "Intermediate", "30 Hours", rating = 4.6)
    )

    val categoryCourses = allCourses.filter { it.category.equals(category, ignoreCase = true) }

    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background)) {
        TopAppBar(
            title = { Text("$category Courses", fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(androidx.compose.material.icons.Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface)
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyColumn(
            modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(text = "Available in $category", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
            }
            items(categoryCourses) { course ->
                CourseCard(course = course, onClick = { onOpenCourse(course) })
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}
