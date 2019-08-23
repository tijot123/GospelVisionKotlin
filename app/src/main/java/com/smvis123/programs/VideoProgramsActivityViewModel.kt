package com.smvis123.programs

import androidx.lifecycle.MutableLiveData
import com.smvis123.base.BaseViewModel
import com.smvis123.model.VideoCategoryData
import com.smvis123.model.Videos
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class VideoProgramsActivityViewModel : BaseViewModel() {
    val videosList: MutableLiveData<MutableList<Videos>> = MutableLiveData()

    fun getVideosList(params: MutableMap<String, String>, videoCategoryId: String) {
        if (apiInterface == null) getApiInterfaceImplementation()
        val dataObserve: Single<VideoCategoryData>? =
            apiInterface?.getVideoCategoryData(params, videoCategoryId)
        dataObserve!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<VideoCategoryData> {
                override fun onSuccess(response: VideoCategoryData) {
                    videosList.value = response.videoCategory.videos
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