package com.example.assignment_1
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import android.os.Bundle

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
            "Stop 8" to 30.0,
            "Stop 9" to 40.0,
            "Stop 10" to 50.0,
            "Stop 11" to 10.0,
            "Stop 12" to 20.0,
            "Stop 13" to 30.0,
            "Stop 14" to 40.0,
            "Stop 15" to 50.0,
            "Stop 16" to 10.0,
            "Stop 17" to 20.0,
            "Stop 18" to 30.0,
            "Stop 19" to 40.0,
            "Stop 20" to 50.0,
            "Stop 21" to 10.0,
            "Stop 22" to 20.0,
            "Stop 23" to 30.0,
            "Stop 24" to 40.0,
            "Stop 25" to 50.0,
            "Stop 26" to 10.0,
            "Stop 27" to 20.0,
            "Stop 28" to 30.0,
            "Stop 29" to 40.0,
            "Stop 30" to 50.0,
            "Stop 31" to 10.0,
            "Stop 32" to 20.0,
            "Stop 33" to 30.0,
            "Stop 34" to 40.0,
            "Stop 35" to 50.0,
            "Stop 36" to 10.0,
            "Stop 37" to 20.0,
            "Stop 38" to 30.0,
            "Stop 39" to 40.0,
            "Stop 40" to 50.0,
            "destination" to 60.0
        )
    }
    val currentStopIndex = remember { mutableStateOf(0) }
    val isKilometers = remember { mutableStateOf(true) }
    val totalStops = normalStops.size
    var totalDistanceCovered by remember { mutableStateOf(0.0) }
    var remainingDistance by remember { mutableStateOf(normalStops.sumOf { it.second }) }
    LaunchedEffect(currentStopIndex.value) {
        totalDistanceCovered = normalStops.subList(1, currentStopIndex.value + 1).sumOf { it.second }
        remainingDistance = normalStops.subList(currentStopIndex.value + 1, normalStops.size).sumOf { it.second }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF87CEEB))
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            ProgressBar(
                currentStopIndex = currentStopIndex.value,
                totalStops = totalStops
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextBox(
                normalStops = normalStops,
                currentStopIndex = currentStopIndex.value,
                isKilometers = isKilometers.value,
                totalDistanceCovered = totalDistanceCovered,
                remainingDistance = remainingDistance
            )
            Spacer(modifier = Modifier.height(300.dp))
            ListTypeTextBox(normalStops)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            )
            {
                Spacer(modifier = Modifier.weight(0.07f))
                ConvertUnitsButton(isKilometers = isKilometers)
                Spacer(modifier = Modifier.weight(0.5f))
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
                Spacer(modifier = Modifier.weight(0.07f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            // Usage in MainContent function
            LoadStopsButton(
                originalNormalStops = normalStops,
                currentStopIndex = currentStopIndex.value,
                isKilometers = isKilometers,
                onStopsLoaded = { loadedStops ->
                    // You can perform further operations on loadedStops if needed
                    // this@MainActivity.loadedStops = loadedStops
                }
            )

        }
    }



}
@Composable
fun LoadStopsButton(
    originalNormalStops: List<Pair<String, Double>>,
    currentStopIndex: Int,
    isKilometers: MutableState<Boolean>,
    onStopsLoaded: (List<Pair<String, Double>>) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }
    var loadedStops: List<Pair<String, Double>>? = null

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
                LazyColumn {
                    items(loadedStops?.size ?: originalNormalStops.size) { index ->
                        val stop = loadedStops?.getOrNull(index) ?: originalNormalStops[index]
                        val textColor = if (index == currentStopIndex) Color.Magenta else Color.Black
                        Text(
                            text = "${stop.first} - ${formatDistance(stop.second, isKilometers.value)}",
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = textColor
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        loadedStops?.let { onStopsLoaded(it) }
                    }
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
    LinearProgressIndicator(
        progress = progress,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
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

private fun formatDistance(distance: Double, isKilometers: Boolean): String {
    val unit = if (isKilometers) "km" else "mi"
    val convertedDistance = if (isKilometers) distance else distance * 0.621371
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
