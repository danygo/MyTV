package com.danielsenik.mytv.repository

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.danielsenik.mytv.data.Database
import com.danielsenik.mytv.model.Show
import com.danielsenik.mytv.service.Service
import com.danielsenik.mytv.service.util.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ShowRepository @Inject constructor(
    private val service: Service,
    private val database: Database
) {
    private var TAG = ShowRepository::class.java.name

    fun getShows(shouldFetch: Boolean): LiveData<Resource<List<Show>>> {
        val data = MediatorLiveData<Resource<List<Show>>>()
        data.value = Resource.Loading()

        val dbShows = retrieveShows()

        data.addSource(dbShows) {
            data.removeSource(dbShows)

            if (it.isEmpty() || shouldFetch) {
                Log.e(TAG, "GET NEW")
                data.addSource(fetchShows()) { resource ->
                    when (resource) {
                        is Resource.Ok -> {
                            Log.e(TAG, "OK")
                            deleteShows()
                            insertShows(resource.data!!)
                            data.addSource(retrieveShows()) { shows ->
                                data.value = Resource.Ok(shows)
                            }
                        }
                        is Resource.Loading -> {
                            Log.e(TAG, "LOADING")
                        }
                        is Resource.Error -> {
                            Log.e(TAG, "ERROR")
                            data.addSource(dbShows) { shows ->
                                if (shows.isEmpty()) {
                                    data.value = Resource.Error(shows, resource.error!!)
                                } else {
                                    data.value = Resource.Ok(shows)
                                }
                            }
                        }
                    }
                }
            } else {
                Log.e(TAG, "GET SAVED")
                data.addSource(dbShows) { shows ->
                    data.value = Resource.Ok(shows)
                }
            }
        }

        return data
    }

    private fun fetchShows(): LiveData<Resource<List<Show>>> {
        val data = MutableLiveData<Resource<List<Show>>>()

        service.getShows().enqueue(object : Callback<List<Show>> {
            override fun onResponse(call: Call<List<Show>>, response: Response<List<Show>>) {
                if (response.isSuccessful) {
                    data.value = Resource.Ok(response.body())
                } else {
                    data.value = Resource.Error(response.body(), Throwable())
                }
            }

            override fun onFailure(call: Call<List<Show>>, t: Throwable) {
                data.value = Resource.Error(null, t)
            }
        })

        return data
    }

    private fun insertShows(shows: List<Show>) {
        AsyncTask.execute { database.showDao().insertShows(shows) }
    }

    private fun retrieveShows(): LiveData<List<Show>> {
        return database.showDao().retrieveShows()
    }

    private fun deleteShows() {
        AsyncTask.execute { database.showDao().deleteShows() }
    }
}