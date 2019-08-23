package com.smvis123.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smvis123.api.ApiClient
import com.smvis123.api.ApiInterface

open class BaseViewModel : ViewModel() {
    var apiInterface: ApiInterface? = null
    var isApiSuccess: MutableLiveData<Boolean> = MutableLiveData()

    fun getApiInterfaceImplementation() {
        apiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)
    }
}