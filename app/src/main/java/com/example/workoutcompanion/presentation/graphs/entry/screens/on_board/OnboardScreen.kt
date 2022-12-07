package com.example.workoutcompanion.presentation.graphs.entry.screens.on_board

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.workoutcompanion.R
import com.example.workoutcompanion.ui.theme.ColorPalette
import com.example.workoutcompanion.ui.theme.Shapes
import com.example.workoutcompanion.ui.theme.Typography
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

object OnboardScreen {
    @Composable
    fun Main(onLogin:()->Unit, onCreateAccount:()->Unit){
         Box(modifier = Modifier.fillMaxSize()){
             Background()
             Foreground(onLogin, onCreateAccount)
         }
    }

    private @Composable
    fun Foreground(onLogin: () -> Unit, onCreateAccount: () -> Unit) {
        val viewModel = OnBoardViewModel()
        val playEntryAnimation = remember {
            MutableTransitionState(false).apply {

                targetState = true
            }
        }
        var angleAnim = remember { Animatable(0f) }
        var isClicked by remember { mutableStateOf(false) }
        var color1 by remember { mutableStateOf(ColorPalette.primaryEnabled)}
        var color2 by remember { mutableStateOf(ColorPalette.primaryEnabled)}
        val color1Anim = animateColorAsState(targetValue = color1)
        val color2Anim = animateColorAsState(targetValue = color2)

        AnimatedVisibility(
            visibleState = playEntryAnimation,
            enter = fadeIn(tween(750)) + slideInVertically(tween(750)),
            exit = fadeOut()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                ColorPalette.primaryBackground.copy(
                                    alpha = 0.50f
                                ), Color.Black
                            )
                        )
                    ),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(1f, true))
                Headline(
                    modifier = Modifier
                        .weight(1f, true)
                        .padding(horizontal = 8.dp)
                )
                Spacer(modifier = Modifier.weight(1f, true))
                FeatureList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(6f, true),
                    viewModel.animationFlow
                )
                Button(
                    onClick = { isClicked = !isClicked
                               color1 = ColorPalette.primaryBlue
                               color2 = ColorPalette.primaryGreen
                              },
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    elevation = null,
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .wrapContentSize()
                        .rotate(angleAnim.value)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp)
                            .padding(horizontal = 24.dp)
                            .background(Brush.verticalGradient(colors = listOf(color1Anim.value,color2Anim.value)), Shapes.large),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Typography.button.apply {
                            Text(
                                text = "Sign me Up",
                                fontSize = fontSize,
                                fontWeight = fontWeight,
                                color = color
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Icon(
                                imageVector = Icons.Filled.ArrowRight,
                                contentDescription = null,
                                tint = ColorPalette.onPrimaryBlue,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }

            }
        }
        LaunchedEffect(key1 = isClicked) {
            launch {
                angleAnim.animateTo(2f, tween(100))
                angleAnim.animateTo(-2f, tween(100))
                angleAnim.animateTo(0f, tween(100))
            }.invokeOnCompletion {
                onCreateAccount()
            }
        }
    }


    private @Composable
    fun FeatureList(modifier :Modifier,animationFlow: Flow<AppFeature>) {
        val list = remember { mutableStateListOf<AppFeature>()}
        LazyColumn(
            modifier = modifier,
            userScrollEnabled = false,
            verticalArrangement = Arrangement.spacedBy(50.dp) ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(list.size){index->
                FeatureItem(appFeature = list[index])

            }
        }
        LaunchedEffect(key1 = true) {
            animationFlow.collectLatest {
                list.add(it)
                Log.d("Test",it.headLineId.toString())
            }
        }
    }

    private @Composable
    fun Headline(modifier: Modifier) {
        Column(
            modifier =modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Typography.h1.apply {
                Text(
                    text = "Why Workout Companion?",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = color,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    private  @Composable
    fun FeatureItem(appFeature: AppFeature) {
        val playEntryAnimation = remember {
            MutableTransitionState(false).apply {

                targetState = true
            }
        }
        AnimatedVisibility(
            visibleState = playEntryAnimation,
            enter = fadeIn(tween(150)) + slideInVertically(tween(150)),
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Image(
                    painter = painterResource(id = appFeature.imageId),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentWidth()
                ) {
                    Typography.h3.apply {
                        Text(
                            text = stringResource(id = appFeature.headLineId),
                            fontSize = fontSize,
                            fontWeight = fontWeight,
                            color = color
                        )
                    }
                    Spacer(modifier = Modifier.size(4.dp))
                    Typography.h4.apply {
                        Text(
                            text = stringResource(id = appFeature.body),
                            fontSize = fontSize,
                            fontWeight = fontWeight,
                            color = color.copy(alpha = 0.50f)
                        )
                    }
                }
            }
        }
    }

    private @Composable
    fun Background() {
        Image(
            painter = painterResource(id = R.drawable.image_on_board),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight
        )
    }
}