package com.example.edubridge

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.School
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.edubridge.ui.theme.BrandGreen

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.edubridge.ui.theme.EduBridgeTheme

@Composable
private fun BrandHeader(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Outlined.School,
            contentDescription = "Logo",
            tint = BrandGreen,
            modifier = Modifier.size(32.dp)
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "EduBridge", color = BrandGreen, fontWeight = FontWeight.Bold, fontSize = 24.sp)
    }
}

@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    enabled: Boolean,
    isPassword: Boolean = false,
    rightLabel: @Composable (() -> Unit)? = null
) {
    val passwordVisible = remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = label, fontWeight = FontWeight.Medium, fontSize = 14.sp)
            rightLabel?.invoke()
        }
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = enabled,
            visualTransformation = if (isPassword && !passwordVisible.value) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = if (isPassword) {
                {
                    val image = if (passwordVisible.value)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                        Icon(imageVector = image, contentDescription = description)
                    }
                }
            } else null,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFE2E8F0),
                focusedBorderColor = BrandGreen
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    onToggleToLogin: () -> Unit = {},
    onAuthSuccess: () -> Unit = {}
) {
    Surface(modifier = modifier.fillMaxSize(), color = androidx.compose.material3.MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    BrandHeader()
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Create an Account", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Join us to start your professional\njourney.", color = Color(0xFF475569), fontSize = 15.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))

                    val fullName = remember { mutableStateOf("") }
                    val email = remember { mutableStateOf("") }
                    val password = remember { mutableStateOf("") }
                    val confirm = remember { mutableStateOf("") }
                    val errorMsg = remember { mutableStateOf("") }
                    val isLoading = remember { mutableStateOf(false) }
                    val auth = FirebaseAuth.getInstance()

                    LabeledTextField(
                        label = "Full Name",
                        value = fullName.value,
                        onValueChange = { fullName.value = it },
                        placeholder = "Jane Doe",
                        enabled = !isLoading.value
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LabeledTextField(
                        label = "Email Address",
                        value = email.value,
                        onValueChange = { email.value = it },
                        placeholder = "jane.doe@example.com",
                        enabled = !isLoading.value
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LabeledTextField(
                        label = "Password",
                        value = password.value,
                        onValueChange = { password.value = it },
                        placeholder = "••••••••",
                        enabled = !isLoading.value,
                        isPassword = true
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LabeledTextField(
                        label = "Confirm Password",
                        value = confirm.value,
                        onValueChange = { confirm.value = it },
                        placeholder = "••••••••",
                        enabled = !isLoading.value,
                        isPassword = true
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
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = BrandGreen),
                        enabled = !isLoading.value
                    ) {
                        Text(text = if (isLoading.value) "Creating..." else "Create Account", fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "Already have an account? ", color = Color(0xFF475569), fontSize = 15.sp)
                        Text(text = "Log in", color = BrandGreen, fontWeight = FontWeight.Medium, fontSize = 15.sp, modifier = Modifier.clickable { onToggleToLogin() })
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
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    Surface(modifier = modifier.fillMaxSize(), color = androidx.compose.material3.MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    BrandHeader()
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Welcome back", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Please enter your credentials to access\nyour courses.", color = Color(0xFF475569), fontSize = 15.sp, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))

                    val rememberMe = remember { mutableStateOf(sharedPrefs.getBoolean("remember_me", false)) }
                    val email = remember { mutableStateOf(if (rememberMe.value) sharedPrefs.getString("email", "") ?: "" else "") }
                    val password = remember { mutableStateOf(if (rememberMe.value) sharedPrefs.getString("password", "") ?: "" else "") }
                    val errorMsg = remember { mutableStateOf("") }
                    val isLoading = remember { mutableStateOf(false) }
                    val auth = FirebaseAuth.getInstance()

                    LabeledTextField(
                        label = "Email Address",
                        value = email.value,
                        onValueChange = { email.value = it },
                        placeholder = "student@example.com",
                        enabled = !isLoading.value
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LabeledTextField(
                        label = "Password",
                        value = password.value,
                        onValueChange = { password.value = it },
                        placeholder = "••••••••",
                        enabled = !isLoading.value,
                        isPassword = true,
                        rightLabel = {
                            Text(text = "Forgot Password?", color = BrandGreen, fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.clickable { /* forgot */ })
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = rememberMe.value,
                            onCheckedChange = { rememberMe.value = it }
                        )
                        Text(text = "Remember me", color = Color(0xFF475569), fontSize = 14.sp)
                    }

                    if (errorMsg.value.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = errorMsg.value, color = Color.Red, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
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
                                                if (rememberMe.value) {
                                                    sharedPrefs.edit()
                                                        .putBoolean("remember_me", true)
                                                        .putString("email", email.value)
                                                        .putString("password", password.value)
                                                        .apply()
                                                } else {
                                                    sharedPrefs.edit()
                                                        .putBoolean("remember_me", false)
                                                        .remove("email")
                                                        .remove("password")
                                                        .apply()
                                                }
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
                        colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = BrandGreen),
                        enabled = !isLoading.value
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = if (isLoading.value) "Logging in..." else "Login", fontSize = 16.sp)
                            if (!isLoading.value) {
                                Spacer(modifier = Modifier.width(8.dp))
                                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                    Divider(color = Color(0xFFE2E8F0), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text(text = "Don't have an account? ", color = Color(0xFF475569), fontSize = 15.sp)
                        Text(text = "Sign up", color = BrandGreen, fontWeight = FontWeight.Medium, fontSize = 15.sp, modifier = Modifier.clickable { onToggleToSignUp() })
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
