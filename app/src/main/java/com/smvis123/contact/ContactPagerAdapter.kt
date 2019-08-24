package com.smvis123.contact

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ContactPagerAdapter(fm: FragmentManager, private val itemList: Array<String>) :
    FragmentStatePagerAdapter(
        fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> ContactFragment()
            else -> FeedbackFragment()
        }
    }

    override fun getCount(): Int {
        return itemList.count()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return itemList[position]
    }
}