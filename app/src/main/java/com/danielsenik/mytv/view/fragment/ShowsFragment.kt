package com.danielsenik.mytv.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.danielsenik.mytv.databinding.FragmentShowsBinding
import com.danielsenik.mytv.model.Show
import com.danielsenik.mytv.service.util.Resource
import com.danielsenik.mytv.view.adapter.ShowRecyclerAdapter
import com.danielsenik.mytv.viewmodel.ShowsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowsFragment : Fragment() {
    private lateinit var binding: FragmentShowsBinding

    private lateinit var showRecyclerAdapter: ShowRecyclerAdapter

    private val showsViewModel: ShowsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowsBinding.inflate(inflater, container, false)
        initView()
        initRecyclerView()
        subscribeUi()
        return binding.root
    }

    private fun initView() {
        binding.refreshFab.setOnClickListener { showsViewModel.fetchShows(true) }

        binding.errorLayout.refreshBt.setOnClickListener { showsViewModel.fetchShows(true) }
    }

    private fun initRecyclerView() {
        showRecyclerAdapter = ShowRecyclerAdapter()
        binding.showRv.adapter = showRecyclerAdapter
    }

    private fun subscribeUi() {
        showsViewModel.getShows().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Ok -> {
                    setShows(it.data)
                    setLoading(false)
                }
                is Resource.Loading -> {
                    setError(false)
                    setLoading(true)
                }
                is Resource.Error -> {
                    setLoading(false)
                    setError(true)
                }
            }
        }

        showsViewModel.fetchShows(false)
    }

    private fun setShows(shows: List<Show>?) {
        showRecyclerAdapter.submitList(shows)
    }

    private fun setLoading(isLoading: Boolean) {
        binding.loadingLayout.root.isVisible = isLoading
    }

    private fun setError(hasError: Boolean) {
        binding.errorLayout.root.isVisible = hasError
    }
}