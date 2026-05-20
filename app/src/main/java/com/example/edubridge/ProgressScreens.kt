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
fun ProgressScreen(modifier: Modifier = Modifier) {
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
                                    sweepAngle = 360f * 0.72f,
                                    useCenter = false,
                                    style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                                )
                            }
                            Text(text = "72%", fontWeight = FontWeight.ExtraBold, fontSize = 28.sp, color = BrandGreen)
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "You're making great strides this week.\nKeep up the momentum!",
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
                                Text(text = "Lessons", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "34", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "📈 + 4 this week", fontSize = 10.sp, color = BrandGreen, fontWeight = FontWeight.Bold)
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
                                Text(text = "Avg. Score", fontSize = 14.sp, fontWeight = FontWeight.Medium)
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "88%", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Across 12 modules", fontSize = 10.sp, color = Color.Gray)
                        }
                    }
                }
            }

            item {
                Text(text = "Weekly Activity", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    modifier = Modifier.fillMaxWidth().height(160.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF8F4))
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val heights = listOf(0.4f, 0.6f, 0.9f, 0.7f, 0.5f, 0.3f, 0.15f)
                        val days = listOf("M", "T", "W", "T", "F", "S", "S")

                        heights.forEachIndexed { index, fraction ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Box(modifier = Modifier.width(28.dp).height(100.dp).background(Color(0xFFDEE5E0), RoundedCornerShape(2.dp))) {
                                    Box(
                                        modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction).background(if (index == 2) BrandGreen else Color(0xFF0061A6), RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp, bottomStart = 2.dp, bottomEnd = 2.dp)).align(Alignment.BottomCenter)
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(text = days[index], fontSize = 10.sp, color = if (index == 2) BrandGreen else Color.DarkGray, fontWeight = if (index == 2) FontWeight.Bold else FontWeight.Normal)
                            }
                        }
                    }
                }
            }

            item {
                Text(text = "Enrolled Courses", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                // Advanced Network...
                EnrolledCourseCard(
                    title = "Advanced Network...",
                    module = "Module 4 of 10",
                    progress = 0.4f,
                    imageRes = R.drawable.pinoy_wiring_basics
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Data Analysis...
                EnrolledCourseCard(
                    title = "Data Analysis...",
                    module = "Module 3 of 3",
                    progress = 0.86f,
                    imageRes = R.drawable.pinoy_automotive
                )
                Spacer(modifier = Modifier.height(12.dp))
                // Full-Stack Development
                EnrolledCourseCard(
                    title = "Full-Stack Development",
                    module = "Module 2 of 15",
                    progress = 0.16f,
                    imageRes = R.drawable.pinoy_plumbing
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
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
