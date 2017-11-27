package com.example.test42.fragments

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.test42.R
import com.example.test42.data.model.Article
import com.example.test42.data.model.Section
import com.example.test42.data.remote.ApiUtils
import com.example.test42.data.remote.ArticleService
import com.example.test42.data.remote.SectionService
import com.example.test42.ui.adapters.SectionsViewAdapter

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bjarne on 25/11/2017.
 */

class NewsFragment : Fragment() {
    private var sectionsRecyclerView: RecyclerView? = null
    private var sectionsViewAdapter: SectionsViewAdapter? = null
    private var sections: MutableList<Section>? = null
    private var sectionsRequest: Call<List<Section>>? = null

    // the Kotlin converter made savedInstanceState a non-null param, but actually it is nullable
    // interestingly the crash provided a very clear messsage that lead me to replace it with Bundle?
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // https://www.journaldev.com/9266/android-fragment-lifecycle


        val view = inflater.inflate(R.layout.fragment_news, container, false)

        // we have zero sections to  begin with
        sections = ArrayList()

        sectionsRecyclerView = view.findViewById(R.id.sectionsRecyclerView)
        sectionsViewAdapter = SectionsViewAdapter(activity.applicationContext, sections)

        val layoutManager = LinearLayoutManager(activity.applicationContext)
        sectionsRecyclerView!!.layoutManager = layoutManager
        sectionsRecyclerView!!.itemAnimator = DefaultItemAnimator()
        sectionsRecyclerView!!.adapter = sectionsViewAdapter

        loadSections()

        return view
    }

    private fun loadSections() {
        // abort current (if any) request
        if (sectionsRequest != null && !sectionsRequest!!.isExecuted) {
            sectionsRequest!!.cancel()
            sectionsRequest = null
        }

        val service = ApiUtils.sectionService
        sectionsRequest = service.getSections("nyhedscenter", "6")
        sectionsRequest!!.enqueue(object : Callback<List<Section>> {
            override fun onResponse(call: Call<List<Section>>, response: Response<List<Section>>) {
                if (response.isSuccessful) {
                    sections!!.clear()
                    sections!!.addAll(response.body())
                    sectionsViewAdapter!!.notifyDataSetChanged()
                    loadArticles()

                } else {
                    //TODO: handle
                }
            }

            override fun onFailure(call: Call<List<Section>>, t: Throwable) {
                //TODO: handle
            }
        })

    }

    private fun loadArticles() {
        val service = ApiUtils.articleService

        // we will simply, because we're rather stupid at this point, load articles for ALL sections
        for (section in sections!!) {
            val request = service.getArticles(
                    "6",
                    section.id,
                    "nyhedscenter",
                    "6")
            request.enqueue(object : Callback<List<Article>> {
                override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
                    if (response.isSuccessful) {
                        sectionsViewAdapter!!.updateArticleSet(response.body(), section)

                    } else {
                        //TODO: handle
                    }
                }

                override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                    //TODO: handle
                }
            })

        }
    }
}
