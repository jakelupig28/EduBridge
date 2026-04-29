package com.example.edubridge

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.edubridge.ui.theme.BrandGreen
import com.example.edubridge.ui.theme.BrandGreenLight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edubridge.ui.theme.EduBridgeTheme

@Composable
private fun BrandHeader(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(color = BrandGreen)
        )
        Spacer(modifier = Modifier.padding(6.dp))
        Text(text = "EduBridge", color = BrandGreen, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onToggleToLogin: () -> Unit = {},
    onAuthSuccess: () -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    BrandHeader()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Create an Account", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Join us to start your professional journey.", color = Color.Gray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    val fullName = remember { mutableStateOf("") }
                    val email = remember { mutableStateOf("") }
                    val password = remember { mutableStateOf("") }
                    val confirm = remember { mutableStateOf("") }
                    val errorMsg = remember { mutableStateOf("") }
                    val isLoading = remember { mutableStateOf(false) }
                    val auth = FirebaseAuth.getInstance()

                    OutlinedTextField(
                        value = fullName.value,
                        onValueChange = { fullName.value = it },
                        placeholder = { Text("Full Name") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        singleLine = true,
                        enabled = !isLoading.value
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        placeholder = { Text("Email Address") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        singleLine = true,
                        enabled = !isLoading.value
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        placeholder = { Text("Password") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        singleLine = true,
                        enabled = !isLoading.value
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = confirm.value,
                        onValueChange = { confirm.value = it },
                        placeholder = { Text("Confirm Password") },
                        modifier = Modifier
                            .fillMaxWidth(),
                        singleLine = true,
                        enabled = !isLoading.value
                    )

                    if (errorMsg.value.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = errorMsg.value, color = Color.Red, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            errorMsg.value = ""
                            when {
                                fullName.value.isEmpty() -> errorMsg.value = "Full name required"
                                email.value.isEmpty() -> errorMsg.value = "Email required"
                                password.value.isEmpty() -> errorMsg.value = "Password required"
                                password.value != confirm.value -> errorMsg.value = "Passwords don't match"
                                password.value.length < 6 -> errorMsg.value = "Password must be at least 6 characters"
                                else -> {
                                    isLoading.value = true
                                    auth.createUserWithEmailAndPassword(email.value, password.value)
                                        .addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                val user = auth.currentUser
                                                if (user != null) {
                                                    val database = FirebaseDatabase.getInstance().reference
                                                    val userMap = mapOf(
                                                        "fullName" to fullName.value,
                                                        "email" to email.value,
                                                        "uid" to user.uid
                                                    )
                                                    database.child("users").child(user.uid).setValue(userMap)
                                                        .addOnCompleteListener {
                                                            isLoading.value = false
                                                            onAuthSuccess()
                                                        }
                                                        .addOnFailureListener { e ->
                                                            isLoading.value = false
                                                            errorMsg.value = "Failed to save profile: ${e.message}"
                                                            Log.e("SignUp", "Database error", e)
                                                        }
                                                }
                                            } else {
                                                isLoading.value = false
                                                errorMsg.value = task.exception?.message ?: "Sign up failed"
                                                Log.e("SignUp", "Auth failed", task.exception)
                                            }
                                        }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        enabled = !isLoading.value
                    ) {
                        Text(text = if (isLoading.value) "Creating..." else "Create Account")
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = "Already have an account? ", color = Color.Gray)
                        TextButton(onClick = onToggleToLogin) {
                            Text(text = "Log in", color = BrandGreen)
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onToggleToSignUp: () -> Unit = {},
    onAuthSuccess: () -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    BrandHeader()
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "Welcome back", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Please enter your credentials to access your courses.", color = Color.Gray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(16.dp))

                    val email = remember { mutableStateOf("") }
                    val password = remember { mutableStateOf("") }
                    val errorMsg = remember { mutableStateOf("") }
                    val isLoading = remember { mutableStateOf(false) }
                    val auth = FirebaseAuth.getInstance()

                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        placeholder = { Text("Email Address") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        enabled = !isLoading.value
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        placeholder = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        enabled = !isLoading.value
                    )

                    if (errorMsg.value.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = errorMsg.value, color = Color.Red, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { /* forgot */ }) {
                            Text(text = "Forgot Password?", color = BrandGreen)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = {
                            errorMsg.value = ""
                            when {
                                email.value.isEmpty() -> errorMsg.value = "Email required"
                                password.value.isEmpty() -> errorMsg.value = "Password required"
                                else -> {
                                    isLoading.value = true
                                    auth.signInWithEmailAndPassword(email.value, password.value)
                                        .addOnCompleteListener { task ->
                                            isLoading.value = false
                                            if (task.isSuccessful) {
                                                Log.d("Login", "User logged in: ${auth.currentUser?.uid}")
                                                onAuthSuccess()
                                            } else {
                                                errorMsg.value = task.exception?.message ?: "Login failed"
                                                Log.e("Login", "Auth failed", task.exception)
                                            }
                                        }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        enabled = !isLoading.value
                    ) {
                        Text(text = if (isLoading.value) "Logging in..." else "Login")
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = "Don't have an account? ", color = Color.Gray)
                        TextButton(onClick = onToggleToSignUp) {
                            Text(text = "Sign up", color = BrandGreen)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSignUp() {
    EduBridgeTheme {
        SignUpScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    EduBridgeTheme {
        LoginScreen()
    }
}


