package com.example.test42.data.remote

import dagger.Module
import dagger.Provides

/**
 * Created by bjarne on 24/11/2017.
 */

@Module
object ApiUtils {
    // this API is for our ( TV 2 Danmark A/S ) news app. This content is copyright TV 2 Danmark A/S
    // you should NOT use this except for experimentation purposes - Also it is in Danish :-)
    // Might switch to some other data source in the future
    private val BASE_URL = "https://app-backend.api.tv2.dk/"

    val sectionService: SectionService
        @Provides
        get() = RetrofitClient.getClient(BASE_URL).create(SectionService::class.java)

    val articleService: ArticleService
        @Provides
        get() = RetrofitClient.getClient(BASE_URL).create(ArticleService::class.java)
}
