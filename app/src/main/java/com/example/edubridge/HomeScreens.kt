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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edubridge.ui.theme.BrandGreen
import com.example.edubridge.ui.theme.BrandGreenLight
import com.example.edubridge.ui.theme.EduBridgeTheme

data class Course(
    val title: String,
    val category: String,
    val level: String,
    val duration: String,
    val progress: Int = 0,
    val rating: Double = 4.8
)

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onOpenCourse: (Course) -> Unit = {}, onSelectTab: (String) -> Unit = {}) {
    val featured = listOf(
        Course("Residential Wiring Basics", "Electrical", "Beginner", "12 Hours", rating = 4.8),
        Course("Network Administration Fundamentals", "ICT", "Intermediate", "24 Hours", rating = 4.9)
    )

    Column(modifier = modifier.fillMaxSize().background(color = BrandGreenLight)) {
        TopBrandBar()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(text = "Welcome back, Alex!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Let's continue mastering your skills.", color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))
                // Search field placeholder
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Search for courses, skills...", color = Color.Gray)
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
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        // Image placeholder
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFcc9b2c)))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Advanced TIG Welding Techniques", fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(6.dp))
                        // progress row
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier
                                .height(8.dp)
                                .fillMaxWidth(0.75f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.LightGray)) {
                                Box(modifier = Modifier
                                    .fillMaxHeight()
                                    .fillMaxWidth(0.65f)
                                    .background(BrandGreen))
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "65%", color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(44.dp), shape = RoundedCornerShape(10.dp)) {
                            Text(text = "Resume")
                        }
                    }
                }
            }

            item {
                Text(text = "Explore Categories", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                                // categories grid (2x2)
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        CategoryCard("Electrical", modifier = Modifier.fillMaxWidth(0.5f))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        CategoryCard("Automotive", modifier = Modifier.fillMaxWidth(0.5f))
                                    }
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        CategoryCard("Welding", modifier = Modifier.fillMaxWidth(0.5f))
                                        Spacer(modifier = Modifier.width(8.dp))
                                        CategoryCard("ICT", modifier = Modifier.fillMaxWidth(0.5f))
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
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
        BottomNavBar(selected = "Home", onSelect = onSelectTab)
    }
}

@Composable
fun CategoryCard(name: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier
        .height(96.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Box(modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFFe6f7ef)))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = name, color = Color.Gray)
        }
    }
}

@Composable
fun CourseCard(course: Course, onClick: () -> Unit = {}) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.DarkGray))
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = course.category, color = BrandGreen, modifier = Modifier.background(Color(0xFFEFF8F4)).padding(6.dp))
                Text(text = course.level, color = Color.Gray, modifier = Modifier.background(Color(0xFFF0F0F0)).padding(6.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = course.title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Learn the fundamentals of safe and effective residential wiring according to standard codes.", color = Color.Gray, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "12 Hours", color = Color.Gray)
                Text(text = "Free", color = BrandGreen, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun CourseDetailScreen(course: Course, modifier: Modifier = Modifier, onStartQuiz: () -> Unit = {}) {
    Box(modifier = modifier.fillMaxSize().background(BrandGreenLight)) {
        Column(modifier = Modifier.align(Alignment.TopStart)) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(Color.LightGray))
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
                    Text(text = "• Design fault-tolerant microservice architectures", color = Color.Gray)
                    Text(text = "• Deploy production-ready Kubernetes clusters", color = Color.Gray)
                    Text(text = "• Implement service meshes with Istio", color = Color.Gray)
                    Text(text = "• Establish robust CI/CD pipelines", color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Course Curriculum", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                ModuleCard("MODULE 1", "Foundations of Distributed Systems", "45 mins")
                Spacer(modifier = Modifier.height(8.dp))
                ModuleCard("MODULE 2", "Docker & Containerization Deep Dive", "1.5 hrs")
                Spacer(modifier = Modifier.height(8.dp))
                ModuleCard("MODULE 3", "Kubernetes Architecture & Core Concepts", "2 hrs")
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
fun ModuleCard(tag: String, title: String, duration: String) {
    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
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
    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = Color.White), modifier = modifier) {
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

