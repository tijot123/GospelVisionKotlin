package com.smvis123

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityMainBinding
import com.smvis123.drawer.DrawerAdapter
import com.smvis123.drawer.DrawerItemClickListener
import com.smvis123.helper.*
import com.smvis123.main.MainActivityViewModel
import com.smvis123.main.SliderAdapter
import com.smvis123.main.VideoCategoryAdapter
import com.smvis123.main.VideoItemClickListener
import com.smvis123.model.Category
import com.smvis123.model.Slider
import com.smvis123.player.VideoPlayerActivity
import com.smvis123.programs.VideoProgramsActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : BaseActivity(), DrawerItemClickListener, VideoItemClickListener {
    override fun onVideoItemClicked(videoCategory: Category) {
        val intent = Intent(this@MainActivity, VideoProgramsActivity::class.java)
        intent.putExtra(VIDEO_CATEGORY_ID, videoCategory.id.toString())
        startActivity(intent)
    }

    override fun onItemClicked(position: Int) {
        closeDrawer()
        when (position) {
            4 -> {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun closeDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel
    private val sliderList: MutableList<Slider> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        setUpActionBar(binding.toolbar, "")

        setUpActionBarToggle()
        setUpDrawerItems()
        setUpVideoRecyclerView()
        initialiseObservers()

        showProgressView()
        viewModel.getSliderList(params)

        binding.facebook.setOnClickListener {
            closeDrawer()
            sendWebIntent(this@MainActivity, FACEBOOK, GOSPEL_FACEBOOK)

        }
        binding.website.setOnClickListener {
            closeDrawer()
            sendWebIntent(this@MainActivity, WEBSITE, GOSPEL_WEBSITE)
        }
        binding.liveTv.setOnClickListener {
            if (Utils.isNetworkAvailable(this)) {
                showProgressView()
                viewModel.getLiveTvData(params)
            } else Utils.showSnackView(getString(R.string.network_connection), binding.root)
        }
    }

    private fun initialiseObservers() {
        viewModel.sliderList.observe(this, Observer {
            sliderList.clear()
            sliderList.addAll(it)
            viewModel.getVideoCategoryList(params)
            showProgressView()
        })
        viewModel.isApiSuccess.observe(this, Observer {
            hideProgressView()
        })
        viewModel.videoCategoryList.observe(this, Observer {
            val sliderAdapter = SliderAdapter(sliderList)
            binding.pager.adapter = sliderAdapter
            binding.indicator.setupWithViewPager(pager, true)
            val timer = Timer()
            timer.scheduleAtFixedRate(SliderTimer(), SLIDER_DELAY, SLIDER_PERIOD)
            binding.programTxt.visibility = View.VISIBLE

            val videoCategoryAdapter = VideoCategoryAdapter(it, this@MainActivity)
            binding.recyclerView.adapter = videoCategoryAdapter
        })

        viewModel.liveTvData.observe(this, Observer {
            if (!TextUtils.isEmpty(it.androidUrl)) {
                val intent = Intent(this, VideoPlayerActivity::class.java)
                startActivity(intent)
            } else if (!TextUtils.isEmpty(it.youtubeUrl)) {
                playYoutubeVideo(it.youtubeUrl)
            }
        })
    }

    private fun setUpVideoRecyclerView() {
        binding.recyclerView.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollVertically(): Boolean {
                return false
            }
        }
    }

    private fun setUpDrawerItems() {
        binding.drawerItems.layoutManager = LinearLayoutManager(this)
        binding.drawerItems.setHasFixedSize(true)
        val drawerImages = resources.obtainTypedArray(R.array.drawerImages)
        val drawerTitles = resources.getStringArray(R.array.drawerTitles)
        val drawerAdapter = DrawerAdapter(drawerTitles, drawerImages, this)
        binding.drawerItems.adapter = drawerAdapter
    }

    private fun setUpActionBarToggle() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.app_name,
            R.string.app_name
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private inner class SliderTimer : TimerTask() {
        override fun run() {
            runOnUiThread {
                if (binding.pager.currentItem < sliderList.size - 1) {
                    binding.pager.currentItem = binding.pager.currentItem + 1
                } else binding.pager.currentItem = 0
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        else {
            val builder =
                AlertDialog.Builder(this, R.style.CustomDialogTheme)
            builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton(
                    "Yes"
                ) { _, _ ->
                    this@MainActivity.finish()
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                }
                .setNegativeButton("No") { dialog, _ -> dialog.cancel() }
                .setIcon(R.mipmap.ic_launcher_round).setTitle(R.string.app_name)
            val alert = builder.create()
            alert.show()
        }
    }
}