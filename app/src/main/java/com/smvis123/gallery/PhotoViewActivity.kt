package com.smvis123.gallery

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.smvis123.R
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityPhotoViewBinding
import com.smvis123.helper.GALLERY_IMAGES
import com.smvis123.model.Lister

class PhotoViewActivity : BaseActivity() {
    private lateinit var binding: ActivityPhotoViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_photo_view)
        setUpActionBar(binding.toolbar, getString(R.string.gallery),binding.toolbarTitle)
        val lister: Lister = intent.getSerializableExtra(GALLERY_IMAGES) as Lister
        val galleryList = lister.galleryList
        val adapter = PhotoViewAdapter(galleryList)
        binding.pager.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
