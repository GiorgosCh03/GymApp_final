package com.example.gymapp_final


import android.graphics.fonts.Font
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp_final.ui.theme.GymApp_finalTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.OutlinedTextField






@Composable
fun Add_Screen(navController : NavController, sharedViewModel: SharedViewModel) {

    var exerciseTime = remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val exercises = listOf(
        "Push-ups",
        "Squats",
        "Plank",
        "Jumping Jacks",
        "Lunges",
        "Burpees",
        "Mountain Climbers",
        "Sit-ups",
        "Crunches",
        "Leg Raises",
        "Bicep Curls",
        "Tricep Dips",
        "Pull-ups",
        "Deadlifts",
        "Bench Press",
        "Shoulder Press",
        "Russian Twists",
        "High Knees",
        "Bicycle Crunches",
        "Glute Bridges"
    )
    val selectedExercises = remember { mutableStateMapOf<String, Boolean>() }

    Box( // Wrap with a Box to apply full black background
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set background to black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            Text(
                text = "Let's add some exercises!",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(top = 24.dp),
                color = Color.White

            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(650.dp), // μεγαλωνουμε την εξωτερικη καρτα για να πιανει περισστορο χωρο στο σκριν
                elevation = CardDefaults.cardElevation(8.dp),

            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Text(
                        text = "How much time did you exercise?",
                        color = Color.Black
                    )

                    OutlinedTextField( // βαλαμε Outline διοτι δινει την γραμμη περιμετρικα και το κανει ποιο ομορφο
                        value = exerciseTime.value,
                        onValueChange = { exerciseTime.value = it },
                        label = { Text("Enter time in minutes", color = Color.Black) },
                        modifier = Modifier
                            .fillMaxWidth(),

                    )


                }
                exercises.forEach {
                    if (selectedExercises[it] == null) selectedExercises[it] = false
                }
                Text(
                    text = "Scroll and select your exercises:",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black
                )

                Card(
                    // λιστα με ασκησεις απο developers
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp) // Set a fixed height so it knows when to scroll
                        .verticalScroll(scrollState),// epitrepei to scroll

                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                    ) {


                        exercises.forEach { exercise ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Checkbox(
                                    checked = selectedExercises[exercise] ?: false,
                                    onCheckedChange = { isChecked ->
                                        selectedExercises[exercise] = isChecked
                                    }
                                )
                                Text(
                                    text = exercise,
                                    color = Color.Black
                                )

                            }

                        }
                    }

                }
                Box( // μπηκε για να κανουμε center το κουμπι
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        val minutes = exerciseTime.value.toIntOrNull() ?: 0
                        val selectedCount =
                            selectedExercises.values.count { it }//selectedExercises.values.count { it }
                        // calculates how many checkboxes are checked at the time the button is pressed,
                        // instead of relying on the potentially stale or recomposed state
                        sharedViewModel.setExerciseCalories(minutes, selectedCount)
                        navController.navigate("home")
                    }) {
                        Text("Submit")
                    }
                }
            }

            Box( // μπηκε για να κανουμε center το κουμπι back to home
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(onClick = {

                    navController.navigate("home")
                }) {
                    Text("Go Back to Home")
                }
            }
        }
    }
}

