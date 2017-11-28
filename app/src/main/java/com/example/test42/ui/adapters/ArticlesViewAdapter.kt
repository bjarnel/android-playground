package com.example.test42.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.bumptech.glide.Glide
import com.example.test42.R
import com.example.test42.data.model.Article
import kotlinx.android.synthetic.main.article_recycler_view_item.view.*

/**
 * Created by bjarne on 26/11/2017.
 */

class ArticlesViewAdapter(private val context: Context,
                          private val articles: List<Article>) : RecyclerView.Adapter<ArticlesViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.article_recycler_view_item, parent, false)

        return ArticlesViewAdapter.ViewHolder(itemView)
    }

    // haha on statement function in Kotlin
    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.update(articles[position], context)
    /*
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // in Kotlin we dont need to use get(Int) for List<> :-)
        holder.update(articles[position], context)
    }
    */

    // kotlin function with only one statement syntax..:-)
    // infers return type, remaining compatible with the (java) function we're overriding
    override fun getItemCount() = articles.size
    /*override fun getItemCount(): Int {
        return articles.size
    }*/

    // RecyclerView.ViewHolder(itemView) indicates super constructor call, right?
    // https://stackoverflow.com/questions/43012903/trying-to-create-a-simple-recyclerview-in-kotlin-but-the-adapter-is-not-applyin
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun update(article:Article, context:Context) {
            itemView.articleTitleTextView.text = article.title
            // remove prev image displayed, if any
            itemView.articleImageView.setImageDrawable(null)

            // load image (async)
            if (article.imageUrl != null) {
                // https://github.com/bumptech/glide
                // dunno why the doc says "GlideApp"
                Glide.with(context)
                        .load(article.imageUrl)
                        .into(itemView.articleImageView)
            }
        }
    }
}
