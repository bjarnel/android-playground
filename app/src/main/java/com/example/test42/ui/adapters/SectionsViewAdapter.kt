package com.example.test42.ui.adapters

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.test42.R
import com.example.test42.data.model.Article
import com.example.test42.data.model.Section
import kotlinx.android.synthetic.main.section_recycler_view_item.view.*

import java.util.ArrayList
import java.util.Dictionary
import java.util.HashMap
import java.util.Hashtable

/**
 * Created by bjarne on 24/11/2017.
 */

class SectionsViewAdapter// constructor, require a list of sections
(private val context: Context, private val sections: List<Section>) : RecyclerView.Adapter<SectionsViewAdapter.ViewHolder>() {
    //TODO: in general we should make our adapters automatically update using observeable collections(?)
    // OR rather EITHER this, or use immutable lists in our adapters to reduce risk
    // ALSO we should create a generic base adapter, possibly..
    private val articleSets: HashMap<String, MutableList<Article>> = HashMap()

    // our viewholder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    // recyclerview asks us to provide a viewholder (these, including the view they hold, will be recycled)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.section_recycler_view_item, parent, false)

        return ViewHolder(itemView)
    }

    fun updateArticleSet(newArticles: List<Article>, section: Section) {
        var articles: MutableList<Article>? = articleSets[section.id]
        if (articles == null) {
            articles = ArrayList()
            articleSets.put(section.id, articles)
        }
        articles.clear()   // remove all
        articles.addAll(newArticles)
        notifyItemChanged(sections.indexOf(section))
    }

    // now the recyclerview is asking us to update a specific view for a particular item
    // that is about to get shown on screen
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (title, id) = sections[position]
        holder.itemView.titleTextView.text = title
        //holder.titleTextView.setText(section.getKey_not_present());

        var articles: List<Article>? = articleSets[id]
        if (articles == null) {
            articles = ArrayList()
            articleSets.put(id, articles)
        }

        if (holder.itemView.articlesRecyclerView.layoutManager == null) {
            val layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            holder.itemView.articlesRecyclerView.layoutManager = layoutManager
            holder.itemView.articlesRecyclerView.itemAnimator = DefaultItemAnimator()

        }

        //TODO: stop parsing around context like this, we should make it accessible via a static
        // application class property (?)
        val adapter = ArticlesViewAdapter(context, articles)
        holder.itemView.articlesRecyclerView.adapter = adapter

    }

    override fun getItemCount(): Int = sections.size
}
