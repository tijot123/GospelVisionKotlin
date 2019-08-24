package com.smvis123.api

import com.smvis123.model.*
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @POST(API_IMAGE_SLIDER)
    @FormUrlEncoded
    fun getImageSliderList(@FieldMap params: Map<String, String>): Single<ImageSlider>

    @POST(API_VIDEO_CATEGORY)
    @FormUrlEncoded
    fun getVideoCategory(@FieldMap params: Map<String, String>): Single<VideoCategory>

    @POST(API_LIVE_TV)
    @FormUrlEncoded
    fun getLiveTv(@FieldMap params: Map<String, String>): Single<LiveTv>

    @POST(API_VIDEO_CATEGORY_DATA)
    @FormUrlEncoded
    fun getVideoCategoryData(@FieldMap params: Map<String, String>, @Path(API_VIDEO_CATEGORY_ID) videoCategoryId: String): Single<VideoCategoryData>

    @POST(API_SCHEDULE)
    @FormUrlEncoded
    fun scheduleCall(@FieldMap params: Map<String, String>): Single<ScheduleResponse>

    @POST(API_GALLERY_ALBUMS)
    @FormUrlEncoded
    fun getGalleryAlbums(@FieldMap params: Map<String, String>): Single<GalleryAlbumsResponse>

    @POST(API_PREACHERS)
    @FormUrlEncoded
    fun getPastorsList(@FieldMap params: Map<String, String>): Single<PreachersResponse>

    @POST(API_GALLERY_ALBUM_IMAGES)
    @FormUrlEncoded
    fun getGalleryAlbumImages(@Path(API_GALLERY_ALBUM_ID) id: String, @FieldMap params: Map<String, String>): Single<Album>
}