package com.example.workoutcompanion.core.presentation.main_navigations.workout_session

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout.set_slot.SetSlot.Companion.WarmUp
import com.example.workoutcompanion.core.presentation.main_navigations.MainNavigation
import com.example.workoutcompanion.ui.Typography
import kotlinx.coroutines.flow.Flow

object WorkoutSessionScreen:MainNavigation.Screens("Workout_Session_Screen") {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    operator fun invoke(viewModel : WorkoutSessionViewModel) {
        val sets by viewModel.sets.collectAsState()
        val slots by viewModel.exerciseSlots.collectAsState()
        val currentSet by viewModel.currentSet.collectAsState()

        BottomSheetScaffold(sheetContent = {} ,
            containerColor = MaterialTheme.colorScheme.background) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize() ,
                verticalArrangement = Arrangement.spacedBy(12.dp) ,
                horizontalAlignment = Alignment.Start
            ) {
                item {
                    Text(text = "Today's Workout" , style = Typography.headlineLarge)
                }
                if (sets.isNotEmpty() && slots.isNotEmpty()) {
                    items(slots) { slot ->
                        val slotSets = sets.filter { it.exerciseSlotUid == slot.uid }
                        val warmUpSets = slotSets.filter { it.type == SetSlot.WarmUp }
                        val workingSets = slotSets.filter { it.type == SetSlot.WorkingSet }
                        Column(
                            modifier = Modifier
                                .fillParentMaxWidth()
                                .wrapContentHeight() ,
                            horizontalAlignment = Alignment.Start ,
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillParentMaxWidth()
                                    .wrapContentHeight() ,
                                horizontalArrangement = Arrangement.Start ,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = slot.exerciseName , style = Typography.labelMedium)
                            }
                            Column(
                                verticalArrangement = Arrangement.spacedBy(
                                    4.dp ,
                                    Alignment.CenterVertically
                                ) ,
                            ) {
                                (warmUpSets + workingSets).onEach {
                                    SetCard(
                                        modifier = Modifier
                                            .fillParentMaxWidth()
                                            .height(40.dp) ,
                                        setSlot = it ,
                                        currentSet = viewModel.currentSet,
                                        onNextItem = {
                                            viewModel.onNextItem()
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private @Composable
    fun SetCard(modifier : Modifier , setSlot : SetSlot , currentSet : Flow<Int> ,onNextItem:()->Unit) {
        val containerColor =
            if (setSlot.type == WarmUp) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer
        val backgroundColor by animateColorAsState(
            targetValue = if (currentSet.collectAsState(
                    initial = -1
                ).value == setSlot.uid
            ) MaterialTheme.colorScheme.primary else containerColor
        )

        Card(
            modifier = modifier ,
            colors = CardDefaults.cardColors(containerColor = backgroundColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp) ,
                verticalArrangement = Arrangement.Center ,
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxSize() ,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize() ,
                        horizontalArrangement = Arrangement.spacedBy(8.dp , Alignment.Start)
                    ) {
                        Text(text = "${setSlot.reps} reps " , color = Color.White)
                        Text(text = "X" , color = Color.White)
                        Text(
                            text = String.format("%.1f" , setSlot.weightInKgs) + " Kgs" ,
                            color = Color.White
                        )
                    }
                    Row() {
                        Icon(
                            imageVector = Icons.Filled.Check ,
                            contentDescription = null ,
                            tint = MaterialTheme.colorScheme.background,
                            modifier = Modifier.clickable { onNextItem() })
                    }
                }
            }
        }
    }
}