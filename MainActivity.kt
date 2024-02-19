package com.example.assignment_1
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.AlertDialog
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.foundation.lazy.LazyColumn
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@Composable
fun MainContent() {
    // Initialize stops
    val normalStops = remember {
        mutableListOf(
            "source" to 0.0,
            "Stop 1" to 10.0,
            "Stop 2" to 20.0,
            "Stop 3" to 30.0,
            "Stop 4" to 40.0,
            "Stop 5" to 50.0,
            "Stop 6" to 10.0,
            "Stop 7" to 20.0,
//            "Stop 8" to 30.0,
//            "Stop 9" to 40.0,
//            "Stop 10" to 50.0,
//            "Stop 11" to 10.0,
//            "Stop 12" to 20.0,
//            "Stop 13" to 30.0,
//            "Stop 14" to 40.0,
//            "Stop 15" to 50.0,
//            "Stop 16" to 10.0,
//            "Stop 17" to 20.0,
//            "Stop 18" to 30.0,
//            "Stop 19" to 40.0,
//            "Stop 20" to 50.0,
//            "source" to 0.0,
//            "Stop 1" to 10.0,
//            "Stop 2" to 20.0,
//            "Stop 3" to 30.0,
//            "Stop 4" to 40.0,
//            "Stop 5" to 50.0,
//            "Stop 6" to 10.0,
//            "Stop 7" to 20.0,
//            "Stop 8" to 30.0,
//            "Stop 9" to 40.0,
//            "Stop 10" to 50.0,
//            "Stop 11" to 10.0,
//            "Stop 12" to 20.0,
//            "Stop 13" to 30.0,
//            "Stop 14" to 40.0,
//            "Stop 15" to 50.0,
//            "Stop 16" to 10.0,
//            "Stop 17" to 20.0,
//            "Stop 18" to 30.0,
//            "Stop 19" to 40.0,
//            "Stop 20" to 50.0,
            "destination" to 60.0

        )
    }

//    val normalStops = remember {
//        mutableListOf(
//            "source" to 0.0,
//            "Stop 1" to 10.0,
//            "Stop 2" to 20.0,
//            "Stop 3" to 30.0,
//            "Stop 4" to 40.0,
//            "Stop 5" to 50.0,
//            "Stop 6" to 10.0,
//            "Stop 7" to 20.0,
////            "Stop 8" to 30.0,
////            "Stop 9" to 40.0,
////            "Stop 10" to 50.0,
////            "Stop 11" to 10.0,
////            "Stop 12" to 20.0,
////            "Stop 13" to 30.0,
////            "Stop 14" to 40.0,
////            "Stop 15" to 50.0,
////            "Stop 16" to 10.0,
////            "Stop 17" to 20.0,
////            "Stop 18" to 30.0,
////            "Stop 19" to 40.0,
////            "Stop 20" to 50.0,
////            "source" to 0.0,
////            "Stop 1" to 10.0,
////            "Stop 2" to 20.0,
////            "Stop 3" to 30.0,
////            "Stop 4" to 40.0,
////            "Stop 5" to 50.0,
////            "Stop 6" to 10.0,
////            "Stop 7" to 20.0,
////            "Stop 8" to 30.0,
////            "Stop 9" to 40.0,
////            "Stop 10" to 50.0,
////            "Stop 11" to 10.0,
////            "Stop 12" to 20.0,
////            "Stop 13" to 30.0,
////            "Stop 14" to 40.0,
////            "Stop 15" to 50.0,
////            "Stop 16" to 10.0,
////            "Stop 17" to 20.0,
////            "Stop 18" to 30.0,
////            "Stop 19" to 40.0,
////            "Stop 20" to 50.0,
//            "destination" to 60.0
//
//        )
//    }


    // Track current stop index
    val currentStopIndex = remember { mutableStateOf(0) }

    // Track distance unit
    val isKilometers = remember { mutableStateOf(true) }

    // Total stops
    val totalStops = normalStops.size

    // Total distance covered
    var totalDistanceCovered by remember { mutableStateOf(0.0) }

    // Remaining distance
    var remainingDistance by remember { mutableStateOf(normalStops.sumOf { it.second }) }

    // Calculate total distance covered and remaining distance
    LaunchedEffect(currentStopIndex.value) {
        totalDistanceCovered = normalStops.subList(1, currentStopIndex.value + 1).sumOf { it.second }
        remainingDistance = normalStops.subList(currentStopIndex.value + 1, normalStops.size).sumOf { it.second }
    }
    Column {
        TextBox(
            normalStops = normalStops,
            currentStopIndex = currentStopIndex.value,
            isKilometers = isKilometers.value,
            totalDistanceCovered = totalDistanceCovered,
            remainingDistance = remainingDistance
        )

        ProgressBar(
            currentStopIndex = currentStopIndex.value,
            totalStops = totalStops
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            ConvertUnitsButton(isKilometers = isKilometers)
            NextStopButton(
                currentStopIndex = currentStopIndex,
                totalStops = totalStops,
                isKilometers = isKilometers,
                onStopReached = {
                    if (currentStopIndex.value < totalStops - 1) {
                        currentStopIndex.value++
                    }
                }
            )
        }
        ListTypeTextBox(normalStops)
        LoadStopsButton(normalStops) // Adding Load Stops button
    }
}

