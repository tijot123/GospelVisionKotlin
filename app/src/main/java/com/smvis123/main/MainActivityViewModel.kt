package com.smvis123.main

import androidx.lifecycle.MutableLiveData
import com.smvis123.base.BaseViewModel
import com.smvis123.model.*
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel : BaseViewModel() {
    var sliderList: MutableLiveData<MutableList<Slider>> = MutableLiveData()
    var videoCategoryList: MutableLiveData<MutableList<Category>> = MutableLiveData()
    var liveTvData: MutableLiveData<Live> = MutableLiveData()

    fun getSliderList(params: MutableMap<String, String>) {
        if (apiInterface == null) getApiInterfaceImplementation()
        val dataObserve: Single<ImageSlider>? = apiInterface?.getImageSliderList(params)
        dataObserve!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ImageSlider> {
                override fun onSuccess(slider: ImageSlider) {
                    sliderList.value = slider.sliderList
                    isApiSuccess.value = true
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    isApiSuccess.value = false
                }

            })
    }

    fun getVideoCategoryList(params: MutableMap<String, String>) {
        if (apiInterface == null) getApiInterfaceImplementation()
        val dataObserve: Single<VideoCategory>? = apiInterface?.getVideoCategory(params)
        dataObserve!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<VideoCategory> {
                override fun onSuccess(response: VideoCategory) {
                    videoCategoryList.value = response.vidCategoryList
                    isApiSuccess.value = true
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    isApiSuccess.value = false
                }

            })
    }

    fun getLiveTvData(params: MutableMap<String, String>) {
        if (apiInterface == null) getApiInterfaceImplementation()
        val dataObserve: Single<LiveTv>? = apiInterface?.getLiveTv(params)
        dataObserve!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<LiveTv> {
                override fun onSuccess(response: LiveTv) {
                    liveTvData.value = response.liveTvList[0]
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