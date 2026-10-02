package com.obscam.app

import androidx.compose.runtime.Immutable

@Immutable
data class CameraSettings(
    val cameraFacing: String = "Rear",
    val resolution: String = "1080p",
    val fps: Int = 30,
    val zoom: Float = 1f,
    val torchEnabled: Boolean = false,
    val isStreaming: Boolean = false,
    val lowLatencyMode: Boolean = true,
    val quality: Int = 75,
    val bitrateKbps: Int = 3000,
    val microphoneEnabled: Boolean = false,
    val localOnlyMode: Boolean = true,
    val port: Int = 8080,
    val username: String = "",
    val password: String = ""
)
