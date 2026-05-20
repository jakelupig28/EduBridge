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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material.icons.outlined.MilitaryTech
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material.icons.outlined.Verified
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edubridge.ui.theme.BrandGreen

import com.example.edubridge.ui.theme.EduBridgeTheme

data class Certificate(
    val title: String,
    val description: String,
    val date: String,
    val icon: ImageVector,
    val isPrimary: Boolean = false
)

@Composable
fun CertificatesScreen(modifier: Modifier = Modifier, onSelect: (Certificate) -> Unit = {}, onSelectTab: (String) -> Unit = {}) {
    val certificates = listOf(
        Certificate("Advanced Web Architecture", "Mastery in scalable system design, microservices, and modern frontend frameworks.", "Oct 24, 2023", Icons.Outlined.WorkspacePremium, true),
        Certificate("UI/UX Design Fundamentals", "Core principles of user-centered design, wireframing, and interactive prototyping.", "Aug 12, 2023", Icons.Outlined.MilitaryTech, false),
        Certificate("Data Structures & Algorithms", "Comprehensive understanding of algorithmic efficiency and complex data structures.", "May 05, 2023", Icons.Outlined.Verified, false),
        Certificate("Cloud Infrastructure Basics", "Foundational knowledge of cloud computing, deployment models, and serverless architecture.", "Jan 18, 2023", Icons.Outlined.Stars, false)
    )

    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background)) {
        TopBrandBar()
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "My Certificates", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your verified technical skills and achievements. Download or share your certificates to showcase your expertise.",
                    color = Color.DarkGray,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
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

    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background)) {
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
    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background)) {
        TopBrandBar()
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onBack() }) {
                Text(text = "← Back to Certificates", color = BrandGreen, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8EFE9))
            ) {
                Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    Card(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(0.dp),
                        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            // Corner Accents
                            val stroke = 2.dp
                            val length = 24.dp

                            // Top Left
                            Box(modifier = Modifier.align(Alignment.TopStart).padding(start = 16.dp, top = 16.dp)) {
                                Box(modifier = Modifier.height(stroke).width(length).background(BrandGreen))
                                Box(modifier = Modifier.width(stroke).height(length).background(BrandGreen))
                            }
                            // Top Right
                            Box(modifier = Modifier.align(Alignment.TopEnd).padding(end = 16.dp, top = 16.dp)) {
                                Box(modifier = Modifier.height(stroke).width(length).background(BrandGreen).align(Alignment.TopEnd))
                                Box(modifier = Modifier.width(stroke).height(length).background(BrandGreen).align(Alignment.TopEnd))
                            }
                            // Bottom Left
                            Box(modifier = Modifier.align(Alignment.BottomStart).padding(start = 16.dp, bottom = 16.dp)) {
                                Box(modifier = Modifier.height(stroke).width(length).background(BrandGreen).align(Alignment.BottomStart))
                                Box(modifier = Modifier.width(stroke).height(length).background(BrandGreen).align(Alignment.BottomStart))
                            }
                            // Bottom Right
                            Box(modifier = Modifier.align(Alignment.BottomEnd).padding(end = 16.dp, bottom = 16.dp)) {
                                Box(modifier = Modifier.height(stroke).width(length).background(BrandGreen).align(Alignment.BottomEnd))
                                Box(modifier = Modifier.width(stroke).height(length).background(BrandGreen).align(Alignment.BottomEnd))
                            }

                            Column(modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Icon(imageVector = Icons.Filled.WorkspacePremium, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(56.dp))
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(text = "CERTIFICATE", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, letterSpacing = 2.sp)
                                Text(text = "OF", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, letterSpacing = 2.sp)
                                Text(text = "COMPLETION", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, letterSpacing = 2.sp)
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(text = "THIS IS PROUDLY", color = Color.DarkGray, fontSize = 12.sp, letterSpacing = 1.sp)
                                Text(text = "PRESENTED TO", color = Color.DarkGray, fontSize = 12.sp, letterSpacing = 1.sp)
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(text = "Alex", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = BrandGreen)
                                Text(text = "Rivera", fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = BrandGreen)
                                Spacer(modifier = Modifier.height(24.dp))
                                Text(
                                    text = "for successfully completing the rigorous academic requirements and demonstrating mastery in",
                                    color = Color.DarkGray,
                                    textAlign = TextAlign.Center,
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(text = "Advanced Systems", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)
                                Text(text = "Architecture", fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)

                                Spacer(modifier = Modifier.weight(1f))
                                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFE5ECE6)))
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "October 24,\n2023", textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.Bold, lineHeight = 16.sp)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Date of Issuance", color = Color.DarkGray, fontSize = 10.sp, fontWeight = FontWeight.Medium)
                                    }
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text(text = "🖋️", color = Color.Gray, fontSize = 20.sp)
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Program Director", color = Color.DarkGray, fontSize = 10.sp, fontWeight = FontWeight.Medium)
                                    }
                                }
                                Spacer(modifier = Modifier.height(16.dp))
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Outlined.Download, contentDescription = "Download", tint = Color.White, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Download PDF", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, BrandGreen),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = BrandGreen)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Outlined.Share, contentDescription = "Share", tint = BrandGreen, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Share to LinkedIn", color = BrandGreen, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun CertificateCard(certificate: Certificate, onView: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(44.dp).clip(CircleShape).background(Color(0xFFE8F3EE)), contentAlignment = Alignment.Center) {
                    Icon(imageVector = certificate.icon, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(24.dp))
                }
                Box(modifier = Modifier.clip(RoundedCornerShape(6.dp)).background(Color(0xFFE8F3EE)).padding(horizontal = 10.dp, vertical = 6.dp)) {
                    Text(text = certificate.date, fontSize = 10.sp, color = Color.DarkGray, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = certificate.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = certificate.description, color = Color.DarkGray, fontSize = 12.sp, lineHeight = 18.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (certificate.isPrimary) {
                    Button(
                        onClick = onView,
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BrandGreen)
                    ) {
                        Text(text = "View Certificate", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    }
                } else {
                    OutlinedButton(
                        onClick = onView,
                        modifier = Modifier.weight(1f).height(44.dp),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, BrandGreen),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = BrandGreen)
                    ) {
                        Text(text = "View Certificate", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(modifier = Modifier.width(12.dp))
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.size(44.dp),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(0.dp),
                    border = BorderStroke(1.dp, Color(0xFFD1E8DF))
                ) {
                    Icon(imageVector = Icons.Outlined.Download, contentDescription = "Download", tint = BrandGreen, modifier = Modifier.size(20.dp))
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
        colors = CardDefaults.cardColors(containerColor = if (item.unread) androidx.compose.material3.MaterialTheme.colorScheme.surface else Color(0xFFE5ECE6))
    ) {
        Row {
            Box(modifier = Modifier.width(4.dp).fillMaxHeight().background(if (item.unread) BrandGreen else Color.Transparent))
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.Top) {
                Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(item.color), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier.size(18.dp).clip(RoundedCornerShape(4.dp)).background(androidx.compose.material3.MaterialTheme.colorScheme.surface))
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
