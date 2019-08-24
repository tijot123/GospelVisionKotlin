package com.smvis123.preachers

import androidx.lifecycle.MutableLiveData
import com.smvis123.base.BaseViewModel
import com.smvis123.model.Preachers
import com.smvis123.model.PreachersResponse
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PreachersActivityViewModel : BaseViewModel() {
    val pastorList: MutableLiveData<MutableList<Preachers>> = MutableLiveData()

    fun getPastorsList(params: MutableMap<String, String>) {
        if (apiInterface == null) getApiInterfaceImplementation()
        val dataObserve: Single<PreachersResponse>? =
            apiInterface?.getPastorsList(params)
        dataObserve!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<PreachersResponse> {
                override fun onSuccess(response: PreachersResponse) {
                    pastorList.value = response.pastorList
                    isApiSuccess.value = true
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    isApiSuccess.value = false
                }

            })
    }
}