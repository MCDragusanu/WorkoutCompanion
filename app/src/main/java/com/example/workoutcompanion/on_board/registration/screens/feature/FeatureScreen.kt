package com.example.workoutcompanion.on_board.registration.screens.feature

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.workoutcompanion.R
import com.example.workoutcompanion.common.composables.AnimatedPrimaryButton
import com.example.workoutcompanion.ui.Typography
import com.example.workoutcompanion.ui.cardShapes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


private class Feature(val imageUid:Int , val titleUid:Int , val descriptionUid:Int)

@Composable
fun FeatureScreen(onBackIsPressed:()->Unit,onSighUp:()->Unit) {

    val currentItem = MutableStateFlow(0)
    val scope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FeatureList(onItemChanged = {
               scope.launch {
                   currentItem.emit(it)
               }
            })

            ItemSlider(
                modifier = Modifier.wrapContentSize(),
                currentItem = currentItem,
                numberOfItems = 4,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            )
            SignUpButton(onClick = onSighUp, modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(60.dp) )
        }

    }
}

@Composable
private fun FeatureList(onItemChanged:(Int)->Unit) {
    val state = rememberLazyListState()

    val featureList = listOf(
        Feature(
            R.drawable.workout_tracking_icon,
            R.string.workout_tracking_title,
            R.string.workout_tracking_caption
        ) ,
        Feature(
            R.drawable.progressive_overload_image,
            R.string.progressive_overload_title,
            R.string.progressive_overload_caption
        ) ,
        Feature(
            R.drawable.exercise_database_icon,
            R.string.exercise_database_title,
            R.string.exercise_database_caption
        ) ,
        Feature(
            R.drawable.workout_designer_image,
            R.string.workout_designer_title,
            R.string.workout_designer_caption
        )
    )
    LazyRow(state =  state , modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight() , horizontalArrangement = Arrangement.spacedBy(4.dp) , verticalAlignment = Alignment.CenterVertically) {
        items(featureList.size) {
            FeatureCard(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .padding(4.dp)
                    .fillMaxHeight(0.70f),
                feature = featureList[it]
            )
        }
    }

    LaunchedEffect(key1 = state.firstVisibleItemIndex) {
        onItemChanged(state.firstVisibleItemIndex)
    }

}

private @Composable
fun FeatureCard(modifier: Modifier , feature: Feature) {

    Card(
        modifier = modifier,
        shape = cardShapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.0f),
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Bottom),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.secondary.copy(0.75f) ,
                                Color.Transparent
                            )
                        )
                    )
                    .wrapContentSize()
            ) {
                Image(
                    painter = painterResource(id = feature.imageUid),
                    contentDescription = null,
                    modifier = Modifier
                        .size(300.dp)
                        .padding(13.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = stringResource(id = feature.titleUid),
                    style = Typography.titleSmall
                )
                Text(
                    text = stringResource(id = feature.descriptionUid),
                    style = Typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f)
                )
            }
        }
    }
}


private @Composable
fun SignUpButton(onClick:()->Unit , modifier : Modifier) {

    AnimatedPrimaryButton(modifier = modifier , action = onClick) { _ , contentColor ->
        Box(modifier = Modifier.height(60.dp).fillMaxWidth() , contentAlignment = Alignment.Center){
            Text(text = "Let's get started" , style = Typography.bodyLarge , color = contentColor )
        }
    }

}
@Composable
fun ItemSlider(modifier:Modifier,
               currentItem: Flow<Int>,
               numberOfItems:Int,
               currentItemContent:@Composable()()->Unit = {
                 Surface(shape = RoundedCornerShape(5.dp) , modifier = Modifier
                     .width(15.dp)
                     .height(10.dp) , color = MaterialTheme.colorScheme.secondary) {}
               },
               defaultItemContent:@Composable()()->Unit = {
                   Surface(shape = RoundedCornerShape(2.dp) , modifier = Modifier
                       .width(10.dp)
                       .height(10.dp) , color = MaterialTheme.colorScheme.surface) {}
               },
               verticalAlignment: Alignment.Vertical,
               horizontalArrangement: Arrangement.Horizontal
               ) {
    val currentState by currentItem.collectAsState(initial = 0)
    Row(
        modifier = modifier,
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement
    ) {

            repeat(numberOfItems) {
                if (it == currentState)
                    currentItemContent()
                else defaultItemContent()
            }
        }

}

