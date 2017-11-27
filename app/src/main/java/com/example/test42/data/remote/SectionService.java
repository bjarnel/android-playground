package com.example.test42.data.remote;

import com.example.test42.data.model.Section;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by bjarne on 24/11/2017.
 */

public interface SectionService {
    @GET("/{appIdentifier}/sections/v{sectionsVersion}/")
    Call<List<Section>> getSections(@Path("appIdentifier") String appIdentifier,
                                    @Path("sectionsVersion") String sectionsVersion);
}
