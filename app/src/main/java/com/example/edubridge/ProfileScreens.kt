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
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.ui.layout.ContentScale
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

import com.example.edubridge.ui.theme.EduBridgeTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, onSettings: () -> Unit = {}) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val displayName = user?.displayName?.takeIf { it.isNotBlank() } ?: "Sarah Jenkins"
    val email = user?.email ?: "sarah.jenkins@example.com"

    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background)) {
        TopBrandBar()
        Column(modifier = Modifier.weight(1f).padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(modifier = Modifier.size(120.dp), contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                                .background(Color(0xFFE2E8F0))
                                .border(3.dp, androidx.compose.material3.MaterialTheme.colorScheme.surface, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Outlined.Person, contentDescription = "Profile", modifier = Modifier.size(64.dp), tint = Color.Gray)
                        }
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(BrandGreen)
                                .border(2.dp, androidx.compose.material3.MaterialTheme.colorScheme.surface, CircleShape)
                                .align(Alignment.BottomEnd),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = Icons.Filled.Edit, contentDescription = "Edit", tint = androidx.compose.material3.MaterialTheme.colorScheme.surface, modifier = Modifier.size(16.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = displayName, fontWeight = FontWeight.Bold, fontSize = 24.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = email, color = Color(0xFF475569), fontSize = 15.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                StatCard(modifier = Modifier.weight(1f), icon = Icons.Outlined.School, iconTint = Color(0xFF1E40AF), bgTint = Color(0xFFEFF6FF), label = "Courses\nCompleted", value = "12")
                StatCard(modifier = Modifier.weight(1f), icon = Icons.Outlined.EmojiEvents, iconTint = Color(0xFF991B1B), bgTint = Color(0xFFFEF2F2), label = "Certificates\nEarned", value = "4")
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text(text = "Account Settings", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            ProfileRow(title = "Settings", icon = Icons.Filled.Settings, onClick = onSettings)
            Spacer(modifier = Modifier.height(12.dp))
            ProfileRow(title = "Help & Support", icon = Icons.Filled.HelpOutline, onClick = {})

            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                onClick = { 
                    auth.signOut() 
                    // ideally we'd pass an onLogout callback up, but keeping it simple
                },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDC2626)),
                colors = androidx.compose.material3.ButtonDefaults.outlinedButtonColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", tint = Color(0xFFDC2626), modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Logout", color = Color(0xFFDC2626), fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SettingsScreen(modifier: Modifier = Modifier, onBack: () -> Unit = {}, isDarkMode: Boolean = false, onThemeChange: (Boolean) -> Unit = {}) {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val displayName = user?.displayName?.takeIf { it.isNotBlank() } ?: "Alex Mercer"
    val email = user?.email ?: "alex.mercer@edubridge.edu"

    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(androidx.compose.material3.MaterialTheme.colorScheme.surface)

                .padding(horizontal = 16.dp, vertical = 20.dp)
                .clickable { onBack() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = BrandGreen, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "Settings", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = BrandGreen, modifier = Modifier.weight(1f), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            Spacer(modifier = Modifier.width(40.dp)) // balance center
        }

        Column(modifier = Modifier.weight(1f).padding(horizontal = 24.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface), elevation = CardDefaults.cardElevation(1.dp)) {
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(64.dp).clip(CircleShape).background(Color(0xFFE2E8F0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Outlined.Person, contentDescription = "Profile", modifier = Modifier.size(32.dp), tint = Color.Gray)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = displayName, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = email, color = Color(0xFF475569), fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "ACCOUNT", color = Color(0xFF475569), fontSize = 13.sp, fontWeight = FontWeight.Medium, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(12.dp))
            SettingsCard {
                SettingsRow("Edit Profile", Icons.Outlined.Person)
                SettingsDivider()
                SettingsRow("Change Password", Icons.Outlined.Lock)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "PREFERENCES", color = Color(0xFF475569), fontSize = 13.sp, fontWeight = FontWeight.Medium, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(12.dp))
            SettingsCard {
                SettingsRow("Notification Preferences", Icons.Outlined.Notifications)
                SettingsDivider()
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Outlined.DarkMode, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = "Dark Mode", modifier = Modifier.weight(1f), fontSize = 16.sp)
                    Switch(checked = isDarkMode, onCheckedChange = { onThemeChange(it) })
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "SUPPORT", color = Color(0xFF475569), fontSize = 13.sp, fontWeight = FontWeight.Medium, letterSpacing = 1.sp)
            Spacer(modifier = Modifier.height(12.dp))
            SettingsCard {
                SettingsRow("Help & Support", Icons.Outlined.HelpOutline)
            }

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { auth.signOut() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout", tint = androidx.compose.material3.MaterialTheme.colorScheme.surface, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Logout", color = androidx.compose.material3.MaterialTheme.colorScheme.surface, fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CoursesEmptyScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background)) {
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
private fun StatCard(modifier: Modifier = Modifier, icon: ImageVector, iconTint: Color, bgTint: Color, label: String, value: String) {
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface), elevation = CardDefaults.cardElevation(1.dp)) {
        Column(modifier = Modifier.padding(20.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Box(modifier = Modifier.size(56.dp).clip(CircleShape).background(bgTint), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = value, fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = label, color = Color(0xFF475569), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontSize = 14.sp)
        }
    }
}

@Composable
private fun ProfileRow(title: String, icon: ImageVector, onClick: () -> Unit = {}) {
    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface), modifier = Modifier.clickable { onClick() }, elevation = CardDefaults.cardElevation(1.dp)) {
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(androidx.compose.material3.MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
                Icon(imageVector = icon, contentDescription = null, tint = Color(0xFF475569), modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, modifier = Modifier.weight(1f), fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.LightGray)
        }
    }
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface), elevation = CardDefaults.cardElevation(1.dp)) {
        Column { content() }
    }
}

@Composable
private fun SettingsRow(text: String, icon: ImageVector) {
    Row(modifier = Modifier.fillMaxWidth().clickable {}.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = BrandGreen, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, modifier = Modifier.weight(1f), fontSize = 16.sp)
        Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
    }
}

@Composable
private fun SettingsDivider() {
    Box(modifier = Modifier.fillMaxWidth().padding(start = 56.dp).height(1.dp).background(Color(0xFFE2E8F0)))
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
