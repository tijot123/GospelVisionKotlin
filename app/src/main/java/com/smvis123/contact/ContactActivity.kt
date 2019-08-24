package com.smvis123.contact

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.smvis123.R
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityContactBinding

class ContactActivity : BaseActivity() {
    private lateinit var binding: ActivityContactBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contact)
        setUpActionBar(binding.toolbar, getString(R.string.contact_us),binding.toolbarTitle)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val contactFragmentTitles =
            resources?.getStringArray(R.array.contactFragmentTitle) as Array<String>
        val contactFragmentAdapter =
            ContactPagerAdapter(supportFragmentManager, contactFragmentTitles)
        binding.pager.adapter = contactFragmentAdapter
        binding.tabs.setupWithViewPager(binding.pager, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
