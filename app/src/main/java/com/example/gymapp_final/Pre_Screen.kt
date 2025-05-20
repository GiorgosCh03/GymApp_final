package com.example.gymapp_final

import android.R.attr.name
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp_final.ui.theme.GymApp_finalTheme
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import androidx.navigation.Navigator
import androidx.lifecycle.viewmodel.compose.viewModel
import org.intellij.lang.annotations.JdkConstants
import java.nio.file.WatchEvent
import androidx.compose.material3.TextFieldDefaults


@Composable
fun Pre_Screen(navController: NavController, sharedViewModel: SharedViewModel) {
    val userNameInput = remember { mutableStateOf("") }
    var kcalGoal by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)

    ) {
        Image(
            painter = painterResource(id= R.drawable.gym_prescreen),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop

        )
        // Main content in the center
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp), // Leaves room for bottom text
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to Gym App!",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                .padding(bottom = 16.dp),
                color = Color.White,
            )

            Text(
                text = "Please enter your Name and Kcal goal!",
                fontSize = 20.sp,
                modifier = Modifier
                        .padding(bottom = 16.dp),
                color = Color.White,
            )

            OutlinedTextField(
                value = userNameInput.value,
                onValueChange = { userNameInput.value = it },
                label = { Text("Name") },
                modifier = Modifier
                    .padding(bottom = 24.dp),

            )

            OutlinedTextField(
                value = kcalGoal,
                onValueChange = { kcalGoal = it },
                label = { Text("Enter kcal goal") },
                singleLine = true,
                modifier = Modifier
                    .padding(bottom = 24.dp)
            )

            Button(
                onClick = {
                    val goal = kcalGoal.toIntOrNull()
                    if (userNameInput.value.isNotBlank() && goal != null) {
                        sharedViewModel.setUserName(userNameInput.value)
                        sharedViewModel.setKcalGoal(goal)
                        navController.navigate("home")
                    }
            }) {
                Text(
                    text = "Continue",
                    color = Color.White,
                )
            }
        }

        // Author credits at the bottom of the screen
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "An App Created by Chasapis Georgios, AEM: 210153",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White,
            )
            Text(
                text = "And Ioakeim Baltas, AEM: 210085",
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White,
            )

        }
    }
}

