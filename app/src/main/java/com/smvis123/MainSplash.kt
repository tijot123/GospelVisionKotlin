package com.smvis123

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityMainSplashBinding

class MainSplash : BaseActivity() {

    private lateinit var binding: ActivityMainSplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_splash)
        Handler().postDelayed({
            val intent = Intent(this@MainSplash, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
