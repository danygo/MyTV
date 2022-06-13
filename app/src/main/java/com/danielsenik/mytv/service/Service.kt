package com.danielsenik.mytv.service

import com.danielsenik.mytv.model.Show
import retrofit2.Call
import retrofit2.http.GET

interface Service {
    companion object {
        const val BASE_URL = "https://api.tvmaze.com/"
    }

    @GET("shows")
    fun getShows(): Call<List<Show>>
}