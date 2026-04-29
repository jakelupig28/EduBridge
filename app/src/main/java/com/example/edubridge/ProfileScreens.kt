package com.example.edubridge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, onSettings: () -> Unit = {}) {
    Column(modifier = modifier.fillMaxSize().background(BrandGreenLight)) {
        TopBrandBar()
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(120.dp).clip(CircleShape).background(Color.LightGray))
                    Box(modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(BrandGreen)
                        .align(Alignment.End))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Sarah Jenkins", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
                    Text(text = "sarah.jenkins@example.com", color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                StatCard(label = "Courses\nCompleted", value = "12")
                StatCard(label = "Certificates\nEarned", value = "4")
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Account Settings", fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ProfileRow(title = "Settings", onClick = onSettings)
            Spacer(modifier = Modifier.height(10.dp))
            ProfileRow(title = "Help & Support")

            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp)) {
                Text(text = "Logout", color = Color(0xFFB71C1C))
            }
        }

        BottomNavBar(selected = "Profile")
    }
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier, onBack: () -> Unit = {}) {
    val darkMode = remember { mutableStateOf(false) }

    Column(modifier = modifier.fillMaxSize().background(BrandGreenLight)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
                .clickable { onBack() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "<", color = BrandGreen, fontSize = 22.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Settings", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = BrandGreen)
        }

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(52.dp).clip(CircleShape).background(Color.LightGray))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = "Alex Mercer", fontWeight = FontWeight.Bold)
                        Text(text = "alex.mercer@edubridge.edu", color = Color.Gray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "ACCOUNT", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            SettingsCard {
                SettingsRow("Edit Profile")
                SettingsDivider()
                SettingsRow("Change Password")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "PREFERENCES", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            SettingsCard {
                SettingsRow("Notification Preferences")
                SettingsDivider()
                Row(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Dark Mode", modifier = Modifier.weight(1f))
                    Switch(checked = darkMode.value, onCheckedChange = { darkMode.value = it })
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "SUPPORT", color = Color.Gray, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            SettingsCard {
                SettingsRow("Help & Support")
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(14.dp)) {
                Text(text = "Logout")
            }
        }
    }
}

@Composable
fun CoursesEmptyScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().background(BrandGreenLight)) {
        TopBrandBar()
        Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.size(220.dp).clip(CircleShape).background(Color.LightGray))
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "No enrolled courses yet", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your learning journey starts here. Explore our catalog to find certifications and courses that match your professional goals.",
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp)) {
                Text(text = "Browse Courses")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Chip("Data Science")
                Chip("UX Design")
                Chip("Web Development")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Chip("Business Analytics")
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        BottomNavBar(selected = "Courses")
    }
}

@Composable
private fun StatCard(label: String, value: String) {
    Card(modifier = Modifier.fillMaxWidth(0.5f), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFEFF8F4)))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = value, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            Text(text = label, color = Color.Gray, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
    }
}

@Composable
private fun ProfileRow(title: String, onClick: () -> Unit = {}) {
    Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color(0xFFEFF8F4)))
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = title, modifier = Modifier.weight(1f))
            Text(text = ">", color = Color.Gray)
        }
    }
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Card(shape = RoundedCornerShape(14.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
        Column { content() }
    }
}

@Composable
private fun SettingsRow(text: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(28.dp).clip(CircleShape).background(Color(0xFFEFF8F4)))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, modifier = Modifier.weight(1f))
        Text(text = ">", color = Color.Gray)
    }
}

@Composable
private fun SettingsDivider() {
    Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFE8ECEA)))
}

@Composable
private fun Chip(text: String) {
    Box(modifier = Modifier.clip(RoundedCornerShape(14.dp)).background(Color(0xFFEFF4EF)).padding(horizontal = 12.dp, vertical = 6.dp)) {
        Text(text = text, color = Color.Gray)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfile() {
    EduBridgeTheme {
        ProfileScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettings() {
    EduBridgeTheme {
        SettingsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCoursesEmpty() {
    EduBridgeTheme {
        CoursesEmptyScreen()
    }
}

