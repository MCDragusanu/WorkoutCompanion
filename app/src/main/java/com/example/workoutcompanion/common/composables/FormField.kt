package com.example.workoutcompanion.on_board.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.workoutcompanion.common.composables.FormState
import kotlinx.coroutines.flow.Flow

@Composable
fun FormField(modifier: Modifier ,
              withErrorMessage:Boolean ,
              formStateFlow: Flow<FormState> ,
              content:@Composable()(formState:Flow<FormState> , Modifier)->Unit ,
              ) {
    val currentState by formStateFlow.collectAsState(initial = FormState())
    Column(modifier = Modifier.wrapContentSize()) {
        content(formStateFlow , modifier)
        if(withErrorMessage)
            AnimatedVisibility(visible = currentState.errorStringResource!=null) {
                 currentState.errorStringResource?.let {
                     Text(text = stringResource(id = it) , color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f) )
                 }
            }
    }
}