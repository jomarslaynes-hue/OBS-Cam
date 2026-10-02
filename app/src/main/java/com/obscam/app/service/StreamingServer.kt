package com.obscam.app.service

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import fi.iki.elonen.NanoHTTPD
import java.io.ByteArrayOutputStream

class StreamingServer(
    private val context: Context,
    private val port: Int = 8080
) : NanoHTTPD(port) {

    override fun serve(session: IHTTPSession): Response {
        val uri = session.uri
        return when {
            uri == "/" || uri == "/control" -> {
                val html = context.assets.open("control/index.html").bufferedReader().use { it.readText() }
                newFixedLengthResponse(Response.Status.OK, "text/html", html)
            }

            uri == "/stream" -> {
                val streamBody = buildMjpegStream()
                newChunkedResponse(Response.Status.OK, "multipart/x-mixed-replace; boundary=obs-cam", streamBody)
            }

            else -> newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not found")
        }
    }

    private fun buildMjpegStream(): java.io.InputStream {
        val boundary = "--obs-cam\r\n"
        val image = buildPlaceholderJpeg()
        val payload = StringBuilder()
        payload.append(boundary)
        payload.append("Content-Type: image/jpeg\r\n")
        payload.append("Content-Length: ${image.size}\r\n\r\n")
        payload.append(image.decodeToString())
        payload.append("\r\n")
        return payload.toString().byteInputStream()
    }

    private fun buildPlaceholderJpeg(): ByteArray {
        val bitmap = Bitmap.createBitmap(1280, 720, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.BLACK)

        val paint = Paint().apply {
            color = Color.WHITE
            textSize = 90f
            isFakeBoldText = true
            textAlign = Paint.Align.CENTER
        }

        canvas.drawText("OBS-Cam", 640f, 360f, paint)

        val output = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, output)
        return output.toByteArray()
    }
}
