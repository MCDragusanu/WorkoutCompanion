package com.example.workoutcompanion.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.theme.*

object DropDownMenu {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    operator fun invoke(
        modifier: Modifier = Modifier,
        parent: @Composable() () -> Unit,
        getItemSelected: (index: Int) -> Unit,
        choices: List<String>,
        expanded: Boolean = true,
    ) {
        var _expanded by remember { mutableStateOf(expanded) }
        ExposedDropdownMenuBox(
            expanded = _expanded, onExpandedChange = { _expanded = !_expanded }, modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
                .then(modifier)
        ) {
            parent()
            ExposedDropdownMenu(expanded = _expanded, onDismissRequest = { _expanded = false }) {
                choices.onEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        getItemSelected(index)
                        _expanded = false
                    } , modifier = Modifier.background(Color.Transparent, shape = Shapes.medium)) {
                        Text(text = s, fontSize = 12.sp, color = textColor)
                    }
                }
            }
        }
    }
}