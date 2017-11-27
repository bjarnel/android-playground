package com.example.test42.ui.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test42.R;
import com.example.test42.data.model.Article;
import com.example.test42.data.model.Section;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by bjarne on 24/11/2017.
 */

public class SectionsViewAdapter extends RecyclerView.Adapter<SectionsViewAdapter.ViewHolder> {
    private Context context;
    private List<Section> sections;
    private HashMap<String, List<Article>> articleSets;

    // our viewholder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public RecyclerView articlesRecyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            articlesRecyclerView = itemView.findViewById(R.id.articlesRecyclerView);

        }
    }

    // constructor, require a list of sections
    public SectionsViewAdapter(Context context, List<Section> sections) {
        this.context = context;
        this.sections = sections;
        // we do not need to provide the generic type information here, because it can be
        // deferred from the type of articleSets..
        articleSets = new HashMap<>();

    }

    // recyclerview asks us to provide a viewholder (these, including the view they hold, will be recycled)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.section_recycler_view_item, parent, false);

        return new ViewHolder(itemView);
    }

    public void updateArticleSet(List<Article> newArticles, Section section) {
        List<Article> articles = articleSets.get(section.getId());
        if(articles == null) {
            articles = new ArrayList<>();
            articleSets.put(section.getId(), articles);
        }
        articles.clear();   // remove all
        articles.addAll(newArticles);
        notifyItemChanged(sections.indexOf(section));
    }

    // now the recyclerview is asking us to update a specific view for a particular item
    // that is about to get shown on screen
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Section section = sections.get(position);
        holder.titleTextView.setText(section.getTitle());
        //holder.typeTextView.setText(section.getType().name());

        List<Article> articles = articleSets.get(section.getId());
        if(articles == null) {
            articles = new ArrayList<>();
            articleSets.put(section.getId(), articles);
        }

        if(holder.articlesRecyclerView.getLayoutManager() == null) {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.articlesRecyclerView.setLayoutManager(layoutManager);
            holder.articlesRecyclerView.setItemAnimator(new DefaultItemAnimator());

        }

        ArticlesViewAdapter adapter = new ArticlesViewAdapter(context, articles);
        holder.articlesRecyclerView.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return sections.size();
    }
}
