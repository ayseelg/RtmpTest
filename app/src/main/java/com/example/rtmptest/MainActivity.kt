package com.example.rtmptest

import android.Manifest
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.rtmplibrary.presentation.viewmodel.StreamViewModel
import com.example.rtmplibrary.presentation.viewmodel.StreamViewModelFactory
import com.pedro.library.view.OpenGlView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var surfaceView: OpenGlView
    private lateinit var etRtmpUrl: EditText
    private lateinit var btnStartStop: Button
    private lateinit var btnSwitchCamera: Button

    private lateinit var viewModel: StreamViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val cameraPermission = permissions[Manifest.permission.CAMERA] ?: false
            val audioPermission = permissions[Manifest.permission.RECORD_AUDIO] ?: false

            if (cameraPermission && audioPermission) {
                initRtmpLibrary()
            } else {
                Toast.makeText(this, "Kamera ve Mikrofon izni gerekli!", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        surfaceView = findViewById(R.id.surfaceView)
        etRtmpUrl = findViewById(R.id.etRtmpUrl)
        btnStartStop = findViewById(R.id.btnStartStop)
        btnSwitchCamera = findViewById(R.id.btnSwitchCamera)

        viewModel = ViewModelProvider(this, StreamViewModelFactory())[StreamViewModel::class.java]

        checkPermissions()

        btnStartStop.setOnClickListener {
            if (viewModel.isStreaming) {
                viewModel.stopStream()
                btnStartStop.text = "Yayını Başlat"
            } else {
                val url = etRtmpUrl.text.toString()
                if (url.isNotEmpty()) {
                    viewModel.startStream(url)
                    btnStartStop.text = "Yayını Durdur"
                } else {
                    Toast.makeText(this, "Lütfen geçerli bir RTMP URL'si girin", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnSwitchCamera.setOnClickListener {
            viewModel.switchCamera()
        }
        
        lifecycleScope.launch {
            viewModel.streamState.collectLatest { state ->
                btnStartStop.text = if (viewModel.isStreaming) "Yayını Durdur" else "Yayını Başlat"
            }
        }
    }

    private fun checkPermissions() {
        requestPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            )
        )
    }

    private fun initRtmpLibrary() {
        viewModel.initCamera(surfaceView)
        viewModel.bindLifecycle(lifecycle)
        viewModel.startPreview()
    }
}
