package com.example.test42.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by bjarne on 24/11/2017.
 */

data class Section(val title: String,
                   //val key_not_present:String, //-- so apparently a thing like this, get null or empty string value?
                   @SerializedName("identifier") val id: String,
                   @SerializedName("hex_color") val hexColor: String,
                   val type:SectionType = SectionType.Regular)