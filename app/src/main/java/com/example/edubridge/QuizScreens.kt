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
            "Electrical" -> listOf(
                Question("What is the primary purpose of a circuit breaker?", listOf("Increase voltage", "Protect from overcurrent", "Measure resistance", "Store energy"), 1),
                Question("Which tool is used to measure electrical voltage?", listOf("Ohmmeter", "Ammeter", "Voltmeter", "Wattmeter"), 2),
                Question("What does AC stand for in electricity?", listOf("Always Current", "Alternative Circuit", "Alternating Current", "Automatic Connection"), 2),
                Question("What color is typically used for a ground wire in residential wiring?", listOf("Red", "Black", "Blue", "Green or Bare"), 3),
                Question("What is the unit of electrical power?", listOf("Volt", "Ampere", "Ohm", "Watt"), 3),
                Question("Which material is the best conductor of electricity?", listOf("Rubber", "Glass", "Copper", "Wood"), 2),
                Question("What is Ohm's Law formula?", listOf("V = I * R", "P = V * I", "E = m * c^2", "F = m * a"), 0),
                Question("What device converts AC to DC?", listOf("Transformer", "Rectifier", "Inverter", "Capacitor"), 1),
                Question("What is the function of a transformer?", listOf("Measure current", "Store charge", "Change voltage levels", "Switch circuits"), 2),
                Question("What does a GFCI outlet protect against?", listOf("Power outages", "Short circuits", "Ground faults", "High voltage"), 2),
                Question("What is the standard residential voltage in many countries?", listOf("110V/220V", "12V", "440V", "5000V"), 0),
                Question("Which component restricts the flow of current?", listOf("Capacitor", "Inductor", "Resistor", "Switch"), 2),
                Question("What is used to join two electrical conductors permanently?", listOf("Tape", "Solder", "Glue", "Wire nut"), 1),
                Question("What is the frequency of AC power in North America?", listOf("50 Hz", "60 Hz", "100 Hz", "120 Hz"), 1),
                Question("What is a 'short circuit'?", listOf("A path of high resistance", "A path of low resistance", "An open loop", "A balanced load"), 1),
                Question("What instrument measures current?", listOf("Voltmeter", "Ammeter", "Galvanometer", "Ohmmeter"), 1),
                Question("Which gauge wire is thicker?", listOf("10 AWG", "14 AWG", "18 AWG", "22 AWG"), 0),
                Question("What is the purpose of wire insulation?", listOf("Conduct electricity", "Prevent shocks/shorts", "Make wire colorful", "Increase weight"), 1),
                Question("What does a fuse do when it blows?", listOf("Melts to break circuit", "Explodes", "Changes color", "Increases voltage"), 0),
                Question("What is a 'hot' wire?", listOf("Ground", "Neutral", "Energized wire", "Broken wire"), 2),
                Question("What is a 'parallel' circuit?", listOf("Single path", "Multiple paths", "No path", "Circular path"), 1),
                Question("What device stores electrical energy in an electric field?", listOf("Resistor", "Inductor", "Capacitor", "Diode"), 2),
                Question("What is the 'neutral' wire's primary job?", listOf("Carry voltage", "Complete the circuit", "Ground the device", "Stop current"), 1),
                Question("Which type of conduit is flexible?", listOf("EMT", "RMC", "FMC", "IMC"), 2),
                Question("What is the unit of electrical charge?", listOf("Coulomb", "Joule", "Farad", "Henry"), 0),
                Question("What is 'inductance' measured in?", listOf("Farads", "Henrys", "Siemens", "Teslas"), 1),
                Question("What is a 'load' in a circuit?", listOf("A battery", "A switch", "A device that consumes power", "A wire"), 2),
                Question("What does 'Continuity' mean in testing?", listOf("Broken path", "Unbroken path", "High voltage", "Low current"), 1),
                Question("Which safety rule is most important?", listOf("Work alone", "Work wet", "De-energize first", "Use metal ladders"), 2),
                Question("What is the symbol for Current?", listOf("V", "R", "P", "I"), 3)
            )
            "Automotive" -> (1..30).map { i ->
                Question("Automotive Question $i: Specific unique question for $i", listOf("Option A", "Option B", "Option C", "Option D"), (i % 4))
            }
            "Welding" -> (1..30).map { i ->
                Question("Welding Question $i: Specific unique question for $i", listOf("Option A", "Option B", "Option C", "Option D"), (i % 4))
            }
            "ICT" -> (1..30).map { i ->
                Question("ICT Question $i: Specific unique question for $i", listOf("Option A", "Option B", "Option C", "Option D"), (i % 4))
            }
            "Plumbing" -> (1..30).map { i ->
                Question("Plumbing Question $i: Specific unique question for $i", listOf("Option A", "Option B", "Option C", "Option D"), (i % 4))
            }
            "Carpentry" -> (1..30).map { i ->
                Question("Carpentry Question $i: Specific unique question for $i", listOf("Option A", "Option B", "Option C", "Option D"), (i % 4))
            }
            else -> (1..30).map { i ->
                Question("General Question $i: Specific unique question for $i", listOf("Option A", "Option B", "Option C", "Option D"), (i % 4))
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

