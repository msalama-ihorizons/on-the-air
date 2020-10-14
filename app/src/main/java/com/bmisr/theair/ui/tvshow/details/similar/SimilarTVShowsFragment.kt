package com.bmisr.theair.ui.tvshow.details.similar

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bmisr.theair.model.Status
import com.bmisr.theair.ui.tvshow.details.TVShowDetailsViewModel
import com.bmisr.theair.ui.tvshow.list.TVShowsListFragment
import kotlinx.android.synthetic.main.fragment_tv_shows_list.*

/**
 * Created by Mohamed Salama on 10/12/2020.
 */

class SimilarTVShowsFragment: TVShowsListFragment()  {

    private val tvShowDetailsViewModel: TVShowDetailsViewModel by activityViewModels()

    override fun handleObservers() {
        tvShowDetailsViewModel.similarTVShowsLiveData?.observe(viewLifecycleOwner, Observer { resource->
            when(resource.status) {
                Status.SUCCESS -> tvShowsAdapter.items = resource.data
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })
    }
}