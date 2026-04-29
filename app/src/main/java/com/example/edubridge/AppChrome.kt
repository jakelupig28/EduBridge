package com.example.edubridge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edubridge.ui.theme.BrandGreen
import com.example.edubridge.ui.theme.BrandGreenLight

@Composable
fun TopBrandBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFF7F9FA))
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(24.dp)) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Box(modifier = Modifier.height(2.dp).fillMaxWidth().background(Color(0xFF334155)))
                Box(modifier = Modifier.height(2.dp).fillMaxWidth(0.8f).background(Color(0xFF334155)))
                Box(modifier = Modifier.height(2.dp).fillMaxWidth(0.6f).background(Color(0xFF334155)))
            }
        }
        Text(text = "EduBridge", color = BrandGreen, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        Box(modifier = Modifier.size(24.dp).clip(CircleShape).background(Color(0xFFF0F3F5)))
    }
}

@Composable
fun BottomNavBar(selected: String, onSelect: (String) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomNavItem("Home", selected == "Home", onSelect)
        BottomNavItem("Courses", selected == "Courses", onSelect)
        BottomNavItem("Progress", selected == "Progress", onSelect)
        BottomNavItem("Certificates", selected == "Certificates", onSelect)
        BottomNavItem("Profile", selected == "Profile", onSelect)
    }
}

@Composable
private fun BottomNavItem(label: String, selected: Boolean, onSelect: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onSelect(label) }
    ) {
        Box(modifier = Modifier.size(20.dp).clip(CircleShape).background(if (selected) BrandGreenLight else Color(0xFFEAEAEA)))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 11.sp, color = if (selected) BrandGreen else Color.Gray)
    }
}

