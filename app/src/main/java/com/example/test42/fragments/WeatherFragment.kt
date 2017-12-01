package com.example.test42.fragments

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.test42.R

/**
 * Created by bjarne on 01/12/2017.
 */

class WeatherFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // https://www.journaldev.com/9266/android-fragment-lifecycle


        val view = inflater.inflate(R.layout.fragment_weather, container, false)
        return view
    }
}