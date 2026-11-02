package com.fmradio.ultra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Station(val frequency: String, val name: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { FMRadioUltraApp() }
    }
}

@Composable
fun FMRadioUltraApp() {
    val context = androidx.compose.ui.platform.LocalContext.current
    var frequency by remember { mutableStateOf(" FM  98.3 ") }
    var output by remember { mutableStateOf("Headphones") }
    var status by remember { mutableStateOf("FM hardware connection not configured") }\n    var diagnostic by remember { mutableStateOf("Device check not run yet.") }
    val stations = remember {
        listOf(
            Station("91.1 MHz", "Station slot 1"),
            Station("93.5 MHz", "Station slot 2"),
            Station("98.3 MHz", "Station slot 3"),
            Station(" FM 104.0", "Station slot 4")
        )
    }
    MaterialTheme(colorScheme = darkColorScheme()) {
        Box(
            Modifier.fillMaxSize().background(
                Brush.verticalGradient(listOf(Color(0xFF080A10), Color(0xFF171923), Color(0xFF050609)))
            )
        ) {
            Column(
                Modifier.fillMaxSize().padding(18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("FM RADIO ULTRA", color = Color(0xFFFFD36A), fontSize = 22.sp, fontWeight = FontWeight.Black)
                Text("PREMIUM EDITION  •  v0.1", color = Color.LightGray, fontSize = 11.sp)
                Spacer(Modifier.height(22.dp))
                Box(
                    Modifier.size(230.dp).background(
                        Brush.radialGradient(listOf(Color(0xFF6B4A13), Color(0xFF17130D), Color(0xFF08090D))),
                        CircleShape
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("ANALOG TUNER", color = Color(0xFFFFD36A), fontSize = 12.sp)
                        Text(frequency, color = Color.White, fontSize = 30.sp, fontWeight = FontWeight.Bold)
                        Text("87.5 — 108.0 MHz", color = Color.LightGray, fontSize = 11.sp)
                    }
                }
                Spacer(Modifier.height(16.dp))
                Text(status, color = Color(0xFFFFC66D), fontSize = 12.sp)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(onClick = { diagnostic = DeviceProbe.run(context) }) {
                        Text("CHECK DEVICE")
                    }
                    OutlinedButton(onClick = { status = "FM scan is disabled until a supported tuner backend is confirmed" }) {
                        Text("AUTO SCAN")
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text(diagnostic, color = Color.LightGray, fontSize = 10.sp, lineHeight = 14.sp)
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = output == "Headphones", onClick = { output = "Headphones" }, label = { Text("Headphones") })
                    FilterChip(selected = output == "Speaker", onClick = { output = "Speaker" }, label = { Text("Speaker") })
                }
                Text("Audio output: $output", color = Color.White, fontSize = 12.sp)
                Spacer(Modifier.height(14.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("SAVED STATIONS", color = Color(0xFFFFD36A), fontWeight = FontWeight.Bold)
                    Text("Favourites • Manager", color = Color.LightGray, fontSize = 11.sp)
                }
                LazyColumn(Modifier.fillMaxWidth().weight(1f)) {
                    items(stations) { station ->
                        Card(
                            Modifier.fillMaxWidth().padding(vertical = 4.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF20232D)),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Row(Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(station.name, color = Color.White)
                                Text(station.frequency, color = Color(0xFFFFD36A), fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
                Text("Equalizer • Recording • Settings (coming after hardware integration)", color = Color.Gray, fontSize = 10.sp)
            }
        }
    }
}
