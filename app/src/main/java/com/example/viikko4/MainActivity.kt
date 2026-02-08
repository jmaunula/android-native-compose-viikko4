package com.example.viikko4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.viikko4.navigation.ROUTE_CALENDAR
import com.example.viikko4.navigation.ROUTE_HOME
import com.example.viikko4.navigation.ROUTE_SETTINGS
import com.example.viikko4.view.CalendarScreen
import com.example.viikko4.view.HomeScreen
import com.example.viikko4.view.SettingsScreen
import com.example.viikko4.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Luodaan navigointiohjain
            val navController = rememberNavController()
            // Haetaan viewModel, jota käytetään koko sovelluksessa
            val viewModel: TaskViewModel = viewModel()

            // NavHost määrittelee eri ruudut (Home, Calendar, Settings)
            NavHost(
                navController = navController,
                startDestination = ROUTE_HOME
            ) {

                // HomeScreen
                composable(ROUTE_HOME) {
                    HomeScreen(
                        viewModel = viewModel,
                        onTaskClick = { id -> viewModel.openTask(id) }, // avaa valitun tehtävän
                        onAddClick = {
                            viewModel.addTaskDialogVisible.value = true
                        }, // näytä lisäysdialogi
                        onNavigateCalendar = { navController.navigate(ROUTE_CALENDAR) },
                        onNavigateSettings = { navController.navigate(ROUTE_SETTINGS) }
                    )
                }

                // CalendarScreen
                composable(ROUTE_CALENDAR) {
                    CalendarScreen(
                        viewModel = viewModel,
                        onTaskClick = { id -> viewModel.openTask(id) },
                        onNavigateHome = { navController.navigate(ROUTE_HOME) }
                    )
                }

                // SettingsScreen
                composable(ROUTE_SETTINGS) {
                    SettingsScreen(
                        onNavigateHome = { navController.navigate(ROUTE_HOME) }
                    )
                }
            }
        }

    }
}
