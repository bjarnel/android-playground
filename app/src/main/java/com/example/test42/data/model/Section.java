package com.example.test42.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by bjarne on 24/11/2017.
 */

public class Section {
    public enum Type {
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

    @SerializedName("title")
    @Expose
    private String title;
    //NOTE: GSon is actually not that good, it does not support making a field as REQUIRED!
    // so title might actually be null and parsing will NOT fail...

    @SerializedName("identifier")
    @Expose
    private String id;

    @SerializedName("hex_color")
    @Expose
    private String hexColor;
    //TODO: parse this (fx. "d11f1f") directly into a Color value (Color.parse), possibly look at this:
    // https://stackoverflow.com/questions/11271375/gson-custom-seralizer-for-one-variable-of-many-in-an-object-using-typeadapter
    // or an entirely custom adapter:
    // https://stackoverflow.com/questions/40236412/gson-custom-deserializer-just-on-field

    @SerializedName("type")
    @Expose
    private Type type = Type.Regular;

    public String getTitle() { return title; }

    public String getId() { return id; }

    public String getHexColor() { return hexColor; }

    public Type getType() { return type; }

    @Override
    public boolean equals(Object o) {
        // if same object (in memory)
        if(this == o)
            return true;
        if(!(o instanceof Section))
            return false;
        return ((Section)o).getId() == getId();
    }
}
