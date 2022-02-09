package com.smvis123.web

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.smvis123.R
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityWebBinding
import com.smvis123.helper.WEB_TITLE
import com.smvis123.helper.WEB_URl

class WebActivity : BaseActivity() {

    private lateinit var binding: ActivityWebBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web)
        val webUrl = intent?.getStringExtra(WEB_URl)
        val title = intent?.getStringExtra(WEB_TITLE)
        setUpActionBar(binding.toolbar, title.toString(), binding.toolbarTitle)
        showProgressView()
        setupWebView(webUrl)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView(webUrl: String?) {
        binding.webView.loadUrl(webUrl!!)
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.domStorageEnabled = true
        binding.webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress == 100)
                    hideProgressView()
            }
        }
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideProgressView()
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                showProgressView()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (binding.webView.canGoBack())
            binding.webView.goBack() else
            super.onBackPressed()
    }
}
