package com.example.test42

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.example.test42.data.model.Section
import com.example.test42.data.remote.ApiUtils
import com.example.test42.data.remote.SectionService
import com.example.test42.ui.adapters.SectionsViewAdapter

import java.util.ArrayList
import java.util.concurrent.CopyOnWriteArrayList

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}
