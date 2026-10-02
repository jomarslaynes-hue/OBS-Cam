package com.obscam.app

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BatteryStd
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.obscam.app.ui.theme.ObsCamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ObsCamTheme {
                ObsCamHomeScreen()
            }
        }
    }
}

@Composable
fun ObsCamHomeScreen() {
    var settings by remember { mutableStateOf(CameraSettings()) }
    var zoom by remember { mutableFloatStateOf(settings.zoom) }
    val ipAddress = remember { mutableStateOf(NetworkInfo.getLocalIpAddressFallback()) }

    Scaffold(containerColor = MaterialTheme.colorScheme.background) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("OBS-Cam", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                    Text("IP Camera + HDMI Output", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Button(onClick = { settings = settings.copy(isStreaming = !settings.isStreaming) }) {
                    Text(if (settings.isStreaming) "Stop" else "Start")
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Color(0xFF101418)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Filled.CameraAlt,
                            contentDescription = null,
                            tint = Color(0xFF76C5FF),
                            modifier = Modifier
                                .background(Color(0xFF1B2430), RoundedCornerShape(20.dp))
                                .padding(18.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Camera preview", fontSize = 18.sp, color = Color.White)
                        Text(
                            text = "${settings.resolution} • ${settings.fps} FPS • ${settings.cameraFacing}",
                            color = Color(0xFFB9D6F2)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatusChip(label = "IP", value = "http://${ipAddress.value}:8080")
                StatusChip(label = "Status", value = if (settings.isStreaming) "Live" else "Idle")
                StatusChip(label = "Wi‑Fi", value = "Connected")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatusChip(label = "Battery", value = "82%")
                StatusChip(label = "Temp", value = "38°C")
                StatusChip(label = "Clients", value = "0")
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Camera controls", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Front / Rear")
                        Button(onClick = {
                            settings = settings.copy(cameraFacing = if (settings.cameraFacing == "Rear") "Front" else "Rear")
                        }) {
                            Text(settings.cameraFacing)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Resolution")
                        Button(onClick = { settings = settings.copy(resolution = if (settings.resolution == "1080p") "720p" else "1080p") }) {
                            Text(settings.resolution)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("FPS")
                        Button(onClick = { settings = settings.copy(fps = if (settings.fps == 30) 60 else 30) }) {
                            Text("${settings.fps}")
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Low latency")
                        Switch(
                            checked = settings.lowLatencyMode,
                            onCheckedChange = { settings = settings.copy(lowLatencyMode = it) }
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Torch")
                        Switch(
                            checked = settings.torchEnabled,
                            onCheckedChange = { settings = settings.copy(torchEnabled = it) }
                        )
                    }

                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text("Zoom: ${zoom.toString().take(3)}x")
                        Slider(
                            value = zoom,
                            onValueChange = {
                                zoom = it
                                settings = settings.copy(zoom = it)
                            },
                            valueRange = 1f..5f,
                            steps = 4
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = { /* focus */ }, modifier = Modifier.weight(1f)) { Text("AF") }
                Button(onClick = { /* exposure */ }, modifier = Modifier.weight(1f)) { Text("Exposure") }
                Button(onClick = { /* settings */ }, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Filled.Settings, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Setup")
                }
            }
        }
    }
}

@Composable
private fun StatusChip(label: String, value: String) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        modifier = Modifier.weight(1f)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, fontWeight = FontWeight.SemiBold)
        }
    }
}
