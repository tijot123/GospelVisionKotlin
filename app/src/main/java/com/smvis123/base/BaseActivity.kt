package com.smvis123.base

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProviders
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.youtube.player.YouTubeStandalonePlayer
import com.smvis123.R
import com.smvis123.api.UNIQUE_ID
import com.smvis123.api.UNIQUE_ID_VALUE
import com.smvis123.helper.REQUEST_CODE_UPDATE
import com.smvis123.player.YoutubeHelper
import java.util.*
import kotlin.collections.HashMap

abstract class BaseActivity : AppCompatActivity() {
    private lateinit var viewModel: BaseViewModel
    val params: MutableMap<String, String> = HashMap()
    private var dialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeProgressDialog()
        viewModel = ViewModelProviders.of(this).get(BaseViewModel::class.java)
        params[UNIQUE_ID] = UNIQUE_ID_VALUE
    }

    private fun initializeProgressDialog() {
        if (dialog == null) dialog = Dialog(this)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCancelable(false)
        dialog?.setContentView(R.layout.progress_view)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun showProgressView() {
        if (dialog == null) return
        if (!dialog?.isShowing!!)
            dialog?.show()
    }

    fun hideProgressView() {
        if (dialog == null) return
        if (dialog?.isShowing!!) dialog?.dismiss()
    }

    fun setUpActionBar(toolbar: Toolbar, title: String, textView: TextView?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        if (!TextUtils.isEmpty(title)) {
            textView?.text = title.toUpperCase(Locale.ENGLISH)
        }
    }


    fun playYoutubeVideo(youtubeUrl: String) {
        val intent = YouTubeStandalonePlayer.createVideoIntent(
            this,
            getString(R.string.google_api_key),
            YoutubeHelper.getYouTubeVideoId(youtubeUrl), 0, true, false
        )
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        startActivity(intent)
    }

    private fun checkForUpdates() {
        val updateManager = AppUpdateManagerFactory.create(this)
        updateManager.appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                updateManager.startUpdateFlowForResult(
                    it,
                    AppUpdateType.IMMEDIATE,
                    this,
                    REQUEST_CODE_UPDATE
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkForUpdates()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_UPDATE) {
            if (requestCode != RESULT_OK) {
                /*If user cancelled the update flow*/
            }
        }
    }
}