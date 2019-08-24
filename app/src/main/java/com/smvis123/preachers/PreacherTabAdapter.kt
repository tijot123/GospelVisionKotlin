package com.smvis123.preachers

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.smvis123.helper.POSITION
import com.smvis123.helper.PREACHER_DATA
import com.smvis123.model.Preachers

class PreacherTabAdapter(
    fm: FragmentManager,
    private val titleList: Array<String>,
    private val preacher: Preachers
) :
    FragmentStatePagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getItem(position: Int): Fragment {
        val bundle = Bundle()
        val fragment = PreacherFragment()
        bundle.putSerializable(PREACHER_DATA, preacher)
        bundle.putInt(POSITION, position)
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return titleList.count()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }
}