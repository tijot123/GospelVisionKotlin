package com.smvis123.api

import com.smvis123.model.ImageSlider
import com.smvis123.model.LiveTv
import com.smvis123.model.VideoCategory
import com.smvis123.model.VideoCategoryData
import io.reactivex.Single
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
}