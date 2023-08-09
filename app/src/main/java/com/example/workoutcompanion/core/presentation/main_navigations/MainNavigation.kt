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
import com.example.workoutcompanion.core.presentation.main_navigations.workout_editor_screen.WorkoutEditorScreen
import com.example.workoutcompanion.core.presentation.main_navigations.workout_editor_screen.WorkoutEditorViewModel
import com.example.workoutcompanion.core.presentation.main_navigations.workout_session.WorkoutSessionScreen
import com.example.workoutcompanion.core.presentation.main_navigations.workout_session.WorkoutSessionViewModel
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

object MainNavigation {

    @Composable
    fun MainNavigation(
        userUid : String ,
        startOnBoardFlow : () -> Unit
    ) {

        val navController = rememberNavController()
        val navBarIsVisible = MutableStateFlow(true)
        val scope = rememberCoroutineScope()


        val profileViewModel = hiltViewModel<ProfileViewModel>().apply {
            this.retrieveAppState(userUid)
        }
        val trainingProgramViewModel = hiltViewModel<TrainingProgramViewModel>().apply {
            this.retrieveAppState(userUid)
        }
        val homeViewModel = hiltViewModel<HomeViewModel>().apply {
            this.retrieveAppState(userUid)
        }

        Scaffold(bottomBar = {
            BottomNavigation(navController = navController , navBarIsVisible.asStateFlow())
        }) {
            NavGraph(
                navController = navController ,
                startOnBoardFlow = startOnBoardFlow ,
                displayNavBar = {
                    scope.launch { navBarIsVisible.emit(true) }
                } ,
                hideNavBar = {
                    scope.launch { navBarIsVisible.emit(false) }
                } ,
                profileViewModel = profileViewModel ,
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
                        selected = item.route == currentRoute ,
                        enabled = isVisible ,
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
        trainingProgramViewModel : TrainingProgramViewModel ,
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
                    navigateToWorkoutScreen = { workoutUid , userUid ->
                        //hideNavBar()
                        navController.navigate(WorkoutEditorScreen.route + "/${workoutUid}/${userUid}")
                    })
            }
            composable(
                WorkoutEditorScreen.route + "/{workoutUid}/{userUid}" ,
                arguments = listOf(navArgument("workoutUid") {
                    type = NavType.LongType
                } , navArgument("userUid") {
                    type = NavType.StringType
                })
            ) {
                hideNavBar()
                val workoutUid = it.arguments?.getLong("workoutUid") ?: -1
                val userUid = it.arguments?.getString("userUid") ?: guestProfile.uid

                val workoutEditorViewModel = hiltViewModel<WorkoutEditorViewModel>().apply {
                    this.retrieveAppState(userUid)
                    this.retrieveWorkout(workoutUid)
                }
                WorkoutEditorScreen(viewModel = workoutEditorViewModel , onBackIsPressed = {
                    displayNavBar()
                    navController.popBackStack()
                } , onMetadataChanged = {
                    trainingProgramViewModel.updateMetadata(it)
                } , onNavigateToSessionScreen = {
                    navController.navigate(WorkoutSessionScreen.route + "/${userUid}/${it}")
                })
            }
            composable(WorkoutSessionScreen.route + "/{userUid}/{sessionUid}" , arguments = listOf(
                navArgument("userUid") {
                    type = NavType.StringType
                } , navArgument("sessionUid") {
                    type = NavType.LongType
                })
            ) {
                val sessionUid = it.arguments?.getLong("sessionUid") ?: -1
                val userUid = it.arguments?.getString("userUid") ?: guestProfile.uid
                val viewModel = hiltViewModel<WorkoutSessionViewModel>().apply {
                    this.retriveProfile(userUid)
                    this.retrieveSession(sessionUid)
                }
                WorkoutSessionScreen.invoke(viewModel = viewModel)
            }
        }
    }

    enum class BottomNavItem(val title : String , val route : String , val icon : ImageVector) {

        Home("Home" , HomeScreen.route , Icons.Filled.Home) ,
        Database("Database" , DatabaseScreen.route , Icons.Filled.Archive) ,
        Profile("Profile" , ProfileScreen.route , Icons.Filled.ManageAccounts) ,
        Workouts("Workouts" , TrainingProgramDashboard.route , Icons.Filled.WorkHistory)
    }

    open class Screens(val route : String)

}


