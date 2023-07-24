package com.example.workoutcompanion.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.workoutcompanion.ui.cardShapes


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T>DropDownMenu(
    modifier: Modifier = Modifier ,
    parent: @Composable() () -> Unit ,
    getItemSelected: (index: Int) -> Unit ,
    choices: List<T> ,
    expanded: Boolean = true ,
    itemContent: @Composable() (T)->Unit
    ) {
        var _expanded by remember { mutableStateOf(expanded) }
        var current by remember { mutableStateOf(0) }
        ExposedDropdownMenuBox(
            expanded = _expanded, onExpandedChange = { _expanded = !_expanded }, modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
                .then(modifier)
        ) {
            parent()
            ExposedDropdownMenu(expanded = _expanded, onDismissRequest = { _expanded = false }) {
                choices.onEachIndexed { index , s ->
                    DropdownMenuItem(
                        onClick = {
                            getItemSelected(index)
                            current = index
                            _expanded = false
                        } ,
                        modifier = Modifier.background(
                            Color.Transparent ,
                            shape = cardShapes.medium
                        ) ,
                        text = {
                            itemContent.invoke(s)
                        })
                }
            }
        }
    }
