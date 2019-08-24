package com.smvis123.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Preachers(
    var id: String,
    var name: String,
    var designation: String,
    var church: String,
    var information: String,
    var service: String,
    var description: String,
    var thumbnail: String
) : Serializable

data class PreachersResponse(
    @SerializedName("pastor list")
    var pastorList: MutableList<Preachers>
)