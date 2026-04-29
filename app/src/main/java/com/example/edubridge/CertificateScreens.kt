package com.example.edubridge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edubridge.ui.theme.BrandGreen
import com.example.edubridge.ui.theme.BrandGreenLight
import com.example.edubridge.ui.theme.EduBridgeTheme

data class Certificate(
    val title: String,
    val description: String,
    val date: String
)

@Composable
fun CertificatesScreen(modifier: Modifier = Modifier, onSelect: (Certificate) -> Unit = {}, onSelectTab: (String) -> Unit = {}) {
    val certificates = listOf(
        Certificate("Advanced Web Architecture", "Mastery in scalable system design, microservices, and modern frontend frameworks.", "Oct 24, 2023"),
        Certificate("UI/UX Design Fundamentals", "Core principles of user-centered design, wireframing, and interactive prototyping.", "Aug 12, 2023"),
        Certificate("Data Structures & Algorithms", "Comprehensive understanding of algorithmic efficiency and complex data structures.", "May 05, 2023"),
        Certificate("Cloud Infrastructure Basics", "Foundational knowledge of cloud computing, deployment models, and serverless architecture.", "Jan 18, 2023")
    )

    Column(modifier = modifier.fillMaxSize().background(BrandGreenLight)) {
        TopBrandBar()
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "My Certificates", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Your verified technical skills and achievements. Download or share your certificates to showcase your expertise.",
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(certificates) { cert ->
                CertificateCard(certificate = cert, onView = { onSelect(cert) })
            }

            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
        BottomNavBar(selected = "Certificates", onSelect = onSelectTab)
    }
}

@Composable
fun NotificationsScreen(modifier: Modifier = Modifier) {
    val items = listOf(
        NotificationItem("New module available", "\"Advanced Network Security Protocols\" is now unlocked in your current track.", "2h ago", Color(0xFF19B375), true),
        NotificationItem("Quiz reminder", "Your final assessment for \"Data Structures\" is due tomorrow at 11:59 PM.", "5h ago", Color(0xFF5BA0FF), true),
        NotificationItem("Certificate earned!", "Congratulations! You've successfully completed the IT Support Specialist certification.", "1d ago", Color(0xFFF28B82), false),
        NotificationItem("Platform Maintenance", "EduBridge will undergo scheduled maintenance this Sunday from 2 AM to 4 AM EST.", "3d ago", Color(0xFFD9D9D9), false)
    )

    Column(modifier = modifier.fillMaxSize().background(BrandGreenLight)) {
        TopBrandBar()
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(text = "Notifications", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                    Text(text = "You have 2 unread alerts.", color = Color.Gray)
                }
                Text(text = "Mark all as read", color = BrandGreen)
            }
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(items) { item ->
                    NotificationCard(item = item)
                }
            }
        }
    }
}

@Composable
fun CertificateDetailScreen(modifier: Modifier = Modifier, onBack: () -> Unit = {}) {
    Column(modifier = modifier.fillMaxSize().background(BrandGreenLight)) {
        TopBrandBar()
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onBack() }) {
                Text(text = "← Back to Certificates", color = BrandGreen)
            }
            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth().height(520.dp),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE5ECE6))
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(12.dp)) {
                    Card(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFEFF8F4)))
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "CERTIFICATE", fontWeight = FontWeight.ExtraBold)
                            Text(text = "OF", fontWeight = FontWeight.ExtraBold)
                            Text(text = "COMPLETION", fontWeight = FontWeight.ExtraBold)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "THIS IS PROUDLY", color = Color.Gray, letterSpacing = 1.sp)
                            Text(text = "PRESENTED TO", color = Color.Gray, letterSpacing = 1.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Alex", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = BrandGreen)
                            Text(text = "Rivera", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = BrandGreen)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "for successfully completing the rigorous academic requirements and demonstrating mastery in",
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(text = "Advanced Systems", fontWeight = FontWeight.ExtraBold)
                            Text(text = "Architecture", fontWeight = FontWeight.ExtraBold)
                            Spacer(modifier = Modifier.height(24.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Column {
                                    Text(text = "October 24,\n2023", textAlign = TextAlign.Center)
                                    Text(text = "Date of Issuance", color = Color.Gray, fontSize = 12.sp)
                                }
                                Column(horizontalAlignment = Alignment.End) {
                                    Text(text = "\u007e\u007e\u007e", color = Color.Gray)
                                    Text(text = "Program Director", color = Color.Gray, fontSize = 12.sp)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(12.dp)) {
                Text(text = "Download PDF")
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(12.dp)) {
                Text(text = "Share to LinkedIn")
            }
        }
    }
}

@Composable
private fun CertificateCard(certificate: Certificate, onView: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color(0xFFEFF8F4)))
                Box(modifier = Modifier.clip(RoundedCornerShape(8.dp)).background(Color(0xFFEFF4E6)).padding(horizontal = 10.dp, vertical = 4.dp)) {
                    Text(text = certificate.date, fontSize = 12.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = certificate.title, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = certificate.description, color = Color.Gray)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = onView, modifier = Modifier.weight(1f).height(42.dp), shape = RoundedCornerShape(10.dp)) {
                    Text(text = "View Certificate")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier.size(42.dp).clip(RoundedCornerShape(10.dp)).background(Color(0xFFEFF8F4)), contentAlignment = Alignment.Center) {
                    Text(text = "↓", color = BrandGreen)
                }
            }
        }
    }
}

// shared TopBrandBar and BottomNavBar are in AppChrome.kt

data class NotificationItem(
    val title: String,
    val message: String,
    val time: String,
    val color: Color,
    val unread: Boolean
)

@Composable
private fun NotificationCard(item: NotificationItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = if (item.unread) Color.White else Color(0xFFE5ECE6))
    ) {
        Row {
            Box(modifier = Modifier.width(4.dp).fillMaxHeight().background(if (item.unread) BrandGreen else Color.Transparent))
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(item.color), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.size(18.dp).clip(RoundedCornerShape(4.dp)).background(Color.White))
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = item.title, fontWeight = FontWeight.SemiBold)
                        Text(text = item.time, color = Color.Gray, fontSize = 12.sp)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = item.message, color = Color.Gray)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCertificates() {
    EduBridgeTheme {
        CertificatesScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNotifications() {
    EduBridgeTheme {
        NotificationsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCertificateDetail() {
    EduBridgeTheme {
        CertificateDetailScreen()
    }
}


