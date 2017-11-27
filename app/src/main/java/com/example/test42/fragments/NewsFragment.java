package com.example.test42.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test42.R;
import com.example.test42.data.model.Article;
import com.example.test42.data.model.Section;
import com.example.test42.data.remote.ApiUtils;
import com.example.test42.data.remote.ArticleService;
import com.example.test42.data.remote.SectionService;
import com.example.test42.ui.adapters.SectionsViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by bjarne on 25/11/2017.
 */

public class NewsFragment extends Fragment {
    private RecyclerView sectionsRecyclerView;
    private SectionsViewAdapter sectionsViewAdapter;
    private List<Section> sections;
    private Call<List<Section>> sectionsRequest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // https://www.journaldev.com/9266/android-fragment-lifecycle



        View view = inflater.inflate(R.layout.fragment_news, container, false);

        // we have zero sections to  begin with
        sections = new ArrayList<Section>();

        sectionsRecyclerView = view.findViewById(R.id.sectionsRecyclerView);
        sectionsViewAdapter = new SectionsViewAdapter(getActivity().getApplicationContext(), sections);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        sectionsRecyclerView.setLayoutManager(layoutManager);
        sectionsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        sectionsRecyclerView.setAdapter(sectionsViewAdapter);

        loadSections();

        return view;
    }

    private void loadSections() {
        // abort current (if any) request
        if(sectionsRequest != null && !sectionsRequest.isExecuted()) {
            sectionsRequest.cancel();
            sectionsRequest = null;
        }

        SectionService service = ApiUtils.getSectionService();
        sectionsRequest = service.getSections("nyhedscenter","6");
        sectionsRequest.enqueue(new Callback<List<Section>>() {
            @Override
            public void onResponse(Call<List<Section>> call, Response<List<Section>> response) {
                if(response.isSuccessful()) {
                    sections.clear();
                    sections.addAll(response.body());
                    sectionsViewAdapter.notifyDataSetChanged();
                    loadArticles();

                } else {
                    //TODO: handle
                }
            }

            @Override
            public void onFailure(Call<List<Section>> call, Throwable t) {
                //TODO: handle
            }
        });

    }

    private void loadArticles() {
        ArticleService service = ApiUtils.getArticleService();

        // we will simply, because we're rather stupid at this point, load articles for ALL sections
        for(final Section section:sections) {
            Call<List<Article>> request = service.getArticles(
                    "6",
                    section.getId(),
                    "nyhedscenter",
                    "6");
            request.enqueue(new Callback<List<Article>>() {
                @Override
                public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                    if(response.isSuccessful()) {
                        sectionsViewAdapter.updateArticleSet(response.body(), section);

                    } else {
                        //TODO: handle
                    }
                }

                @Override
                public void onFailure(Call<List<Article>> call, Throwable t) {
                    //TODO: handle
                }
            });

        }
    }
}
