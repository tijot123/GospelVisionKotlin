package com.smvis123

import android.os.Bundle
import android.view.MenuItem
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import com.smvis123.base.BaseActivity
import com.smvis123.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about)
        setUpActionBar(binding.toolbar, getString(R.string.about_us))
        binding.textView.text = HtmlCompat.fromHtml(
            "Gospel Vision, the pioneer Christian television channel in Sri Lanka, operates under the banner of Zoe Life Ministries and is committed to the vision entrusted to it by the living God - a God who cares for the nations of the world as much as He does for each individual. As people belonging to God's great family and who have experienced the love of our Father God. It is our privilege and honour to share this good news with all the world."
                    + "<br><br>Therefore, it is our passion to take the Gospel with its power to bring hope in a world riddled with pain, purpose in every life without meaning and joy in every situation where there is nothing but sorrow, to the uttermost parts of this earth, in our quest to 'reach the unreached'"
                    + "<br><br>We believe that God is the strength of our hearts and His Word is life � the zoe, abundant life. We are certain that one could never have 'enough' of the Word of God for in it is the power of transformation and it is our desire to see every believer powerfully impacted by the Word. To this end, we telecast systematic preaching of local and foreign preachers, Biblical answers for healthy living, secrets to financial freedom, praise and worship, live talk-shows, kids and teens programmes, women's hour etc. among others, with a view to making the Gospel accessible to people from all walks of life. As such, we are committed to 'preach the Word of God and to equip and build up the saints'."
                    + "<br><br>We have further been bestowed the honour of hosting many great servants of God from highly acclaimed churches both locally and internationally and are thus striving towards our goal of 'bringing unity and oneness to the body of Christ'."
                    + "<br><br>Gospel Vision is currently hosted on LBN TV and on the Webcast www.gospelvision.tv. LBN covers Colombo and other parts of Sri Lanka and has a customer base of over 35,000, while the Webcast has a worldwide coverage.",
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
