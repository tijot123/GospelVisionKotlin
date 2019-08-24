package com.smvis123.model

import com.google.gson.annotations.SerializedName

data class Schedule(
    var id: String,
    var title: String,
    var time: String,
    var date: String,
    @SerializedName("user_id")
    var userId: String,
    var day: String,
    var month: String,
    @SerializedName("image_path")
    var imagePath: String,
    @SerializedName("sub_id")
    var subId: String
)

data class ScheduleResponse(
    @SerializedName("mon")
    var monList: List<Schedule>,
    @SerializedName("tue")
    var tueList: List<Schedule>,
    @SerializedName("wed")
    var wedList: List<Schedule>,
    @SerializedName("thu")
    var thuList: List<Schedule>,
    @SerializedName("fri")
    var friList: List<Schedule>,
    @SerializedName("sat")
    var satList: List<Schedule>,
    @SerializedName("sun")
    var sunList: List<Schedule>
)