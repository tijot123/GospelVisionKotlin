package com.smvis123.preachers


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.smvis123.R
import com.smvis123.databinding.FragmentPreacherBinding
import com.smvis123.helper.POSITION
import com.smvis123.helper.PREACHER_DATA
import com.smvis123.model.Preachers

class PreacherFragment : Fragment() {
    private lateinit var binding: FragmentPreacherBinding
    private var preacher: Preachers? = null
    private var position: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preacher = arguments?.getSerializable(PREACHER_DATA) as Preachers?
        position = arguments?.getInt(POSITION, 0) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_preacher, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        when (position) {
            0 -> {
                binding.aboutTab.visibility = View.VISIBLE
                binding.contactTab.visibility = View.GONE
            }
            1 -> {
                binding.aboutTab.visibility = View.GONE
                binding.contactTab.visibility = View.VISIBLE
            }
        }
        binding.nameTvId.text = preacher?.name
        binding.churchTvId.text = preacher?.church
        binding.desiTvId.text = preacher?.designation
        binding.infoTvId.text =
            preacher?.information?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }
        binding.aboutTvId.text =
            preacher?.description?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }
        binding.serTvId.text =
            preacher?.service?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY) }

    }


}
