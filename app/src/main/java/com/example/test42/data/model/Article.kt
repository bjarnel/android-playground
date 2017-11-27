package com.example.test42.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by bjarne on 25/11/2017.
 */

data class Article(val title: String,   // wonder what happens if title in the json is null?
                   val label: String? = null,
                   @SerializedName("small_teaser_image_square") val imageSmallSquareUrl: String? = null,
                   @SerializedName("small_teaser_image") val imageSmallUrl: String? = null,
                   @SerializedName("teaser_image") val imageUrl: String? = null)

