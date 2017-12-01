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
import kotlinx.android.synthetic.main.fragment_news.view.*
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

import java.util.ArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by bjarne on 25/11/2017.
 */

class NewsFragment : Fragment() {
    // lateinit is similar to implicit unwrapped in Swift
    private var sections: MutableList<Section> = ArrayList()
    private var sectionsRequest: Call<List<Section>>? = null
    //https://stackoverflow.com/questions/33278869/how-do-i-initialize-kotlins-mutablelist-to-empty-mutablelist
    private val articleRequests = mutableListOf<Call<List<Article>>>()

    // the Kotlin converter made savedInstanceState a non-null param, but actually it is nullable
    // interestingly the crash provided a very clear messsage that lead me to replace it with Bundle?
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // https://www.journaldev.com/9266/android-fragment-lifecycle
        // http://abhiandroid.com/ui/fragment-lifecycle-example-android-studio.html


        val view = inflater.inflate(R.layout.fragment_news, container, false)

        // No need for this findViewById anymore with Kotlin:-)
        //sectionsRecyclerView = view.findViewById(R.id.sectionsRecyclerView)

        val layoutManager = LinearLayoutManager(activity.applicationContext)
        // we can reference with child with by its id directly with Kotlin
        view.sectionsRecyclerView.layoutManager = layoutManager
        view.sectionsRecyclerView.itemAnimator = DefaultItemAnimator()
        view.sectionsRecyclerView.adapter = SectionsViewAdapter(activity.applicationContext, sections)

        loadSections()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()

        abortRequests()
    }

    private fun abortRequests() {
        //https://futurestud.io/tutorials/retrofit-2-cancel-requests
        sectionsRequest?.cancel()
        sectionsRequest = null
        for(request in articleRequests) {
            request.cancel()
        }
        articleRequests.clear()
    }

    // https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.experimental/index.html
    // https://stackoverflow.com/questions/43132080/kotlin-coroutines-the-right-way-in-android
    // https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md
    // while this coroutine implementation is AWESOME! it provides no real benefits for our needs
    // here. Simply put: we need a request/job that we can cancel later. Might as well use the default callback
    // API from Retrofit2
    /*
    private fun loadSections() = launch(UI) {
        try {
            val newSections: List<Section> = run(CommonPool) {
                ApiUtils.sectionService.getSections("nyhedscenter", "6").execute().body()
            }
            sections.clear()
            sections.addAll(newSections)
            view.sectionsRecyclerView.adapter.notifyDataSetChanged()

            loadArticles()
        } finally {
        }
    }
    */

    private fun loadSections() {
        abortRequests()

        val request = ApiUtils.sectionService
                              .getSections("nyhedscenter", "6")
        request.enqueue(object : Callback<List<Section>> {
            override fun onResponse(call: Call<List<Section>>, response: Response<List<Section>>) {
                if (response.isSuccessful) {
                    sections.clear()
                    sections.addAll(response.body())
                    view.sectionsRecyclerView.adapter.notifyDataSetChanged()

                    loadArticles()

                } else {
                    //TODO: handle
                }
            }

            override fun onFailure(call: Call<List<Section>>, t: Throwable) {
                /*
                https://futurestud.io/tutorials/retrofit-2-cancel-requests
                If you cancel the request, Retrofit will classify this as a failed request and thus call
                 the onFailure() callback in your request declaration. This callback is also used when
                 there is no Internet connection or something went wrong on the network level.
                */
                // so if !all.isCancelled then it was a network error..
            }
        })

        sectionsRequest = request

    }

    private fun loadArticles() {
        val service = ApiUtils.articleService

        // we will simply, because we're rather stupid at this point, load articles for ALL sections
        for (section in sections) {
            val request = service.getArticles(
                    "6",
                    section.id,
                    "nyhedscenter",
                    "6")
            request.enqueue(object : Callback<List<Article>> {
                override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
                    if (response.isSuccessful) {
                        // haha this is basically Swift code..
                        // this crashes if the fragment has been destroyed(!)
                        (view.sectionsRecyclerView.adapter as? SectionsViewAdapter)?.updateArticleSet(response.body(), section)

                    } else {
                        //TODO: handle
                    }
                }

                override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                    /*
                https://futurestud.io/tutorials/retrofit-2-cancel-requests
                If you cancel the request, Retrofit will classify this as a failed request and thus call
                 the onFailure() callback in your request declaration. This callback is also used when
                 there is no Internet connection or something went wrong on the network level.
                */
                }
            })
            articleRequests.add(request)

        }
    }
}
