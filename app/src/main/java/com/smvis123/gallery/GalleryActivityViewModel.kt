package com.smvis123.gallery

import androidx.lifecycle.MutableLiveData
import com.smvis123.base.BaseViewModel
import com.smvis123.model.Album
import com.smvis123.model.GalleryAlbums
import com.smvis123.model.GalleryAlbumsResponse
import com.smvis123.model.GalleryImages
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class GalleryActivityViewModel : BaseViewModel() {
    val galleryAlbumsList: MutableLiveData<MutableList<GalleryAlbums>> = MutableLiveData()
    val galleryAlbumsImagesList: MutableLiveData<MutableList<GalleryImages>> = MutableLiveData()

    fun getGalleryAlbums(params: MutableMap<String, String>) {
        if (apiInterface == null) getApiInterfaceImplementation()
        val dataObserve: Single<GalleryAlbumsResponse>? =
            apiInterface?.getGalleryAlbums(params)
        dataObserve!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<GalleryAlbumsResponse> {
                override fun onSuccess(response: GalleryAlbumsResponse) {
                    galleryAlbumsList.value = response.galleryAlbumsList
                    isApiSuccess.value = true
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    isApiSuccess.value = false
                }

            })
    }

    fun getGalleryAlbumImages(params: MutableMap<String, String>, albumId: String) {
        if (apiInterface == null) getApiInterfaceImplementation()
        val dataObserve: Single<Album>? =
            apiInterface?.getGalleryAlbumImages(albumId, params)
        dataObserve!!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Album> {
                override fun onSuccess(response: Album) {
                    galleryAlbumsImagesList.value = response.photo.imageList
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