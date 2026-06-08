package com.example.edubridge

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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

import com.example.edubridge.ui.theme.EduBridgeTheme

data class Question(
    val text: String,
    val options: List<String>,
    val correctIndex: Int
)

object QuizRepository {
    fun getQuestionsForCategory(category: String): List<Question> {
        return when (category) {
            "Electrical" -> (1..30).map { i ->
                Question(
                    "Electrical Question $i: What is the standard unit of electrical resistance?",
                    listOf("Volt", "Ampere", "Ohm", "Watt"),
                    2
                )
            }
            "Automotive" -> (1..30).map { i ->
                Question(
                    "Automotive Question $i: Which component is responsible for spark ignition?",
                    listOf("Alternator", "Spark Plug", "Fuel Pump", "Radiator"),
                    1
                )
            }
            "Welding" -> (1..30).map { i ->
                Question(
                    "Welding Question $i: What gas is commonly used in TIG welding?",
                    listOf("Oxygen", "Argon", "Acetylene", "Nitrogen"),
                    1
                )
            }
            "ICT" -> (1..30).map { i ->
                Question(
                    "ICT Question $i: What does CPU stand for?",
                    listOf("Central Processing Unit", "Computer Personal Unit", "Central Power Utility", "Control Processing Unit"),
                    0
                )
            }
            "Plumbing" -> (1..30).map { i ->
                Question(
                    "Plumbing Question $i: Which tool is used to clear a clogged drain?",
                    listOf("Wrench", "Plunger", "Hacksaw", "Screwdriver"),
                    1
                )
            }
            "Carpentry" -> (1..30).map { i ->
                Question(
                    "Carpentry Question $i: Which type of wood is generally considered a hardwood?",
                    listOf("Pine", "Cedar", "Oak", "Spruce"),
                    2
                )
            }
            else -> (1..30).map { i ->
                Question(
                    "General Question $i: Safety first means?",
                    listOf("Work fast", "Wear PPE", "Ignore rules", "Sleep"),
                    1
                )
            }
        }
    }
}

@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    questions: List<Question>,
    onComplete: (correct: Int, total: Int, timeTaken: String) -> Unit
) {
    val indexState = remember { mutableStateOf(0) }
    val selected = remember { mutableStateOf<Int?>(null) }
    val correctCount = remember { mutableStateOf(0) }

    val currentIndex = indexState.value
    val current = questions[currentIndex]

    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background).padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Column {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Question ${currentIndex + 1} of ${questions.size}", fontWeight = FontWeight.SemiBold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(androidx.compose.material3.MaterialTheme.colorScheme.surface)) {
                        Text(text = "12:45", modifier = Modifier.align(Alignment.Center), color = Color.Black)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Category tag
                    Text(text = "SAFETY PROTOCOLS", color = BrandGreen, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = current.text, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
                    Spacer(modifier = Modifier.height(16.dp))

                    current.options.forEachIndexed { idx, option ->
                        val isSelected = selected.value == idx
                        val isCorrect = isSelected && idx == current.correctIndex
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    selected.value = idx
                                },
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = if (isSelected) androidx.compose.material3.MaterialTheme.colorScheme.background else androidx.compose.material3.MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) BrandGreen else Color.Transparent)
                                ) {
                                    Text(text = ('A' + idx).toString(), modifier = Modifier.align(Alignment.Center), color = if (isSelected) androidx.compose.material3.MaterialTheme.colorScheme.surface else BrandGreen)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(text = option)
                                Spacer(modifier = Modifier.weight(1f))
                                if (isCorrect) {
                                    Box(modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .background(BrandGreen)) {
                                        Text(text = "✓", modifier = Modifier.align(Alignment.Center), color = androidx.compose.material3.MaterialTheme.colorScheme.surface)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Next button
        Button(onClick = {
            // record
            selected.value?.let { sel ->
                if (sel == current.correctIndex) correctCount.value += 1
            }
            selected.value = null
            if (currentIndex < questions.size - 1) {
                indexState.value = currentIndex + 1
            } else {
                // complete
                onComplete(correctCount.value, questions.size, "4m 30s")
            }
        }, modifier = Modifier.fillMaxWidth().height(56.dp), shape = RoundedCornerShape(12.dp)) {
            Text(text = if (currentIndex < questions.size - 1) "Next Question" else "Finish Quiz")
        }
    }
}

@Composable
fun QuizResultScreen(modifier: Modifier = Modifier, percent: Int, correct: Int, total: Int, timeTaken: String, onContinue: () -> Unit = {}, onRetake: () -> Unit = {}) {
    Column(modifier = modifier.fillMaxSize().background(androidx.compose.material3.MaterialTheme.colorScheme.background).padding(16.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Card(shape = RoundedCornerShape(12.dp), colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surface), elevation = CardDefaults.cardElevation(defaultElevation = 6.dp), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.size(72.dp).clip(CircleShape).background(Color(0xFFEFF8F4)), contentAlignment = Alignment.Center) {
                    Text(text = "🏆", fontSize = 28.sp)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = if (percent >= 50) "Pass" else "Fail", color = BrandGreen, modifier = Modifier.background(Color.Transparent).padding(6.dp))
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$percent%", style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Outstanding work! You've successfully mastered the core concepts of this technical module.", color = Color.Gray, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MetricCard("$correct/$total", "Correct")
                    MetricCard(timeTaken, "Time Taken")
                }
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onContinue, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(12.dp)) {
                    Text(text = "Continue")
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(onClick = onRetake, modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(12.dp)) {
                    Text(text = "Retake Quiz")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuiz() {
    EduBridgeTheme {
        val sample = listOf(
            Question("When identifying potential hazards in a high-voltage environment, which of the following is the FIRST step according to occupational safety guidelines?", listOf("Isolate the power source completely.", "Conduct a thorough site and equipment assessment.", "Equip appropriate personal protective equipment (PPE).", "Notify emergency services of intent to work."), 1)
        )
        QuizScreen(questions = sample, onComplete = { _, _, _ -> })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuizResult() {
    EduBridgeTheme {
        QuizResultScreen(percent = 85, correct = 17, total = 20, timeTaken = "4m 30s")
    }
}

