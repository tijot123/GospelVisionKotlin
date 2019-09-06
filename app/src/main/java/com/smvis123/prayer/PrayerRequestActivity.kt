package com.smvis123.prayer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.smvis123.R
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityPrayerRequestBinding
import com.smvis123.helper.Utils


class PrayerRequestActivity : BaseActivity() {
    private lateinit var binding: ActivityPrayerRequestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_prayer_request)
        setUpActionBar(binding.toolbar, getString(R.string.prayer_request), binding.toolbarTitle)
        binding.buttonSend.setOnClickListener {
            Utils.hideKeyboard(this@PrayerRequestActivity)
            val name = binding.editTextName.text.toString()
            val email = binding.editTextEmail.text.toString()
            val message = binding.editTextMessage.text.toString()
            if (!TextUtils.isEmpty(name)) {
                binding.editTextName.error = null
                if (!TextUtils.isEmpty(email)) {
                    binding.editTextEmail.error = null
                    if (email.matches(Patterns.EMAIL_ADDRESS.toRegex())) {
                        binding.editTextEmail.error = null
                        if (!TextUtils.isEmpty(message)) {
                            binding.editTextMessage.error = null
                            sendPrayerRequest(name, email, message)
                        } else binding.editTextMessage.error = getString(R.string.required)
                    } else binding.editTextEmail.error = getString(R.string.email_error)
                } else binding.editTextEmail.error = getString(R.string.required)
            } else binding.editTextName.error = getString(R.string.required)
        }
    }

    private fun sendPrayerRequest(name: String, email: String, message: String) {
        try {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "gospelvisiontvsl@gmail.com"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "PRAYER REQUEST")
            intent.putExtra(
                Intent.EXTRA_TEXT, "Name    :  " + name + "\n\n"
                        + "E-mail :  " + email + "\n\n"
                        + "Request :  " + message
            )
            startActivity(intent)
            clearData()

        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

    }

    private fun clearData() {
        binding.editTextEmail.setText("")
        binding.editTextMessage.setText("")
        binding.editTextName.setText("")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
