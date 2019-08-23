package com.smvis123.player

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.smvis123.R
import com.smvis123.databinding.ActivityVideoPlayerBinding
import com.smvis123.helper.Utils
import com.smvis123.helper.VIDEO_URl

class VideoPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_player)
        val videoUrl = intent.getStringExtra(VIDEO_URl)
        val mediaController = MediaController(this)
        binding.videoView.setMediaController(mediaController)
        binding.videoView.setZOrderMediaOverlay(true)
        binding.videoView.setVideoURI(Uri.parse(videoUrl))
        binding.videoView.setOnCompletionListener {
            it.start()
        }
        binding.videoView.setOnPreparedListener {
            binding.videoView.start()
        }
        binding.videoView.setOnErrorListener { _, _, _ ->
            Utils.showToast(getString(R.string.no_live_data), this)
            onBackPressed()
            true
        }
    }

    override fun onPause() {
        binding.videoView.pause()
        super.onPause()
    }

    override fun onResume() {
        binding.videoView.resume()
        super.onResume()
    }
}
