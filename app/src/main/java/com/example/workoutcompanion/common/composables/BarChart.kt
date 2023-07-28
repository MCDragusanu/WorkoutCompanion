package com.example.workoutcompanion.common.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun BarChart(entries:List<Pair<String , Double>> = listOf(Pair("Chest" , 24.0),Pair("Chest" , 16.0),Pair("Chest" , 28.0),Pair("Chest" , 24.0)) , modifier : Modifier = Modifier
    .width(300.dp)
    .height(300.dp)) {

    val minValue = entries.minOf { it.second}
    val maxValue =entries.maxOf { it.second }
    val yScaleLabelCount = 5
    val yScaleStep =( maxValue - minValue) / yScaleLabelCount
    val minScaleValue = minValue *0.5
    val maxScaleValue = maxValue*1.5
    val labelDimensions = Size(50f , 50f)
    val f : (Double) -> Float = {

        val maxDiff = maxScaleValue - minScaleValue
        val heightPercent = ((it - minScaleValue) / maxDiff).toFloat()
        heightPercent
    }
    Row(modifier = modifier , verticalAlignment = Alignment.Bottom) {
        //Left Grid
        BoxWithConstraints(modifier = Modifier
            .width(50.dp)
            .fillMaxHeight()) {
            val width = this.maxWidth
            val height = this.maxHeight
            Column(
                verticalArrangement = Arrangement.Top ,
                horizontalAlignment = Alignment.Start ,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.width(width).height((f(minValue) * height.value).dp))
                repeat(yScaleLabelCount) {
                    Column {
                        Spacer(modifier = Modifier.height((f(yScaleStep) * height.value).dp))
                        Text(text = (maxValue - yScaleStep * it).toString())
                    }
                }

            }
        }
        BoxWithConstraints() {
            val boxWidth = this.maxWidth
            val boxHeight = this.maxHeight
            val itemSpacing = 8.dp
            val itemWidth = 50.dp
            //(boxWidth - ((entries.size - 1) * itemSpacing.value).dp) / (entries.size)

            Row(
                verticalAlignment = Alignment.Bottom ,
                horizontalArrangement = Arrangement.spacedBy(itemSpacing)
            ) {
                entries.onEach {
                    val itemHeight = ((boxHeight.value - 50) * f(it.second)).dp
                    Log.d(
                        "Test" ,
                        "Box height = ${boxHeight.value} item height = ${itemHeight.value}"
                    )
                    Column() {
                        Surface(
                            modifier = Modifier
                                .width(itemWidth)
                                .height(itemHeight) ,
                            color = MaterialTheme.colorScheme.surface
                        ) {}
                        Box(
                            modifier = Modifier
                                .height(labelDimensions.height.dp)
                                .width(labelDimensions.width.dp)
                        ) {
                            Text(text = it.first)
                        }
                    }

                }
            }
        }
        //Bar Chart
    }
}