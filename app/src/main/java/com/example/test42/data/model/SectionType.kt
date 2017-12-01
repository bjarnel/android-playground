package com.example.test42.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by bjarne on 01/12/2017.
 */
enum class SectionType {
    @SerializedName("topstory")
    Topstory,
    @SerializedName("latest")
    Latest,
    @SerializedName("mostread")
    Mostread,
    @SerializedName("regional")
    Regional,
    @SerializedName("regular")
    Regular
}