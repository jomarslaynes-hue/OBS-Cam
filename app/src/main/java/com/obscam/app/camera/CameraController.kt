package com.obscam.app.camera

import android.content.Context
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.Executor

class CameraController(
    private val context: Context,
    private val lifecycleOwner: LifecycleOwner,
    private val executor: Executor = ContextCompat.getMainExecutor(context)
) {
    private var cameraProvider: ProcessCameraProvider? = null
    private var camera: Camera? = null
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    fun bind(previewView: PreviewView, lensFacing: Int = CameraSelector.LENS_FACING_BACK) {
        this.lensFacing = lensFacing
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }

            val selector = CameraSelector.Builder()
                .requireLensFacing(this.lensFacing)
                .build()

            try {
                cameraProvider?.unbindAll()
                camera = cameraProvider?.bindToLifecycle(lifecycleOwner, selector, preview)
                Log.d("OBS-Cam", "Camera bound successfully")
            } catch (error: Exception) {
                Log.e("OBS-Cam", "Unable to bind camera", error)
            }
        }, executor)
    }

    fun switchCamera(previewView: PreviewView) {
        lensFacing = if (lensFacing == CameraSelector.LENS_FACING_BACK) {
            CameraSelector.LENS_FACING_FRONT
        } else {
            CameraSelector.LENS_FACING_BACK
        }
        bind(previewView, lensFacing)
    }

    fun setTorch(enabled: Boolean) {
        camera?.cameraControl?.enableTorch(enabled)
    }

    fun setZoom(level: Float) {
        camera?.cameraControl?.setZoomRatio(level.coerceIn(1f, 5f))
    }
}
