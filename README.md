# OBS-Cam

A practical Android application for using an Android phone as a professional IP camera and HDMI-based camera source for OBS Studio, with a local web control panel.

## Features
- Rear/front camera switching
- 720p, 1080p, and 4K options when supported
- 24 / 30 / 60 FPS selection where supported
- Digital zoom, autofocus, exposure, white balance, torch
- Local MJPEG and a low-latency OBS-friendly network path
- Browser remote control via `/control`
- Foreground service with streaming notification
- Hardware limitation awareness for HDMI output and USB webcam support

## Hardware modes

### A. IP Camera (Wi‑Fi/LAN)
Android phone -> Wi‑Fi/LAN -> OBS PC

- Works via `http://PHONE-IP:8080` or `http://PHONE-IP:8080/stream`
- Best for local network use and browser control
- Compatible with OBS Browser Source or other network input methods

### B. HDMI Camera
Android phone -> USB-C alt mode -> HDMI adapter -> HDMI capture card -> OBS

- Requires phone hardware support for DisplayPort Alt Mode
- Use the dedicated HDMI output mode in the app
- If unsupported, the app will warn the user and fall back to IP-cam mode

### C. USB Webcam
Android phone -> USB-C -> USB -> OBS PC

- Only available when Android offers native UVC webcam support
- Not all phones support this; do not assume a normal USB-C-to-USB cable makes the phone behave as a UVC webcam
- If not available, the app switches to network/IP-camera mode and explains the limitation

## Project structure
- `settings.gradle.kts`
- `build.gradle.kts`
- `app/build.gradle.kts`
- `app/src/main/AndroidManifest.xml`
- `app/src/main/java/com/obscam/app/...`
- `app/src/main/assets/control/index.html`
- `README.md`

## Setup
1. Open the project in Android Studio.
2. Let Gradle sync.
3. Connect a device or emulator with API 26+.
4. Choose `Run > Run 'app'`.
5. Grant camera and microphone permissions when prompted.

## OBS setup

### Browser source
- Use: `http://PHONE-IP:8080/`

### MJPEG stream
- Use: `http://PHONE-IP:8080/stream`

This is intended for OBS Browser Source or input devices that can read a local MJPEG URL.

## USB-C / HDMI setup
1. Confirm your phone supports DisplayPort Alt Mode.
2. Connect the phone to a USB-C to HDMI adapter.
3. Feed the HDMI output into a capture card.
4. In the app, enable HDMI Output Mode.
5. Use the camera preview full-screen for clean capture.

## Network setup
1. Connect phone and OBS computer to the same Wi‑Fi or LAN.
2. Open the app.
3. Observe the displayed local IP address.
4. Browse to `http://PHONE-IP:8080/control` on any device on the network.

## Troubleshooting
- No camera preview: verify camera permissions and device support.
- No Wi‑Fi IP: ensure the phone is connected to Wi‑Fi or LAN.
- HDMI unavailable: phone lacks DisplayPort Alt Mode or output support.
- USB webcam unsupported: Android does not provide UVC webcam support.
- Streaming fails: reset the foreground service and confirm local network connectivity.

## Requirements
- Android Studio
- Kotlin
- Gradle
- AndroidX
- CameraX / Camera2 concepts
- Material Design
- Min Android API 26 (Android 8.0)

## Notes
This starter project lays out a production-oriented Android app structure and includes the control interface, streaming server, foreground service, and hardware-awareness docs requested in the brief. It is designed to be built and extended in Android Studio for a real device.
