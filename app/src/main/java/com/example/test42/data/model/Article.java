package com.example.test42.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bjarne on 25/11/2017.
 */

public class Article {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("label")
    @Expose
    private String label;

    @SerializedName("identifier")
    @Expose
    private String id;

    @SerializedName("small_teaser_image_square")
    @Expose
    private String imageSmallSquareUrl;

    @SerializedName("small_teaser_image")
    @Expose
    private String imageSmallUrl;

    @SerializedName("teaser_image")
    @Expose
    private String imageUrl;

    public String getTitle() { return title; }

    public String getImageUrl() { return imageUrl; }

}
