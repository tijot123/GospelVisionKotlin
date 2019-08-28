package com.smvis123.preachers

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.smvis123.R
import com.smvis123.api.IMAGE_BASE_URL
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityPreacherDetailsBinding
import com.smvis123.helper.GlideApp
import com.smvis123.helper.PREACHER_DATA
import com.smvis123.model.Preachers

class PreacherDetailsActivity : BaseActivity() {
    private lateinit var binding: ActivityPreacherDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preacher_details)
        val preacher: Preachers = intent.getSerializableExtra(PREACHER_DATA) as Preachers
        setUpActionBar(binding.toolbar, preacher.name, binding.toolbarTitle)

        GlideApp.with(this).load(IMAGE_BASE_URL + preacher.thumbnail).into(binding.imageView)

        val tabTitles = resources?.getStringArray(R.array.preacher_date)
        val adapter = tabTitles?.let { PreacherTabAdapter(supportFragmentManager, it, preacher) }
        binding.pager.adapter = adapter
        binding.tabs.setupWithViewPager(binding.pager, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        supportFinishAfterTransition()
    }
}
