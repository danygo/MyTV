package com.danielsenik.mytv.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.danielsenik.mytv.model.Show
import com.danielsenik.mytv.repository.ShowRepository
import com.danielsenik.mytv.service.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    repository: ShowRepository
) : ViewModel() {
    private val shouldFetch = MutableLiveData<Boolean>()
    private val shows: LiveData<Resource<List<Show>>>

    init {
        shows = Transformations.switchMap(shouldFetch) { repository.getShows(it) }
    }

    fun getShows(): LiveData<Resource<List<Show>>> {
        return shows;
    }

    fun fetchShows(shouldFetch: Boolean) {
        this.shouldFetch.value = shouldFetch
    }
}