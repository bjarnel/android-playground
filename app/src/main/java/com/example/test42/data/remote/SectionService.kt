package com.example.test42.data.remote

import com.example.test42.data.model.Section

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by bjarne on 24/11/2017.
 */

interface SectionService {
    @GET("/{appIdentifier}/sections/v{sectionsVersion}/")
    fun getSections(@Path("appIdentifier") appIdentifier: String,
                    @Path("sectionsVersion") sectionsVersion: String): Call<List<Section>>
}
