package com.example.test42.data.remote

import com.example.test42.data.model.Article

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by bjarne on 25/11/2017.
 */

interface ArticleService {
    // https://app-backend.api.tv2.dk/articles/v6/?section_identifier=1&app_identifier=nyhedscenter&sections_version=6&count=100

    @GET("/articles/v{articlesVersion}/")
    fun getArticles(@Path("articlesVersion") articlesVersion: String,
                    @Query("section_identifier") sectionId: String,
                    @Query("app_identifier") appIdentifier: String,
                    @Query("sections_version") sectionsVersion: String): Call<List<Article>>
}
