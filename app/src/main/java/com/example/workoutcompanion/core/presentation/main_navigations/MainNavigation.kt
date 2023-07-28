package com.example.workoutcompanion.core.presentation.main_navigations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.workoutcompanion.core.data.user_database.common.guestProfile
import com.example.workoutcompanion.core.presentation.main_navigations.database.DatabaseScreen
import com.example.workoutcompanion.core.presentation.main_navigations.home.HomeScreen
import com.example.workoutcompanion.core.presentation.main_navigations.profile.ProfileScreen
import com.example.workoutcompanion.core.presentation.main_navigations.database.DatabaseScreenViewModel
import com.example.workoutcompanion.core.presentation.main_navigations.home.HomeViewModel
import com.example.workoutcompanion.core.presentation.main_navigations.profile.ProfileViewModel
import com.example.workoutcompanion.core.presentation.main_navigations.training_program_dashboard.TrainingProgramDashboard
import com.example.workoutcompanion.core.presentation.main_navigations.training_program_dashboard.TrainingProgramViewModel
import com.example.workoutcompanion.core.presentation.main_navigations.workout_screen.WorkoutScreen
import com.example.workoutcompanion.core.presentation.main_navigations.workout_screen.WorkoutScreenViewModel
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

object MainNavigation {

    @Composable
    fun MainNavigation(homeViewModel : HomeViewModel ,
                       trainingProgramViewModel : TrainingProgramViewModel ,
                       profileViewModel: ProfileViewModel ,
                       startOnBoardFlow:()->Unit
                       ) {

        val navController = rememberNavController()
        val navBarIsVisible = MutableStateFlow(true)
        val scope = rememberCoroutineScope()
        Scaffold(bottomBar = {
            BottomNavigation(navController = navController , navBarIsVisible.asStateFlow())
        }) {
            NavGraph(
                navController = navController ,
                startOnBoardFlow = startOnBoardFlow,
                displayNavBar = {
                    scope.launch { navBarIsVisible.emit(true) }
                } ,
                hideNavBar = {
                    scope.launch { navBarIsVisible.emit(false) }
                } ,
                profileViewModel = profileViewModel,
                trainingProgramViewModel = trainingProgramViewModel ,
                homeViewModel = homeViewModel ,
                databaseScreenViewModel = hiltViewModel())
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
        databaseScreenViewModel : DatabaseScreenViewModel ,
        trainingProgramViewModel: TrainingProgramViewModel ,
        homeViewModel : HomeViewModel ,
        profileViewModel : ProfileViewModel ,
        startOnBoardFlow : () -> Unit ,
        navController : NavHostController ,
        hideNavBar : () -> Unit ,
        displayNavBar : () -> Unit
    ) {
        NavHost(navController = navController , startDestination = HomeScreen.route) {
            composable(HomeScreen.route) {
                displayNavBar()
                HomeScreen.invoke(homeViewModel , startOnBoardFlow = {
                    startOnBoardFlow()
                })
            }
            composable(ProfileScreen.route) {
                displayNavBar()
                ProfileScreen.invoke(profileViewModel)
            }
            composable(DatabaseScreen.route) {
                displayNavBar()
                DatabaseScreen.invoke(viewModel = databaseScreenViewModel , hideNavBar = {
                    hideNavBar()
                } , displayNavBar = {
                    displayNavBar()
                } ,
                    onSubmitWorkout = {
                        trainingProgramViewModel.onSubmittedWorkout(it)
                        //TODO think if you make the user navigate now to the workout screen
                        navController.navigate(TrainingProgramDashboard.route)
                    })
            }
            composable(TrainingProgramDashboard.route) {
                displayNavBar()
                TrainingProgramDashboard.invoke(
                    trainingProgramViewModel ,
                    onNavigateToExerciseDatabase = {
                        navController.navigate(DatabaseScreen.route)
                    } ,
                    onHideNavBar = hideNavBar ,
                    onShowNavBar = displayNavBar ,
                    navigateToWorkoutScreen = {workoutUid , userUid->
                        //hideNavBar()
                        navController.navigate(WorkoutScreen.route + "/${workoutUid}/${userUid}")
                    })
            }
            composable(
                WorkoutScreen.route + "/{workoutUid}/{userUid}" ,
                arguments = listOf(navArgument("workoutUid") {
                    type = NavType.LongType
                } , navArgument("userUid"){
                    type = NavType.StringType
                })
            ) {
                hideNavBar()
                val workoutUid = it.arguments?.getLong("workoutUid") ?: -1
                val userUid = it.arguments?.getString("userUid")?: guestProfile.uid
                val viewModel =
                    hiltViewModel<WorkoutScreenViewModel>().apply {
                        this.retrieveProfile(userUid)
                        this.retrieveWorkout(workoutUid)
                    }
                WorkoutScreen(viewModel = viewModel , onBackIsPressed = {
                    displayNavBar()
                    navController.popBackStack()
                })
            }
        }
    }

    enum class BottomNavItem(val title:String , val route:String , val icon: ImageVector) {

        Home("Home" , HomeScreen.route , Icons.Filled.Home) ,
        Database("Database" , DatabaseScreen.route, Icons.Filled.Archive) ,
        Profile("Profile" , ProfileScreen.route , Icons.Filled.ManageAccounts) ,
        Workouts("Workouts" , TrainingProgramDashboard.route , Icons.Filled.WorkHistory)
    }

    open class Screens(val route : String)

}

