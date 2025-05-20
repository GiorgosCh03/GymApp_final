package com.example.gymapp_final


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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gymapp_final.ui.theme.GymApp_finalTheme


@Preview(showBackground = true)
@Composable
fun ExtrasScreenPreview() {
    val navController = rememberNavController() // mock navController for preview
    val previewViewModel = object : SharedViewModel() {
        init {
            setWater(4) // Pretend user drank 4 cups
        }
    }
    GymApp_finalTheme {
        Extras_Screen(navController = navController, sharedViewModel = previewViewModel)
    }
}

@Composable
fun Extras_Screen(navController: NavController, sharedViewModel: SharedViewModel) {
    val waterCupsState by sharedViewModel.waterCups.collectAsState()
    var localWaterCups by remember { mutableStateOf(0) }

    val steps by sharedViewModel.stepsCount.collectAsState()
    var stepsInput by remember { mutableStateOf("") }

    var sunInput by remember { mutableStateOf("") }

    Box( // Wrap with a Box to apply full black background
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),// Set background to black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Daily Health Info",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Water Intake",
                        color = Color.Black

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "$localWaterCups cups",

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = {
                            if (localWaterCups > 0) localWaterCups-- }) {
                            Text("-")
                        }
                        Spacer(modifier = Modifier.width(16.dp))

                        Button(onClick = {

                            localWaterCups++ }) {
                            Text("+")
                        }
                    }
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),


            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Steps Walked",
                        style = MaterialTheme.typography.titleMedium,

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = stepsInput,
                        onValueChange = {
                            stepsInput = it // just save input locally

                        },
                        label = { Text("Enter steps") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "You've walked $steps steps today",

                        )
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(8.dp),


            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Sun Exposure (hours)",
                        color = Color.Black

                    )

                    OutlinedTextField(
                        value = sunInput,
                        onValueChange = {
                            sunInput = it // just save input locally

                        },
                        label = { Text(
                            text = "Enter hours (e.g. 1.5)",
                            )},
                        singleLine = true
                    )
                }
            }
            Box( // μπηκε για να κανουμε center το κουμπι back to home
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(onClick = {
                    stepsInput.toIntOrNull()?.let { sharedViewModel.setSteps(it) }
                    sunInput.toFloatOrNull()?.let { sharedViewModel.setSunExposure(it) }
                    sharedViewModel.setWater(localWaterCups)
                    navController.navigate("home")
                }) {
                    Text("Update Info")
                }

            }

            Box( // μπηκε για να κανουμε center το κουμπι back to home
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 70.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                Button(onClick = {
                    navController.navigate("home")
                }) {
                    Text("Back to Home")
                }
            }
        }
    }
}
