package com.example.test42.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test42.R;
import com.example.test42.data.model.Article;

import java.util.List;

/**
 * Created by bjarne on 26/11/2017.
 */

public class ArticlesViewAdapter extends RecyclerView.Adapter<ArticlesViewAdapter.ViewHolder> {
    private Context context;
    private List<Article> articles;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_recycler_view_item, parent, false);

        return new ArticlesViewAdapter.ViewHolder(itemView);
    }

    public ArticlesViewAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.articleTitleTextView.setText(article.getTitle());
        holder.articleImageView.setImageDrawable(null);

        if(article.getImageUrl() != null) {

            // https://github.com/bumptech/glide
            // dunno why the doc says "GlideApp"
            Glide.with(context)
                    .load(article.getImageUrl())
                    .into(holder.articleImageView);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView articleTitleTextView;
        public ImageView articleImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            articleTitleTextView = itemView.findViewById(R.id.articleTitleTextView);
            articleImageView = itemView.findViewById(R.id.articleImageView);
        }
    }
}
