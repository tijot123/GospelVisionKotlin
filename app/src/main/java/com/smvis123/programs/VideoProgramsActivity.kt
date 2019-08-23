package com.smvis123.programs

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.smvis123.R
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityVideoProgramsBinding
import com.smvis123.helper.VIDEO_CATEGORY_ID
import com.smvis123.model.Videos

class VideoProgramsActivity : BaseActivity(), VideoClickedListener {
    override fun onVideoClicked(video: Videos) {
        playYoutubeVideo(video.videoUrl)
    }

    private lateinit var binding: ActivityVideoProgramsBinding
    private lateinit var viewModel: VideoProgramsActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_programs)
        viewModel = ViewModelProviders.of(this).get(VideoProgramsActivityViewModel::class.java)
        setUpActionBar(binding.toolbar, getString(R.string.programmes))
        initRecyclerView()
        initObservers()
        val videoCategoryId = intent.getStringExtra(VIDEO_CATEGORY_ID) as String
        viewModel.getVideosList(params, videoCategoryId)
        showProgressView()
    }

    private fun initObservers() {
        viewModel.isApiSuccess.observe(this, Observer {
            hideProgressView()
        })
        viewModel.videosList.observe(this, Observer {
            val adapter = VideoProgramsAdapter(it, this@VideoProgramsActivity)
            binding.recyclerView.adapter = adapter
        })
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
