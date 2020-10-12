package com.bmisr.theair.ui.tvshowslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bmisr.theair.R
import com.bmisr.theair.model.Status
import com.irozon.sneaker.Sneaker
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.bmisr.theair.ui.adpater.TVShowsAdapter
import com.bmisr.theair.ui.tvshowdetails.TVShowDetailsActivity
import kotlinx.android.synthetic.main.fragment_tv_shows_list.*

/**
 * Created by Mohamed Salama on 10/12/2020.
 */
open class TVShowsListFragment: Fragment()  {

    private val tvShowsViewModel: TVShowsViewModel by activityViewModels()
    protected lateinit var tvShowsAdapter: TVShowsAdapter

    companion object {
        private const val NUMBER_OF_COL = 3
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_shows_list, container, false)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowsAdapter = TVShowsAdapter(
            context,
            OnRecyclerItemClickListener { pos ->

                startActivity(
                    TVShowDetailsActivity.newIntent(
                        requireActivity(),
                        tvShowsAdapter.items[pos].id
                    )
                )

            })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvShowsRV.adapter =  tvShowsAdapter
        tvShowsRV.layoutManager = GridLayoutManager(context, NUMBER_OF_COL)
        tvShowsRV.setHasFixedSize(true)

        handleObservers()
    }

    open fun handleObservers() {
        tvShowsViewModel.tvShowsListLiveData.observe(viewLifecycleOwner, Observer { resources ->
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