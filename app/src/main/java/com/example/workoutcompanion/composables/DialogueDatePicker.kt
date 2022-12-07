package com.example.workoutcompanion.composables

import android.content.res.ColorStateList
import android.widget.DatePicker
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.workoutcompanion.common.Date
import com.example.workoutcompanion.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object DialogueDatePicker {

    @Composable
    operator fun invoke(
        onDismiss: () -> Unit,
        onGetDate: (Date) -> Unit,
        startDate: Date,
        title: String,
        caption: String
    ) {
        var currentDate by remember { mutableStateOf(startDate) }
        Dialog(
            onDismissRequest = { onDismiss() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(background, CardShapes.large),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Filled.ExitToApp,
                        contentDescription = null,
                        tint = secondary,
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { onDismiss() })
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
                ) {
                    if (title.isNotEmpty()) {
                        Typography.h2.apply {
                            Text(
                                text = title,
                                Modifier,
                                color = textColor,
                                fontSize,
                                fontStyle,
                                fontWeight
                            )
                        }
                    }
                    if (caption.isNotEmpty()) {
                        Typography.h3.apply {
                            Text(
                                text = caption,
                                Modifier,
                                color = textColor.copy(alpha = 0.75f),
                                fontSize,
                                fontStyle,
                                fontWeight
                            )
                        }
                    }
                    AndroidView(factory = { context ->
                        DatePicker(context).apply {
                            this.init(
                                startDate.year, startDate.monthOfYear-1, startDate.dayOfMonth
                            ) { p0, p1, p2, p3 ->
                                val date = Date( p3, p2+1, p1)
                                currentDate = date
                            }
                             this.foregroundTintList = ColorStateList.valueOf(textColor.hashCode())
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(475.dp)
                        .padding(16.dp))
                    DialogCTA(
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .height(55.dp)
                            .padding(vertical = 16.dp),
                        onClick = {
                            onGetDate(currentDate)
                            onDismiss()
                        }
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }

    @Composable
    private fun DialogCTA(modifier: Modifier, onClick: () -> Unit) {
        var scale = 0.8f
        var isClicked by remember { mutableStateOf(false) }
        val scaleAnim = remember { Animatable(0.8f) }
        val scope = rememberCoroutineScope()
        Button(
            onClick = {
                isClicked = !isClicked
                scope.launch {
                    delay(400)
                    onClick()
                }
            },
            modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            elevation = null,
            shape = Shapes.medium
        ) {
            Row(
                modifier = Modifier
                    .background(Brush.linearGradient(listOf(primary, secondary)), shape = Shapes.medium)
                    .then(modifier),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Typography.button.apply {
                    Text(
                        text = "Pick Date",
                        Modifier,
                        color = onPrimary,
                        fontSize,
                        fontStyle,
                        fontWeight
                    )
                }
                Spacer(modifier = Modifier.size(4.dp))
                AnimatedVisibility(visible = isClicked) {
                    if (isClicked) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            tint = onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
        LaunchedEffect(key1 = isClicked) {
            scaleAnim.animateTo(1f, spring(Spring.DampingRatioMediumBouncy))
        }
    }
}