package com.example.gymapp_final

import Log_Screen
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.navigation.Navigator
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GymApp_finalTheme {
                val navController = rememberNavController()
                val sharedViewModel: SharedViewModel = viewModel(this)
                NavHost(navController = navController, startDestination = "Pre_Screen") { // to startdest mas dinei to poy xekiname
                    composable("home") {
                        Greeting(name = "Android", navController = navController, sharedViewModel = sharedViewModel)
                    }
                    composable("add_screen") {
                        Add_Screen(navController = navController,sharedViewModel= sharedViewModel)
                    }
                    composable("Log_screen") {
                        Log_Screen(navController = navController)
                    }
                    composable("Extras_Screen") {
                        Extras_Screen(navController = navController,sharedViewModel = sharedViewModel)

                    }
                    composable("Pre_Screen"){
                        Pre_Screen(navController = navController, sharedViewModel= sharedViewModel)

                    }
                }
            }
        }
    }
}
@Composable
fun Greeting(name: String, navController: NavController, modifier: Modifier = Modifier, sharedViewModel : SharedViewModel) {

    val waterCups by sharedViewModel.waterCups.collectAsState()// get latest water value
    val kcalBurned: Int by sharedViewModel.kcalBurned.collectAsState()
    val vitaminD by sharedViewModel.vitaminD.collectAsState()
    val exerciseCalories by sharedViewModel.exerciseCalories.collectAsState()
    val stepCalories by sharedViewModel.stepCalories.collectAsState()
    val userName = sharedViewModel.userName.value
    val scrollState = rememberScrollState() // scrollable main screen

    Box( // Wrap with a Box to apply full black background
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set background to black
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(scrollState) // 👈 Make the main screen scrollable
                .padding(16.dp)
        ) {
            UpperPanel(
                userName = userName,
                waterCups = waterCups,
                kcalBurned = kcalBurned,
                vitaminD = vitaminD,
                stepsCalories = stepCalories,
                exerciseCalories = exerciseCalories,
                sharedViewModel = sharedViewModel
            )
            LowerPanel(navController = navController, modifier = Modifier)
        }
    }
}

@Composable
fun UpperPanel(waterCups : Int, kcalBurned: Int , vitaminD: Int , exerciseCalories: Int, stepsCalories: Int , userName :String, sharedViewModel: SharedViewModel) {

    val kcalGoal by sharedViewModel.kcalGoal.collectAsState()
    val remainingKcal = kcalGoal - kcalBurned

    Column {
        Text(text = "Welcome, $userName!",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 24.dp),
            color = Color.White
        )
        Row(
        ) {
            Text(
                text = "Are you ready for today's workout?",
                color = Color.White)

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C))
        ) {
            Column (
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(modifier = Modifier // balame to box gia na kanoyme center ton stoxo
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Kcal Goal: $kcalGoal",
                        fontWeight = FontWeight.Bold,
                        color = Color.Green,
                        fontSize = 20.sp
                    )
                }
                Text(
                    text = "Burned: $stepsCalories from steps, and $exerciseCalories from workout (Total: $kcalBurned)",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )

                Text(
                    text = "Vitamin D: $vitaminD IU",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                if (vitaminD >= 400) {
                    Text(
                        text = "Great job! You've met your daily Vitamin D goal.",
                        fontWeight = FontWeight.Medium,
                        color = Color.Yellow,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                } else {
                    Text(
                        text = "You're below your Vitamin D goal. Aim for at least 2 hours of sun.",
                        fontWeight = FontWeight.Medium,
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }

                Text(
                    text = "Hydration: $waterCups cups",
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
                if (waterCups >= 8) {
                    Text(
                        text = "Well done! You achieved the daily target.",
                        fontWeight = FontWeight.Medium,
                        color = Color.Cyan,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                } else {
                    Text(
                        text = "You need to drink more water! Aim for 8 cups daily.",
                        fontWeight = FontWeight.Medium,
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier
                            .padding(top = 4.dp)
                    )
                }
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1B5E20)) // optional: green tone
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Remaining Calories",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
                Text(
                    text = if (remainingKcal > 0)
                        "$remainingKcal kcal left to burn"
                    else
                        "Goal Achieved!",
                    fontSize = 18.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun LowerPanel(navController: NavController, modifier : Modifier) {
    val scrollState = rememberScrollState()

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = { navController.navigate("Log_Screen") }) {
                Text(
                    text = "Log",
                )
            }
            Button(onClick = {
                navController.navigate("add_screen")
            }) {
                Text(
                    text = "Add",
                )
            }
            Button(onClick = { navController.navigate("Extras_Screen") }) {
                Text(
                    text = "Extras",
                )
            }
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp) // 👈 This creates a scrollable box
            .padding(24.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C2C2C))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Suggested Exercises !",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White
            )
            Text(
                text = "Having trouble finding your next exercise? Scroll and take a look at our suggested exercises.",
                color = Color.White

            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState) // 🔁 Makes it scrollable
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                //bazoumee to kathe exerxise se ena column gia kalytero spacing & look

                Column(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                )
                {
                    Text(
                        text = "1. Plank",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White

                    )
                    Image(
                        painter = painterResource(id = R.drawable.plank),
                        contentDescription = "Plank Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Hold your body in a straight line on elbows and toes, engaging your core and keeping hips level",
                        color = Color.White

                    )
                }
                Column(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                )
                {
                    Text(
                        text = "2. lat Pulldown",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Image(
                        painter = painterResource(id = R.drawable.lat_pulldown),
                        contentDescription = "Suggested Exercise Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Grip the bar wide and pull it to your upper chest while keeping your torso upright and elbows moving down and back",
                        color = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                )
                {
                    Text(
                        text = "3. Barbell Squat",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                    Image(
                        painter = painterResource(id = R.drawable.barbell_squat),
                        contentDescription = "Suggested Exercise Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Stand with the bar on your upper back, feet shoulder-width apart, and squat down keeping your chest up and back straight",
                        color = Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                )
                {
                    Text(
                        text = "4. Dumbell Bench Press",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White

                    )
                    Image(
                        painter = painterResource(id = R.drawable.dumbbell_bench_press),
                        contentDescription = "Suggested Exercise Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = "Lie on a bench, press dumbbells from chest to arms extended, keeping control and tension through the movement",
                        color = Color.White
                    )
                }
            }
        }
    }
}

