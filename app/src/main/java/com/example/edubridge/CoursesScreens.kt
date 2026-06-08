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
        Course("Advanced Commercial Wiring Techniques", "Electrical", "Advanced", "40 Hours", progress = 45, imageRes = R.drawable.advance_commercial_wiring),
        Course("Modern EV Diagnostics & Repair", "Automotive", "Intermediate", "60 Hours", progress = 0, imageRes = R.drawable.modern_ev_diagnostic_and_repair),
        Course("Residential Pipe Systems Fundamentals", "Plumbing", "Beginner", "32 Hours", progress = 0, imageRes = R.drawable.plumbing),
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
            body = """
                Electrical safety is paramount in any technical field. Always follow these core rules:
                
                1. Lockout/Tagout (LOTO): Ensure all power sources are disconnected and locked before beginning work.
                2. Test Before You Touch: Use a multimeter or voltage tester to confirm that a circuit is truly de-energized.
                3. Use Insulated Tools: Only use tools rated for the voltage you are working with (typically 1000V for residential/commercial).
                4. Wear Proper PPE: This includes arc-flash rated clothing, insulated gloves, and safety glasses.
                
                Remember, electricity is invisible and unforgiving. Never skip safety protocols to save time.
            """.trimIndent()
        )
        "Circuit Design" -> LessonContent(
            title = title,
            coreConcept = "Series & Parallel",
            body = """
                Understanding the fundamental difference between series and parallel circuits is essential for any electrical work.
                
                Series Circuits:
                - Components are connected end-to-end.
                - The same current (I) flows through all components.
                - If one component fails, the entire circuit is broken.
                - Total resistance is the sum of all individual resistances.
                
                Parallel Circuits:
                - Components are connected across the same two points.
                - Voltage (V) remains constant across all branches.
                - Current splits between branches based on their individual resistance.
                - If one branch fails, the others continue to operate. This is how most building wiring is designed.
            """.trimIndent()
        )
        "Engine Basics" -> LessonContent(
            title = title,
            coreConcept = "Combustion Cycle",
            body = """
                The modern internal combustion engine operates on the four-stroke cycle, also known as the Otto cycle.
                
                1. Intake: The piston moves down, and the intake valve opens, pulling a fuel-air mixture into the cylinder.
                2. Compression: The intake valve closes, and the piston moves up, compressing the mixture to increase its energy potential.
                3. Power: The spark plug ignites the mixture. The resulting explosion forces the piston down, turning the crankshaft.
                4. Exhaust: The exhaust valve opens, and the piston moves up again to push the spent gases out of the engine.
                
                Mastering these steps allows technicians to diagnose performance issues and mechanical failures accurately.
            """.trimIndent()
        )
        "Diagnostics & Troubleshooting" -> LessonContent(
            title = title,
            coreConcept = "The Scientific Approach",
            body = """
                Effective troubleshooting is a systematic process of elimination. 
                
                - Step 1: Verify the concern. Make sure you understand exactly what the symptom is.
                - Step 2: Visual inspection. Look for obvious signs like loose wires, leaks, or burnt components.
                - Step 3: Scan for codes. Use an OBD-II scanner to pull trouble codes from the vehicle's computer.
                - Step 4: Test components. Use multimeters, pressure gauges, or oscilloscopes to verify the operation of specific parts.
                - Step 5: Root cause analysis. Don't just replace the failed part; understand WHY it failed to prevent a repeat issue.
            """.trimIndent()
        )
        "TIG/MIG Fundamentals" -> LessonContent(
            title = title,
            coreConcept = "Gas Shielded Welding",
            body = """
                MIG (Metal Inert Gas) and TIG (Tungsten Inert Gas) are the two most common advanced welding processes.
                
                MIG Welding:
                - Uses a continuously feeding wire as an electrode and filler.
                - Faster and easier for beginners to learn.
                - Ideal for thicker materials and production work.
                
                TIG Welding:
                - Uses a non-consumable tungsten electrode.
                - Requires manual addition of filler rod with the other hand.
                - Provides the highest quality and precision.
                - Used for exotic metals like aluminum and stainless steel.
            """.trimIndent()
        )
        else -> LessonContent(
            title = title,
            coreConcept = "Core Principles",
            body = "This module covers the essential concepts and theories needed to master the practical skills in this category. Make sure to review the video lecture and complete the assigned practical exercises. Technical mastery requires both theoretical understanding and hands-on practice."
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
        Course("Residential Wiring Basics", "Electrical", "Beginner", "24 Hours", rating = 4.8, imageRes = R.drawable.residential_wiring_basics),
        Course("Advanced Commercial Wiring Techniques", "Electrical", "Advanced", "60 Hours", rating = 4.7, imageRes = R.drawable.advance_commercial_wiring),
        Course("Electrical Grid Maintenance", "Electrical", "Intermediate", "45 Hours", rating = 4.9, imageRes = R.drawable.electrical_grid_maintenance),

        Course("Automotive Diagnostics", "Automotive", "Intermediate", "36 Hours", rating = 4.9, imageRes = R.drawable.automotive),
        Course("Modern EV Diagnostics & Repair", "Automotive", "Advanced", "72 Hours", rating = 4.9, imageRes = R.drawable.modern_ev_diagnostic_and_repair),
        Course("Engine Overhaul Mastery", "Automotive", "Advanced", "120 Hours", rating = 4.6, imageRes = R.drawable.engine_overhaul_mastery),

        Course("Structural Welding Basics", "Welding", "Beginner", "40 Hours", rating = 4.8, imageRes = R.drawable.structural_welding_basics),
        Course("TIG Welding Fundamentals", "Welding", "Intermediate", "56 Hours", rating = 4.7, imageRes = R.drawable.tig_welding_fundamentals),
        Course("Pipeline Welding Pro", "Welding", "Advanced", "80 Hours", rating = 4.9, imageRes = R.drawable.pipeline_welding_pro),

        Course("Network Cable Splicing", "ICT", "Beginner", "30 Hours", rating = 4.5, imageRes = R.drawable.network_cable_splicing),
        Course("Fiber Optic Installation", "ICT", "Intermediate", "48 Hours", rating = 4.9, imageRes = R.drawable.fiber_optic_installation),
        Course("Cloud Native Architecture", "ICT", "Advanced", "64 Hours", rating = 4.8, imageRes = R.drawable.cloud_native_architecture),
        Course("Server Room Maintenance", "ICT", "Intermediate", "40 Hours", rating = 4.6, imageRes = R.drawable.server_room_maintenance)
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
