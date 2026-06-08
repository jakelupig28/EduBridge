package com.example.edubridge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.ElectricalServices
import androidx.compose.material.icons.outlined.LaptopMac
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edubridge.ui.theme.BrandGreen

import com.example.edubridge.ui.theme.EduBridgeTheme

data class Course(
    val title: String,
    val category: String,
    val level: String,
    val duration: String,
    val progress: Int = 0,
    val rating: Double = 4.8,
    val image: ImageVector? = null,
    val imageRes: Int? = null
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onOpenCourse: (Course) -> Unit = {},
    onSelectTab: (String) -> Unit = {},
    onCategorySelect: (String) -> Unit = {},
    courseProgress: Map<String, Int> = emptyMap()
) {
    val featured = listOf(
        Course("Residential Wiring Basics", "Electrical", "Beginner", "24 Hours", rating = 4.8, imageRes = R.drawable.residential_wiring_basics, image = Icons.Outlined.ElectricalServices),
        Course("Automotive Diagnostics", "Automotive", "Intermediate", "36 Hours", rating = 4.9, imageRes = R.drawable.automotive, image = Icons.Outlined.DirectionsCar)
    )

    // Find the most recent or in-progress course
    val inProgressCourse = courseProgress.entries.firstOrNull { it.value < 100 }?.key
        ?: courseProgress.keys.firstOrNull()
    
    // Find matching course object (mocking it if not found in featured)
    val continueCourse = featured.find { it.title == inProgressCourse } 
        ?: featured.first() // Fallback to first featured for now
    
    val currentProgress = courseProgress[continueCourse.title] ?: 0

    Column(modifier = modifier.fillMaxSize().background(color = androidx.compose.material3.MaterialTheme.colorScheme.background)) {
        TopBrandBar()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(text = "Welcome back!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Let's continue mastering your skills.", color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))
                // Search field placeholder
                Card(
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Row(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search", tint = Color.Gray, modifier = Modifier.size(24.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Search for courses, skills...", color = Color.Gray, fontSize = 15.sp)
                    }
                }
            }

            item {
                Text(text = "Continue Learning", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // Image representing the course
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFEFF8F4)),
                            contentAlignment = Alignment.Center
                        ) {
                            if (continueCourse.imageRes != null) {
                                Image(
                                    painter = androidx.compose.ui.res.painterResource(id = continueCourse.imageRes!!),
                                    contentDescription = continueCourse.title,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(modifier = Modifier.background(Color(0xFFE1E8FA), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                            Text(text = continueCourse.category, color = Color(0xFF2C5282), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = continueCourse.title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        // progress row
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(text = if (currentProgress == 100) "Completed" else "In Progress", color = Color.Gray, fontSize = 13.sp)
                            Text(text = "$currentProgress%", color = Color.Gray, fontSize = 13.sp)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Box(modifier = Modifier
                            .height(8.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFE2E8F0))) {
                            Box(modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(currentProgress / 100f)
                                .background(BrandGreen))
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { onOpenCourse(continueCourse) }, modifier = Modifier.fillMaxWidth().height(44.dp), shape = RoundedCornerShape(8.dp), colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = BrandGreen)) {
                            Text(text = if (currentProgress == 100) "Review" else "Continue", fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }

            item {
                Text(text = "Explore Categories", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(12.dp))
                // categories grid (2x2)
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CategoryCard("Electrical", 3, Color(0xFFE6F7EF), BrandGreen, Icons.Outlined.ElectricalServices, modifier = Modifier.weight(1f).clickable { onCategorySelect("Electrical") })
                        CategoryCard("Automotive", 2, Color(0xFFEBF8FF), Color(0xFF3182CE), Icons.Outlined.DirectionsCar, modifier = Modifier.weight(1f).clickable { onCategorySelect("Automotive") })
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CategoryCard("Welding", 3, Color(0xFFFFF5F5), Color(0xFFE53E3E), Icons.Outlined.Build, modifier = Modifier.weight(1f).clickable { onCategorySelect("Welding") })
                        CategoryCard("ICT", 4, Color(0xFFE6FFFA), Color(0xFF319795), Icons.Outlined.LaptopMac, modifier = Modifier.weight(1f).clickable { onCategorySelect("ICT") })
                    }
                }
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Featured Courses", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                    Text(text = "View All", color = BrandGreen)
                }
            }

            items(featured) { course ->
                CourseCard(course = course, onClick = { onOpenCourse(course) })
            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun CategoryCard(name: String, coursesCount: Int, bgColor: Color, iconColor: Color, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Box(modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(bgColor), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = name, tint = iconColor, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = name, color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "$coursesCount Courses", color = Color.Gray, fontSize = 12.sp)
        }
    }
}

@Composable
fun CourseCard(course: Course, onClick: () -> Unit = {}) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(Color(0xFFEFF8F4)),
                contentAlignment = Alignment.Center
            ) {
                if (course.imageRes != null) {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(id = course.imageRes),
                        contentDescription = course.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (course.image != null) {
                    Icon(imageVector = course.image, contentDescription = course.title, modifier = Modifier.size(64.dp), tint = BrandGreen)
                }

                // Top Right Badge
                Card(modifier = Modifier.align(Alignment.TopEnd).padding(12.dp), shape = RoundedCornerShape(4.dp), colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface)) {
                    Row(modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.Star, contentDescription = "Rating", tint = Color(0xFFF6AD55), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = course.rating.toString(), fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
                    }
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.background(Color(0xFFEFF8F4), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text(text = course.category, color = BrandGreen, fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                    Box(modifier = Modifier.background(Color(0xFFF1F5F9), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text(text = course.level, color = Color(0xFF475569), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = course.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "Learn the fundamentals of safe and effective residential electrical wiring according to standard codes.", color = Color(0xFF475569), fontSize = 13.sp, lineHeight = 18.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Outlined.Schedule, contentDescription = "Time", tint = Color(0xFF475569), modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = course.duration, color = Color(0xFF475569), fontSize = 13.sp)
                    }
                    Text(text = "Free", color = BrandGreen, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
fun CourseDetailScreen(course: Course, modifier: Modifier = Modifier, onStartQuiz: () -> Unit = {}, onBack: () -> Unit = {}, onOpenLesson: (String) -> Unit = {}) {
    Box(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background)) {
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color(0xFFEFF8F4)),
                contentAlignment = Alignment.Center
            ) {
                if (course.imageRes != null) {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(id = course.imageRes),
                        contentDescription = course.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (course.image != null) {
                    Icon(imageVector = course.image, contentDescription = course.title, modifier = Modifier.size(80.dp), tint = BrandGreen)
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(text = "Advanced", color = BrandGreen, modifier = Modifier.background(Color(0xFFEFF8F4)).padding(6.dp))
                    Text(text = "Architecture", color = Color.Gray, modifier = Modifier.background(Color(0xFFF0F0F0)).padding(6.dp))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = course.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Master the principles of scalable system design. Learn to architect, deploy, and manage highly available containerized applications in production environments.", color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))

                // Instructor row
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.Gray))
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = "Dr. Samuel Chen", fontWeight = FontWeight.SemiBold)
                        Text(text = "Lead Architect at CloudScale", color = Color.Gray, fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                // metrics grid
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MetricCard("DURATION", "48 Hours", modifier = Modifier.fillMaxWidth(0.5f))
                    Spacer(modifier = Modifier.width(8.dp))
                    MetricCard("CONTENT", "12 Modules", modifier = Modifier.fillMaxWidth(0.5f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    MetricCard("CERTIFICATE", "Included", modifier = Modifier.fillMaxWidth(0.5f))
                    Spacer(modifier = Modifier.width(8.dp))
                    MetricCard("RATING", "4.9 (2.4k)", modifier = Modifier.fillMaxWidth(0.5f))
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "What you'll learn", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = "• Safety standards and equipment usage", color = Color.Gray)
                    Text(text = "• Core theoretical principles of the field", color = Color.Gray)
                    Text(text = "• Practical implementation and techniques", color = Color.Gray)
                    Text(text = "• Testing, troubleshooting, and diagnosing issues", color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Course Curriculum", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))

                val coursesModules = when(course.category) {
                    "Electrical" -> listOf(
                        "MODULE 1" to "Safety and Tools",
                        "MODULE 2" to "Circuit Design",
                        "MODULE 3" to "Installation & Maintenance"
                    )
                    "Automotive" -> listOf(
                        "MODULE 1" to "Engine Basics",
                        "MODULE 2" to "Diagnostics & Troubleshooting",
                        "MODULE 3" to "Hybrid and EV Systems"
                    )
                    "Welding" -> listOf(
                        "MODULE 1" to "Welding Safety",
                        "MODULE 2" to "TIG/MIG Fundamentals",
                        "MODULE 3" to "Structural Integrity"
                    )
                    "Carpentry" -> listOf(
                        "MODULE 1" to "Wood Properties & Tools",
                        "MODULE 2" to "Framing Basics",
                        "MODULE 3" to "Finishing Techniques"
                    )
                    else -> listOf(
                        "MODULE 1" to "Networking Basics",
                        "MODULE 2" to "Foundations of Distributed Systems",
                        "MODULE 3" to "Server Architectures"
                    )
                }

                coursesModules.forEach { (tag, title) ->
                    ModuleCard(tag, title, "45 mins", onClick = { onOpenLesson(title) })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        Box(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = onStartQuiz, shape = RoundedCornerShape(12.dp)) {
                    Text(text = "Start Quiz")
                }
                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = {}, shape = RoundedCornerShape(12.dp)) {
                    Text(text = "Enroll Now")
                }
            }
        }
    }
}

@Composable
fun ModuleCard(tag: String, title: String, duration: String, onClick: () -> Unit = {}) {
    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface), modifier = Modifier.clickable { onClick() }.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = tag, color = BrandGreen, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = duration, color = Color.Gray)
        }
    }
}

@Composable
fun MetricCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface), modifier = modifier) {
        Column(modifier = Modifier.padding(12.dp), horizontalAlignment = Alignment.Start) {
            Text(text = title, color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = value, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    EduBridgeTheme {
        HomeScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCourseDetail() {
    EduBridgeTheme {
        CourseDetailScreen(course = Course("Cloud Native Architecture & Kubernetes Microservices", "ICT", "Advanced", "48 Hours"))
    }
}