@Composable
fun LoadStopsButton(
    normalStops: List<Pair<String, Double>>
) {
    var showDialog by remember { mutableStateOf(false) }

    Button(
        onClick = { showDialog = true },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "Load Stops")
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Stops") },
            text = {
                if (normalStops.size > 10) {
                    LazyColumn {
                        items(normalStops.size) { index ->
                            val (stopName, distance) = normalStops[index]
                            Text(
                                text = "$stopName - ${formatDistance(distance, true)}",
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                } else {
                    Column {
                        normalStops.forEach { (stopName, distance) ->
                            Text(
                                text = "$stopName - ${formatDistance(distance, true)}",
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showDialog = false }
                ) {
                    Text(text = "Close")
                }
            }
        )
    }
}



@Composable
fun ListTypeTextBox(normalStops: List<Pair<String, Double>>) {
    val listType = if (normalStops.size <= 10) "Normal list" else "Lazy list"
    Text(
        text = "List Type: $listType",
        modifier = Modifier.padding(16.dp)
    )
}
@Composable
fun ProgressBar(currentStopIndex: Int, totalStops: Int) {
    val progress = if (currentStopIndex == totalStops - 1) 1.0f else currentStopIndex.toFloat() / totalStops.toFloat()
//    LinearProgressIndicator(
//        progress = progress,
//        modifier = Modifier.padding(16.dp)
//    )
    LinearProgressIndicator(
        progress = progress,
        modifier = Modifier
            .padding(horizontal = 16.dp) // Add horizontal padding
            .fillMaxWidth() // Make it cover the whole width of the screen
    )
}

@Composable
fun TextBox(
    normalStops: List<Pair<String, Double>>,
    currentStopIndex: Int,
    isKilometers: Boolean,
    totalDistanceCovered: Double,
    remainingDistance: Double
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Current Stop: ${normalStops[currentStopIndex].first}"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Total Distance Covered: ${formatDistance(totalDistanceCovered, isKilometers)}")
        Spacer(modifier = Modifier.height(8.dp))
        if (currentStopIndex < normalStops.size - 1) {
            val nextStop = normalStops.getOrNull(currentStopIndex + 1)
            if (nextStop != null) {
                val (stopName, stopDistance) = nextStop
                Text(
                    text = "Next Stop: $stopName, " +
                            "Distance: ${formatDistance(stopDistance, isKilometers)}"
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Text(text = "Remaining Distance: ${formatDistance(remainingDistance, isKilometers)}")
        Spacer(modifier = Modifier.height(8.dp))
    }
}


// Function to format distance based on selected unit
private fun formatDistance(distance: Double, isKilometers: Boolean): String {
    val unit = if (isKilometers) "km" else "mi"
    val convertedDistance = if (isKilometers) distance else distance * 0.621371 // convert kilometers to miles if necessary
    return String.format("%.2f %s", convertedDistance, unit)
}

@Composable
fun ConvertUnitsButton(isKilometers: MutableState<Boolean>) {
    Button(onClick = { isKilometers.value = !isKilometers.value }) {
        Text(text = if (isKilometers.value) "Km to Mile" else "Mile to Km")
    }
}

@Composable
fun NextStopButton(
    currentStopIndex: MutableState<Int>,
    totalStops: Int,
    isKilometers: MutableState<Boolean>,
    onStopReached: () -> Unit
)
{
    Button(onClick = {
        onStopReached()
    })
    {
        Text(text = "Next Stop")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        MainContent()
    }
}

