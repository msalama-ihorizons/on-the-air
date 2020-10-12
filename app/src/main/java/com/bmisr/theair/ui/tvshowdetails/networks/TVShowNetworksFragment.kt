package com.bmisr.theair.ui.tvshowdetails.networks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bmisr.theair.R
import com.bmisr.theair.model.Status
import com.bmisr.theair.ui.adpater.TVShowNetworksAdapter
import com.bmisr.theair.ui.tvshowdetails.TVShowDetailsViewModel
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import kotlinx.android.synthetic.main.fragment_tv_show_networks.*

/**
 * Created by Mohamed Salama on 10/12/2020.
 */
class TVShowNetworksFragment : Fragment() {

    private lateinit var tvShowNetworksAdapter: TVShowNetworksAdapter
    private val tvShowDetailsViewModel: TVShowDetailsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowNetworksAdapter = TVShowNetworksAdapter(
            context,
            OnRecyclerItemClickListener {

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show_networks, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvShowNetworksRV.adapter = tvShowNetworksAdapter

        tvShowDetailsViewModel.tvShowDetailsLiveData?.observe(viewLifecycleOwner, Observer { resource ->

            when(resource.status) {
                Status.SUCCESS -> tvShowNetworksAdapter.items = resource.data?.networks
            }
        })
    }
}