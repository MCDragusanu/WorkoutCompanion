package com.example.workoutcompanion.core.presentation.main_navigations.screens.workouts

import com.example.workoutcompanion.core.data.workout_tracking.set_slot.SetSlot
import com.example.workoutcompanion.core.data.workout_tracking.week.Week

data class WeeklyProgressionState(val isLoading:Boolean,
                                  val week : Week,
                                  val sets:List<SetSlot>
                                  )