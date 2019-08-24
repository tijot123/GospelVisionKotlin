package com.smvis123.base

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    fun showProgressView() {
        (activity as BaseActivity).showProgressView()
    }

    fun hideProgressView() {
        (activity as BaseActivity).hideProgressView()
    }
}