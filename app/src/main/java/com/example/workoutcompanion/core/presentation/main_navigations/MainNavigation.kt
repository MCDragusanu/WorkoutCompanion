package com.example.workoutcompanion.core.presentation.main_navigations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.workoutcompanion.core.presentation.main_navigations.screens.database.DatabaseScreen
import com.example.workoutcompanion.core.presentation.main_navigations.screens.home.HomeScreen
import com.example.workoutcompanion.core.presentation.main_navigations.screens.ProfileScreen
import com.example.workoutcompanion.core.presentation.main_navigations.screens.database.DatabaseScreenViewModel
import com.example.workoutcompanion.core.presentation.main_navigations.screens.home.HomeViewModel
import com.example.workoutcompanion.core.presentation.main_navigations.screens.workouts.WorkoutScreen
import com.example.workoutcompanion.core.presentation.main_navigations.screens.workouts.WorkoutViewModel
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

object MainNavigation {

    @Composable
    fun MainNavigation() {

        val navController = rememberNavController()
        val navBarIsVisible = MutableStateFlow<Boolean>(true)
        val scope = rememberCoroutineScope()
        Scaffold(bottomBar = {
            BottomNavigation(navController = navController , navBarIsVisible.asStateFlow())
        }) {
            NavGraph(navController = navController , displayNavBar = {
                scope.launch { navBarIsVisible.emit(true)  }
            } , hideNavBar = {
                scope.launch { navBarIsVisible.emit(false) }
            } , workoutViewModel = hiltViewModel(), homeViewModel = hiltViewModel() , databaseScreenViewModel = hiltViewModel())
        }

    }

    @Composable

    fun BottomNavigation(
        navController : NavController = rememberNavController() ,
        isVisibleFlow : StateFlow<Boolean>
    ) {
        val items = BottomNavItem.values()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        val isVisible by isVisibleFlow.collectAsState()
        AnimatedVisibility(visible = isVisible) {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.secondaryContainer ,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer ,
                windowInsets = WindowInsets(8.dp) ,
                modifier = Modifier
                    .padding(12.dp)
                    .clip(cardShapes.large)
                    .wrapContentHeight(Alignment.CenterVertically)
                    .height(70.dp)
            ) {
                items.forEach { item ->
                    val contentColor by
                    animateColorAsState(targetValue = if (item.route == currentRoute) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSecondaryContainer)
                    NavigationBarItem(
                        selected = item.route == currentRoute,
                        enabled = isVisible,
                        colors = NavigationBarItemColors(
                            selectedIconColor = contentColor ,
                            selectedTextColor = contentColor ,
                            selectedIndicatorColor = MaterialTheme.colorScheme.secondaryContainer ,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer ,
                            unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer ,
                            disabledIconColor = Color.Unspecified ,
                            disabledTextColor = Color.Unspecified
                        ) ,
                        onClick = { navController.navigate(item.route) } , icon = {
                            Icon(
                                imageVector = item.icon ,
                                contentDescription = null ,
                                //  tint = contentColor
                            )
                        } , label = {
                            AnimatedVisibility(visible = currentRoute == item.route) {
                                Text(text = item.title , style = Typography.labelSmall)
                            }
                        })
                }
            }
        }
    }

    @Composable
    fun NavGraph(
        databaseScreenViewModel : DatabaseScreenViewModel,
        workoutViewModel : WorkoutViewModel,
        homeViewModel : HomeViewModel,
        navController : NavHostController ,
        hideNavBar : () -> Unit ,
        displayNavBar : () -> Unit
    ) {
        NavHost(navController = navController , startDestination = HomeScreen.route) {
            composable(HomeScreen.route) {
                displayNavBar()
                HomeScreen.invoke(homeViewModel , startOnBoardFlow = {

                })
            }
            composable(ProfileScreen.route) {
                displayNavBar()
                ProfileScreen.invoke()
            }
            composable(DatabaseScreen.route) {
                displayNavBar()
                DatabaseScreen.invoke(viewModel = databaseScreenViewModel , hideNavBar = {
                    hideNavBar()
                } , displayNavBar = {
                    displayNavBar()
                } ,
                    onSubmitWorkout = {
                       workoutViewModel.onSubmittedWorkout(it)
                        //TODO think if you make the user navigate now to the workout screen
                       navController.navigate(WorkoutScreen.route)
                    })
            }
            composable(WorkoutScreen.route) {
                displayNavBar()
                WorkoutScreen.invoke(workoutViewModel , onNavigateToExerciseDatabase = {
                    navController.navigate(DatabaseScreen.route)
                } , onHideNavBar = hideNavBar , onShowNavBar = displayNavBar)
            }
        }
    }


    open class Screens(val route : String)

}


