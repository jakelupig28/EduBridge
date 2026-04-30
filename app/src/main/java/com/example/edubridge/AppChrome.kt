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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.WorkspacePremium
import androidx.compose.material3.Icon
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
        Icon(imageVector = Icons.Outlined.Menu, contentDescription = "Menu", tint = BrandGreen, modifier = Modifier.size(24.dp))
        Text(text = "EduBridge", color = BrandGreen, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search", tint = BrandGreen, modifier = Modifier.size(24.dp))
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
        BottomNavItem("Home", Icons.Outlined.Home, selected == "Home", onSelect)
        BottomNavItem("Courses", Icons.Outlined.School, selected == "Courses", onSelect)
        BottomNavItem("Progress", Icons.Outlined.BarChart, selected == "Progress", onSelect)
        BottomNavItem("Certificates", Icons.Outlined.WorkspacePremium, selected == "Certificates", onSelect)
        BottomNavItem("Profile", Icons.Outlined.Person, selected == "Profile", onSelect)
    }
}

@Composable
private fun BottomNavItem(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector, selected: Boolean, onSelect: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onSelect(label) }
    ) {
        Icon(imageVector = icon, contentDescription = label, tint = if (selected) BrandGreen else Color.Gray, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = label, fontSize = 11.sp, color = if (selected) BrandGreen else Color.Gray)
    }
}
