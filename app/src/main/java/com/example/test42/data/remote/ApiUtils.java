package com.example.test42.data.remote;

/**
 * Created by bjarne on 24/11/2017.
 */

public class ApiUtils {
    // this API is for our ( TV 2 Danmark A/S ) news app. This content is copyright TV 2 Danmark A/S
    // you should NOT use this except for experimentation purposes - Also it is in Danish :-)
    // I might change to some other data source in the future
    public static final String BASE_URL = "https://app-backend.api.tv2.dk/";

    public static SectionService getSectionService() {
        return RetrofitClient.getClient(BASE_URL).create(SectionService.class);
    }

    public static ArticleService getArticleService() {
        return RetrofitClient.getClient(BASE_URL).create(ArticleService.class);
    }
}
