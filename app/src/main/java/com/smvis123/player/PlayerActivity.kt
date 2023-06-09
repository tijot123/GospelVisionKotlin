package com.smvis123.player

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_ALWAYS
import com.smvis123.databinding.ActivityPlayerBinding
import com.smvis123.helper.VIDEO_URl


class PlayerActivity : AppCompatActivity() {

    private var player: ExoPlayer? = null
    private var playWhenReadyInstant = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private val binding: ActivityPlayerBinding by lazy {
        ActivityPlayerBinding.inflate(
            layoutInflater
        )
    }
    private val videoUrlLive by lazy { intent?.getStringExtra(VIDEO_URl).toString() }
    private val playbackStateListener: Player.Listener = playbackStateListener()

    @SuppressLint("StaticFieldLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    private fun invokePlayer(videoUrl: String) {
        val mediaItem = MediaItem.fromUri(videoUrl)
        binding.playerView.player?.apply {
            setMediaItem(mediaItem)
            playWhenReady = playWhenReadyInstant
            seekTo(currentItem, playbackPosition)
            addListener(playbackStateListener)
            prepare()
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    private fun initialisePlayer() {
        Log.e("initialisePlayer: ", videoUrlLive)
        val trackSelector = DefaultTrackSelector(this).apply {
            setParameters(buildUponParameters().setMaxVideoSizeSd())
        }
        player = ExoPlayer.Builder(this).setTrackSelector(trackSelector).build().also { exoPlayer ->
            binding.playerView.apply {
                player = exoPlayer
                setShowNextButton(false)
                setShowPreviousButton(false)
                setShowRewindButton(false)
                setShowFastForwardButton(false)
                setShowShuffleButton(false)
                setShowMultiWindowTimeBar(true)
                setShowBuffering(SHOW_BUFFERING_ALWAYS)
            }
        }
        invokePlayer(videoUrlLive)
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initialisePlayer()
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT <= 23 || player == null) {
            initialisePlayer()
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playWhenReadyInstant = exoPlayer.playWhenReady
            currentItem = exoPlayer.currentMediaItemIndex
            playbackPosition = exoPlayer.currentPosition
            exoPlayer.removeListener(playbackStateListener)
            exoPlayer.release()
        }
        player = null
    }

    private fun hideSystemUi() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.playerView).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun playbackStateListener() = object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }
            Log.d("PlayerActivity", "changed state to $stateString")
        }

        override fun onPlayerError(error: PlaybackException) {
            super.onPlayerError(error)
            error.message?.let { Toast.makeText(this@PlayerActivity, it, Toast.LENGTH_LONG).show() }
                .also {
                    onBackPressed()
                }
        }

        override fun onPlayerErrorChanged(error: PlaybackException?) {
            super.onPlayerErrorChanged(error)
            error?.message?.let {
                Toast.makeText(this@PlayerActivity, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        if (newConfig.orientation == ORIENTATION_PORTRAIT) {
            binding.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        } else binding.playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
        super.onConfigurationChanged(newConfig)
    }
}