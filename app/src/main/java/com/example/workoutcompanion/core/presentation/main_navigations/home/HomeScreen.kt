package com.example.workoutcompanion.core.presentation.main_navigations.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.workoutcompanion.core.data.user_database.common.UserProfile
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.ui.Typography

object HomeScreen :MainNavigation.Screens("Home Screen") {

    @Composable
    operator fun invoke(viewModel : HomeViewModel , startOnBoardFlow:()->Unit) {
        val appState by viewModel.appState.collectAsState(null)

        Scaffold(
            modifier = Modifier.fillMaxSize() ,
            containerColor = MaterialTheme.colorScheme.background
        ) {
            AnimatedVisibility(visible = appState==null) {
                Column(
                    modifier = Modifier.fillMaxSize() ,
                    verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.CenterVertically) ,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "There is no account found")
                    Text(
                        text = "Click here to create your account" ,
                        modifier = Modifier.clickable { startOnBoardFlow() })
                }
            }
            AnimatedVisibility(visible = appState!=null) {
                appState?.let { state->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp) ,
                        verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.Top) ,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.size(24.dp))
                        Headline(profile = state.userProfile) {

                        }
                    }
                }
            }
        }
    }

    private @Composable
    fun Headline(profile : UserProfile , onProfileClicked:()->Unit) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight() ,
            verticalAlignment = Alignment.CenterVertically ,
            horizontalArrangement = Arrangement.spacedBy(8.dp , Alignment.Start)
        ) {
            IconButton(onClick = onProfileClicked) {
                Icon(imageVector = Icons.Filled.AccountBox , contentDescription = null )
            }
            Column {
                Text(
                    text = "Welcome Back" ,
                    style = Typography.bodySmall ,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
                Text(
                    text = "${profile.firstName} ${profile.lastName}" ,
                    style = Typography.headlineSmall ,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}