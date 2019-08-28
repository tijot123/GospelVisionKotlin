package com.smvis123.preachers

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.smvis123.R
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityPreachersBinding
import com.smvis123.helper.PREACHER_DATA
import com.smvis123.model.Preachers

class PreachersActivity : BaseActivity(), PreacherItemClickListener {
    override fun onItemClicked(preacher: Preachers, imageView: ImageView) {
        val intent = Intent(this@PreachersActivity, PreacherDetailsActivity::class.java)
        intent.putExtra(PREACHER_DATA, preacher)
        val options = ViewCompat.getTransitionName(imageView)?.let {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@PreachersActivity,
                imageView,
                it
            )
        }
        startActivity(intent, options?.toBundle())
    }

    private lateinit var viewModel: PreachersActivityViewModel
    private lateinit var binding: ActivityPreachersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_preachers)
        viewModel = ViewModelProviders.of(this).get(PreachersActivityViewModel::class.java)
        setUpActionBar(binding.toolbar, getString(R.string.preachers), binding.toolbarTitle)
        initObservers()
        initRecyclerView()

        viewModel.getPastorsList(params)
        showProgressView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initObservers() {
        viewModel.isApiSuccess.observe(this, Observer {
            hideProgressView()
        })
        viewModel.pastorList.observe(this, Observer {
            val adapter = PreachersAdapter(it, this@PreachersActivity)
            binding.recyclerView.adapter = adapter
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
