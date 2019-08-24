package com.smvis123.gallery

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.smvis123.R
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityGalleryBinding
import com.smvis123.helper.GALLERY_IMAGES
import com.smvis123.helper.Utils
import com.smvis123.model.GalleryAlbums
import com.smvis123.model.Lister

class GalleryActivity : BaseActivity(), GalleryItemClickedListener {
    override fun onItemClicked(item: GalleryAlbums) {
        viewModel.getGalleryAlbumImages(params, item.id)
        showProgressView()
    }

    private lateinit var binding: ActivityGalleryBinding
    private lateinit var viewModel: GalleryActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)
        viewModel = ViewModelProviders.of(this).get(GalleryActivityViewModel::class.java)
        setUpActionBar(binding.toolbar, getString(R.string.gallery),binding.toolbarTitle)
        initRecyclerView()
        initObservers()
        viewModel.getGalleryAlbums(params)
        showProgressView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun initObservers() {
        viewModel.isApiSuccess.observe(this, Observer {
            hideProgressView()
        })
        viewModel.galleryAlbumsList.observe(this, Observer {
            val adapter = GalleryAlbumAdapter(it, this@GalleryActivity)
            binding.recyclerView.adapter = adapter
        })
        viewModel.galleryAlbumsImagesList.observe(this, Observer {
            if (it.isNotEmpty()) {
                val lister = Lister(it)
                val intent = Intent(this@GalleryActivity, PhotoViewActivity::class.java)
                intent.putExtra(GALLERY_IMAGES, lister)
                startActivity(intent)
            } else Utils.showSnackView(getString(R.string.no_images), binding.root)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
