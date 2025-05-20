import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.material3.DatePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class Exercise(val name: String, val description: String)

@Preview(showBackground = true)
@Composable
fun LogScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        Log_Screen(navController = navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Log_Screen(navController: NavController) {
    val datePickerState = rememberDatePickerState()
    val selectedDateMillis = datePickerState.selectedDateMillis
    val selectedDate = selectedDateMillis?.let { convertMillisToDate(it) } ?: ""

    val chestExercises = listOf(
        Exercise("Bench Press", "3 sets of 10 reps"),
        Exercise("Incline Dumbbell Press", "3 sets of 12 reps"),
        Exercise("Chest Fly", "4 sets of 8 reps")
    )

    val backExercises = listOf(
        Exercise("Deadlifts", "3 sets of 5 reps"),
        Exercise("Pull-ups", "4 sets to failure"),
        Exercise("Bent-over Rows", "3 sets of 8 reps")
    )

    val handsExercises = listOf(
        Exercise("Bicep Curls", "3 sets of 12 reps"),
        Exercise("Tricep Dips", "3 sets of 15 reps"),
        Exercise("Hammer Curls", "3 sets of 10 reps")
    )

    val workoutType = selectedDateMillis?.let { getWorkoutTypeForDate(it) } ?: ""
    val exercises = when (workoutType) {
        "Chest" -> chestExercises
        "Back" -> backExercises
        "Hands" -> handsExercises
        else -> emptyList()
    }
    Box( // Wrap with a Box to apply full black background
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set background to black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),

            ) {
            Text(
                text = "Pick a Date to View Log:",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 24.dp),
                color = Color.White

            )

            DatePicker(
                state = datePickerState,
                showModeToggle = false,
                modifier = Modifier.fillMaxWidth(),

            )

            if (selectedDateMillis != null) {
                if (workoutType == "Rest") {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Rest Day on $selectedDate",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Text(
                                "Take a break! No scheduled exercises today.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White
                            )
                        }
                    }
                } else if (exercises.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C))

                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Workout for $selectedDate: $workoutType Day",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            exercises.forEach { exercise ->

                                Text(
                                    text = "• ${exercise.name} - ${exercise.description}",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
            Box( // μπηκε για να κανουμε center το κουμπι back to home
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                contentAlignment = Alignment.BottomCenter
            ) {

                Button(onClick = { navController.navigate("home") }) {
                    Text("Go Back to Home")
                }
            }
        }
    }
}

fun getWorkoutTypeForDate(millis: Long): String {
    val calendar = Calendar.getInstance().apply { timeInMillis = millis }
    val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

    return if (dayOfYear % 5 == 0) {
        "Rest"
    } else {
        when (dayOfYear % 4) {
            1 -> "Chest"
            2 -> "Back"
            3 -> "Hands"
            0 -> "Chest"
            else -> ""
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(Date(millis))
}
