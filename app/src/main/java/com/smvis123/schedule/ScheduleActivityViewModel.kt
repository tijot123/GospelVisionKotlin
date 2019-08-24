package com.smvis123.schedule

import androidx.lifecycle.MutableLiveData
import com.smvis123.base.BaseViewModel
import com.smvis123.model.ScheduleResponse
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ScheduleActivityViewModel : BaseViewModel() {
    val scheduleList: MutableLiveData<ScheduleResponse> = MutableLiveData()
    fun getScheduleList(params: MutableMap<String, String>) {
        if (apiInterface == null) getApiInterfaceImplementation()
        val dataObserve: Single<ScheduleResponse>? =
            apiInterface?.scheduleCall(params)
        dataObserve!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ScheduleResponse> {
                override fun onSuccess(response: ScheduleResponse) {
                    scheduleList.value = response
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