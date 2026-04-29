package com.example.edubridge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
            EduBridgeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AuthHost(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AuthHost(modifier: Modifier = Modifier) {
    val isLoginScreen = remember { mutableStateOf(true) }
    val loggedIn = remember { mutableStateOf(false) }
    val selectedCourse = remember { mutableStateOf<Course?>(null) }
    val quizActive = remember { mutableStateOf(false) }
    val quizResultPercent = remember { mutableStateOf<Int?>(null) }
    val quizResultCorrect = remember { mutableStateOf(0) }
    val quizResultTotal = remember { mutableStateOf(0) }
    val quizResultTime = remember { mutableStateOf("") }
    val currentScreen = remember { mutableStateOf("Home") }
    val selectedCertificate = remember { mutableStateOf<Certificate?>(null) }
    val showSettings = remember { mutableStateOf(false) }

    val sampleQuestions = listOf(
        com.example.edubridge.Question(
            "When identifying potential hazards in a high-voltage environment, which of the following is the FIRST step according to occupational safety guidelines?",
            listOf(
                "Isolate the power source completely.",
                "Conduct a thorough site and equipment assessment.",
                "Equip appropriate personal protective equipment (PPE).",
                "Notify emergency services of intent to work."
            ),
            1
        ),
        com.example.edubridge.Question(
            "Which of the following PPE is essential when working with energized equipment?",
            listOf("Insulated gloves", "Cloth gloves", "Disposable gloves", "No gloves"),
            0
        )
    )

    when {
        !loggedIn.value -> {
            if (isLoginScreen.value) {
                LoginScreen(modifier = modifier, onToggleToSignUp = { isLoginScreen.value = false }) {
                    loggedIn.value = true
                }
            } else {
                SignUpScreen(modifier = modifier, onToggleToLogin = { isLoginScreen.value = true }) {
                    loggedIn.value = true
                }
            }
        }

        showSettings.value -> {
            SettingsScreen(modifier = modifier, onBack = { showSettings.value = false })
        }

        selectedCertificate.value != null -> {
            CertificateDetailScreen(modifier = modifier, onBack = { selectedCertificate.value = null })
        }

        quizActive.value -> {
            QuizScreen(modifier = modifier, questions = sampleQuestions) { correct, total, timeTaken ->
                quizActive.value = false
                quizResultPercent.value = (correct * 100) / total
                quizResultCorrect.value = correct
                quizResultTotal.value = total
                quizResultTime.value = timeTaken
            }
        }

        quizResultPercent.value != null -> {
            QuizResultScreen(modifier = modifier, percent = quizResultPercent.value!!, correct = quizResultCorrect.value, total = quizResultTotal.value, timeTaken = quizResultTime.value, onContinue = {
                quizResultPercent.value = null
                selectedCourse.value = null
            }, onRetake = {
                quizResultPercent.value = null
                quizActive.value = true
            })
        }

        selectedCourse.value != null -> {
            CourseDetailScreen(course = selectedCourse.value!!, modifier = modifier, onStartQuiz = { quizActive.value = true })
        }

        currentScreen.value == "Home" -> {
            HomeScreen(modifier = modifier, onOpenCourse = { course -> selectedCourse.value = course }, onSelectTab = { tab -> currentScreen.value = tab })
        }

        currentScreen.value == "Courses" -> {
            CoursesEmptyScreen(modifier = modifier)
        }

        currentScreen.value == "Progress" -> {
            NotificationsScreen(modifier = modifier)
        }

        currentScreen.value == "Certificates" -> {
            CertificatesScreen(modifier = modifier, onSelect = { cert -> selectedCertificate.value = cert }, onSelectTab = { tab -> currentScreen.value = tab })
        }

        currentScreen.value == "Profile" -> {
            ProfileScreen(modifier = modifier, onSettings = { showSettings.value = true })
        }

        else -> {
            HomeScreen(modifier = modifier, onOpenCourse = { course -> selectedCourse.value = course }, onSelectTab = { tab -> currentScreen.value = tab })
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