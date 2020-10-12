package com.bmisr.theair.ui.favourites

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bmisr.theair.model.Status
import com.irozon.sneaker.Sneaker
import com.bmisr.theair.ui.tvshowslist.TVShowsListFragment
import com.bmisr.theair.ui.tvshowslist.TVShowsViewModel
import kotlinx.android.synthetic.main.fragment_tv_shows_list.*

/**
 * Created by Mohamed Salama on 9/7/2020.
 */

class FavouritesTVShowsFragment: TVShowsListFragment() {

    private val tvShowsViewModel: TVShowsViewModel by activityViewModels()

    override fun handleObservers() {
        tvShowsViewModel.favTvShowsListLiveData.observe(viewLifecycleOwner, Observer { resources ->
            when (resources.status) {
                Status.SUCCESS -> tvShowsAdapter.items = resources.data
                Status.ERROR ->  Sneaker.with(this)
                    .setTitle("Error")
                    .setMessage(resources.message?: "UnKnow Error")
                    .sneakError()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }
}