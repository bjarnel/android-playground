package com.example.test42;

import android.util.Log;

import com.example.test42.data.model.Article;
import com.example.test42.data.remote.ApiUtils;
import com.example.test42.data.remote.ArticleService;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by bjarne on 25/11/2017.
 */

public class ArticleServiceUnitTest {
    List<Article> articles = null;

    @Test
    public void canFetchArticles() throws InterruptedException {
        articles = null;

        final CountDownLatch signal = new CountDownLatch(1);

        ArticleService service = ApiUtils.INSTANCE.getArticleService();
        Call<List<Article>> request = service.getArticles("6",
                                                          "2",  // seneste nyt
                                                          "nyhedscenter",
                                                          "6");
        request.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                if(response.isSuccessful()) {
                    articles = response.body();

                } else {
                    Log.d("INFO", "" + response.code());
                }
                signal.countDown();
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Log.d("INFO", "Request could not be completed");
                signal.countDown();
            }
        });


        signal.await(30, TimeUnit.SECONDS);

        assertNotNull(articles);
        assertFalse(articles.isEmpty());
    }
}
