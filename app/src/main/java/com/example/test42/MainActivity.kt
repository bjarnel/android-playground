package com.example.test42

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.internal.NavigationMenuItemView
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem

import com.example.test42.data.model.Section
import com.example.test42.data.remote.ApiUtils
import com.example.test42.data.remote.SectionService
import com.example.test42.fragments.NewsFragment
import com.example.test42.fragments.SportsFragment
import com.example.test42.fragments.WeatherFragment
import com.example.test42.ui.adapters.SectionsViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

import java.util.ArrayList
import java.util.concurrent.CopyOnWriteArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var currentActiveTabId = R.id.action_news

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // https://kotlinlang.org/docs/reference/lambdas.html
        // experimenting with lambdas instead of class with one function in it
        //navigation.setOnNavigationItemSelectedListener { _ -> false }
        navigation.setOnNavigationItemSelectedListener { item -> didTap(item) }
    }

    private fun didTap(item: MenuItem): Boolean {
        // basic "guard"
        //if(currentActiveTabId == item.itemId) { return false }
        //currentActiveTabId = item.itemId

        // https://android.jlelse.eu/a-few-ways-to-implement-a-swift-like-guard-in-kotlin-ffd94027864e
        // check this out!
        currentActiveTabId = (currentActiveTabId != item.itemId)?.let { item.itemId } ?: return false

        // https://developer.android.com/training/basics/fragments/fragment-ui.html

        // in Kotlin we use when instead of switch, and it is more powerful
        // https://kotlinlang.org/docs/reference/control-flow.html
        /*
        when (item.itemId) {
            R.id.action_news -> print("news")
            R.id.action_sports -> print("sports")
            R.id.action_weather -> print("weather")
        }
        */

        // assignment from the "result" of the when expression
        /*
        val newFragment = when (item.itemId) {
            R.id.action_news -> NewsFragment()
            R.id.action_sports -> SportsFragment()
            else -> WeatherFragment()  // R.id.action_weather
        }

        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, newFragment)
            .commit()
*/
        //NOTE: this causes app to crash with view must not be null if NewsFragment has not finished loading articles (onResponse, trying
        // to update adapter(s)
        //NOTE: setOnNavigationItemSelectedListener may not be right, at least it does not actually update the currently active tab..

        // shorter version...

        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, when (item.itemId) {
                    R.id.action_news -> NewsFragment()
                    R.id.action_sports -> SportsFragment()
                    else -> WeatherFragment()
                })
                .commit()

        return false
    }

}
