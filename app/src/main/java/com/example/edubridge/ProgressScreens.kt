package com.example.edubridge

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edubridge.ui.theme.BrandGreen
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.Image
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.automirrored.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.Analytics

@Composable
fun ProgressScreen(modifier: Modifier = Modifier, courseProgress: Map<String, Int> = emptyMap()) {
    val totalProgress = if (courseProgress.isEmpty()) 0 else courseProgress.values.average().toInt()
    val completedCourses = courseProgress.filter { it.value == 100 }.size
    
    Column(modifier = modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        TopBrandBar()
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                // Overall Progress Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF8F4))
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Overall Progress", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(24.dp))
                        Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center) {
                            Canvas(modifier = Modifier.fillMaxSize()) {
                                drawArc(
                                    color = Color(0xFFD1E8DF),
                                    startAngle = 0f,
                                    sweepAngle = 360f,
                                    useCenter = false,
                                    style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                                )
                                drawArc(
                                    color = BrandGreen,
                                    startAngle = -90f,
                                    sweepAngle = 360f * (totalProgress / 100f),
                                    useCenter = false,
                                    style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                                )
                            }
                            Text(text = "$totalProgress%", fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, color = BrandGreen)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = if (totalProgress > 0) "You're making great strides this week.\nKeep up the momentum!" else "Start your journey by enrolling in a course!",
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            item {
                // Two small cards
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF8F4))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFFD6E4FF)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.AutoMirrored.Outlined.LibraryBooks, contentDescription = null, tint = Color(0xFF1E40AF), modifier = Modifier.size(16.dp))
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Completed", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "$completedCourses", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Courses", fontSize = 10.sp, color = BrandGreen, fontWeight = FontWeight.Bold)
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF8F4))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier.size(28.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFFD1E8DF)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(imageVector = Icons.Outlined.Analytics, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(16.dp))
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = "Active", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "${courseProgress.size}", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Courses in progress", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }
            }

            item {
                Text(text = "Enrolled Courses", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                
                if (courseProgress.isEmpty()) {
                    Text(text = "No courses enrolled yet.", color = Color.Gray, modifier = Modifier.padding(vertical = 16.dp))
                }
            }

            items(courseProgress.toList()) { (title, progress) ->
                EnrolledCourseCard(
                    title = title,
                    module = if (progress == 100) "Completed" else "In Progress",
                    progress = progress / 100f,
                    imageRes = R.drawable.pinoy_wiring_basics // Ideally this would be dynamic too
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun EnrolledCourseCard(title: String, module: String, progress: Float, imageRes: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF8F4))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, maxLines = 1)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = module, fontSize = 10.sp, color = Color.DarkGray)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(3.dp)).background(Color(0xFFD6E4FF))) {
                        Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(progress).background(BrandGreen))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "${(progress * 100).toInt()}%", fontSize = 12.sp, color = BrandGreen, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
