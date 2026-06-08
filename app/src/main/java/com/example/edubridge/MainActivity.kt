package com.example.edubridge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
// Home and course screens are in the same package; no extra imports required
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.edubridge.ui.theme.EduBridgeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isSystemDark = isSystemInDarkTheme()
            val isDarkMode = remember { mutableStateOf(isSystemDark) }

            EduBridgeTheme(darkTheme = isDarkMode.value) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AuthHost(
                        modifier = Modifier.padding(innerPadding),
                        isDarkMode = isDarkMode.value,
                        onThemeChange = { isDarkMode.value = it }
                    )
                }
            }
        }
    }
}

@Composable
fun AuthHost(modifier: Modifier = Modifier, isDarkMode: Boolean = false, onThemeChange: (Boolean) -> Unit = {}) {
    val isLoginScreen = remember { mutableStateOf(true) }
    val loggedIn = remember { mutableStateOf(false) }
    val userFullName = remember { mutableStateOf("Student") }
    val selectedCourse = remember { mutableStateOf<Course?>(null) }
    val quizActive = remember { mutableStateOf(false) }
    val quizResultPercent = remember { mutableStateOf<Int?>(null) }
    val quizResultCorrect = remember { mutableStateOf(0) }
    val quizResultTotal = remember { mutableStateOf(0) }
    val quizResultTime = remember { mutableStateOf("") }
    val currentScreen = remember { mutableStateOf("Home") }
    val selectedCertificate = remember { mutableStateOf<Certificate?>(null) }
    val showSettings = remember { mutableStateOf(false) }
    val selectedLesson = remember { mutableStateOf<String?>(null) }
    val selectedCategory = remember { mutableStateOf<String?>(null) }
    val courseProgress = remember { mutableStateOf<Map<String, Int>>(emptyMap()) }

    val auth = com.google.firebase.auth.FirebaseAuth.getInstance()
    val database = com.google.firebase.database.FirebaseDatabase.getInstance().reference

    // Fetch user name and progress on login
    if (loggedIn.value && auth.currentUser != null) {
        val uid = auth.currentUser!!.uid
        database.child("users").child(uid).child("fullName").get().addOnSuccessListener {
            it.value?.let { name -> userFullName.value = name.toString() }
        }
        database.child("progress").child(uid).get().addOnSuccessListener { snapshot ->
            val progressMap = mutableMapOf<String, Int>()
            snapshot.children.forEach { child ->
                progressMap[child.key!!] = (child.value as Long).toInt()
            }
            courseProgress.value = progressMap
        }
    }

    val sampleQuestions = com.example.edubridge.QuizRepository.getQuestionsForCategory(selectedCourse.value?.category ?: "Electrical")

    Column(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
            when {
                !loggedIn.value -> {
                    if (isLoginScreen.value) {
                        LoginScreen(modifier = Modifier.fillMaxSize(), onToggleToSignUp = { isLoginScreen.value = false }) {
                            loggedIn.value = true
                        }
                    } else {
                        SignUpScreen(modifier = Modifier.fillMaxSize(), onToggleToLogin = { isLoginScreen.value = true }) {
                            loggedIn.value = true
                        }
                    }
                }

                showSettings.value -> {
                    SettingsScreen(
                        modifier = Modifier.fillMaxSize(),
                        onBack = { showSettings.value = false },
                        isDarkMode = isDarkMode,
                        onThemeChange = onThemeChange,
                        onLogout = {
                            loggedIn.value = false
                            showSettings.value = false
                            currentScreen.value = "Home"
                        }
                    )
                }

                selectedCertificate.value != null -> {
                    CertificateDetailScreen(modifier = Modifier.fillMaxSize(), userFullName = userFullName.value, onBack = { selectedCertificate.value = null })
                }

                quizActive.value -> {
                    QuizScreen(modifier = Modifier.fillMaxSize(), questions = sampleQuestions) { correct, total, timeTaken ->
                        quizActive.value = false
                        quizResultPercent.value = (correct * 100) / total
                        quizResultCorrect.value = correct
                        quizResultTotal.value = total
                        quizResultTime.value = timeTaken
                        
                        // Update progress in database if passed
                        if (quizResultPercent.value!! >= 75) {
                             selectedCourse.value?.let { course ->
                                 auth.currentUser?.uid?.let { uid ->
                                     database.child("progress").child(uid).child(course.title).setValue(100)
                                 }
                             }
                        }
                    }
                }

                quizResultPercent.value != null -> {
                    QuizResultScreen(modifier = Modifier.fillMaxSize(), percent = quizResultPercent.value!!, correct = quizResultCorrect.value, total = quizResultTotal.value, timeTaken = quizResultTime.value, onContinue = {
                        quizResultPercent.value = null
                        selectedCourse.value = null
                    }, onRetake = {
                        quizResultPercent.value = null
                        quizActive.value = true
                    })
                }

                selectedLesson.value != null -> {
                    LessonScreen(
                        lessonTitle = selectedLesson.value!!,
                        modifier = Modifier.fillMaxSize(),
                        onBack = { selectedLesson.value = null }
                    )
                }

                selectedCategory.value != null -> {
                    CategoryDetailScreen(
                        category = selectedCategory.value!!,
                        modifier = Modifier.fillMaxSize(),
                        onBack = { selectedCategory.value = null },
                        onOpenCourse = { course -> selectedCourse.value = course }
                    )
                }

                selectedCourse.value != null -> {
                    CourseDetailScreen(
                        course = selectedCourse.value!!,
                        modifier = Modifier.fillMaxSize(),
                        onStartQuiz = { quizActive.value = true },
                        onBack = { selectedCourse.value = null },
                        onOpenLesson = { lesson -> selectedLesson.value = lesson }
                    )
                }

                currentScreen.value == "Home" -> {
                    HomeScreen(
                        modifier = Modifier.fillMaxSize(),
                        onOpenCourse = { course -> selectedCourse.value = course },
                        onCategorySelect = { category -> selectedCategory.value = category },
                        courseProgress = courseProgress.value
                    )
                }

                currentScreen.value == "Courses" -> {
                    CourseCatalogScreen(modifier = Modifier.fillMaxSize(), onOpenCourse = { course -> selectedCourse.value = course })
                }

                currentScreen.value == "Progress" -> {
                    ProgressScreen(modifier = Modifier.fillMaxSize(), courseProgress = courseProgress.value)
                }

                currentScreen.value == "Certificates" -> {
                    CertificatesScreen(modifier = Modifier.fillMaxSize(), onSelect = { cert -> selectedCertificate.value = cert }, onSelectTab = { tab -> currentScreen.value = tab })
                }

                currentScreen.value == "Profile" -> {
                    ProfileScreen(
                        modifier = Modifier.fillMaxSize(),
                        onSettings = { showSettings.value = true },
                        onLogout = {
                            loggedIn.value = false
                            currentScreen.value = "Home"
                        }
                    )
                }

                else -> {
                    HomeScreen(modifier = Modifier.fillMaxSize(), onOpenCourse = { course -> selectedCourse.value = course })
                }
            }
        }

        if (loggedIn.value) {
            BottomNavBar(selected = currentScreen.value, onSelect = { tab ->
                currentScreen.value = tab
                selectedCourse.value = null
                selectedLesson.value = null
                selectedCategory.value = null
                selectedCertificate.value = null
                showSettings.value = false
                quizActive.value = false
                quizResultPercent.value = null
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthPreview() {
    EduBridgeTheme {
        AuthHost()
    }
}
