package com.example.workoutcompanion.entry_navigation.on_board.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.composables.AnimatedButton

@Composable
fun TermsOfUseDialogue(onDismiss:()->Unit , onAgreed:()->Unit , onRejected:()->Unit ) {
    Dialog(
        onDismissRequest = { onDismiss() } ,
        properties = DialogProperties(
            usePlatformDefaultWidth = false ,
            dismissOnBackPress = false ,
            dismissOnClickOutside = false
        )
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .background(
                    MaterialTheme.colorScheme.surface ,
                    shape = MaterialTheme.shapes.extraLarge
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize() ,
                horizontalAlignment = Alignment.CenterHorizontally ,
                verticalArrangement = Arrangement.spacedBy(8.dp , Alignment.Bottom)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 8.dp , vertical = 4.dp) ,
                    verticalAlignment = Alignment.CenterVertically ,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Terms of Use\nAgreement" , style = Typography.headlineMedium)
                    IconButton(onClick = { onDismiss() }) {
                        Icon(imageVector = Icons.Filled.ExitToApp , contentDescription = null)
                    }
                }
                Spacer(modifier = Modifier.size(8.dp))
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 12.dp , vertical = 8.dp) ,
                    horizontalAlignment = Alignment.Start ,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = stringResource(id = R.string.privacy_policy_title) ,
                                style = Typography.headlineSmall
                            )
                            Text(
                                text = stringResource(id = R.string.privacy_policy_description) ,
                                color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                            )
                        }
                    }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = stringResource(id = R.string.info_collect_title) ,
                                style = Typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Item(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(start = 16.dp) ,
                                contentStringRes = R.string.workout_info_description ,
                                titleStringRes = R.string.workout_info ,
                                index = 0
                            )
                            Item(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(start = 16.dp) ,
                                contentStringRes = R.string.user_preferences_description ,
                                titleStringRes = R.string.user_preferences ,
                                index = 1
                            )
                            Item(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(start = 16.dp) ,
                                contentStringRes = R.string.basic_user_info_description ,
                                titleStringRes = R.string.basic_user_info ,
                                index = 2
                            )
                        }
                    }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = stringResource(id = R.string.use_info_title) ,
                                style = Typography.headlineSmall
                            )
                            Item(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(start = 16.dp) ,
                                contentStringRes = R.string.app_functionality_description ,
                                titleStringRes = R.string.app_functionality ,
                                index = 0
                            )
                            Item(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(start = 16.dp) ,
                                contentStringRes = R.string.communication_description ,
                                titleStringRes = R.string.communication ,
                                index = 1
                            )
                        }
                    }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text(
                                text = stringResource(id = R.string.data_storage_title) ,
                                style = Typography.headlineSmall
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Item(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(start = 16.dp) ,
                                contentStringRes = R.string.firestore_db_description ,
                                titleStringRes = R.string.firestore_db ,
                                index = 0
                            )
                            Item(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(start = 16.dp) ,
                                contentStringRes = R.string.third_party_description ,
                                titleStringRes = R.string.third_party ,
                                index = 1
                            )
                        }
                    }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = stringResource(id = R.string.data_retention_title) ,
                                style = Typography.headlineSmall
                            )
                            Text(
                                text = stringResource(id = R.string.data_retention_description) ,
                                style = Typography.bodySmall ,
                                color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                            )
                        }
                    }
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = stringResource(id = R.string.updates_title) ,
                                style = Typography.headlineSmall
                            )
                            Text(
                                text = stringResource(id = R.string.updates_description) ,
                                style = Typography.bodySmall ,
                                color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                            )
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(8.dp)
                        ) {
                            AnimatedButton(modifier = Modifier, action = onAgreed
                            ) { _ , contentColor ->
                                Box(modifier = Modifier.fillMaxWidth().height(50.dp) , contentAlignment = Alignment.Center){
                                    Text(
                                        text = "I agree with the terms" ,
                                        style = Typography.labelMedium ,
                                        color = contentColor ,
                                        modifier = Modifier.padding(vertical = 10.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Item(modifier: Modifier, contentStringRes:Int, titleStringRes:Int, index:Int){
    Column(modifier = modifier , horizontalAlignment = Alignment.Start , verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text ="${index+1}. "+stringResource(id = titleStringRes) , style = Typography.bodyLarge)
        Text(text = stringResource(id = contentStringRes) , style = Typography.bodySmall , color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
    }
}
