package com.bmisr.theair.ui.tvshow.details.cast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.bmisr.theair.R
import com.bmisr.theair.model.Status
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import com.bmisr.theair.ui.tvshow.adapter.TVShowCastAdapter
import com.bmisr.theair.ui.tvshow.details.TVShowDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_show_cast.*

/**
 * Created by Mohamed Salama on 10/12/2020.
 */

@AndroidEntryPoint
class TVShowCastFragment: Fragment() {

    private lateinit var tvShowCastAdapter: TVShowCastAdapter
    private val tvShowDetailsViewModel: TVShowDetailsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvShowCastAdapter = TVShowCastAdapter(
            context,
            OnRecyclerItemClickListener {

            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show_cast, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvShowCastRV.adapter = tvShowCastAdapter

        tvShowDetailsViewModel.tvShowCastLiveData?.observe(viewLifecycleOwner, Observer { resource ->

            when(resource.status) {
                Status.SUCCESS -> tvShowCastAdapter.items = resource.data
                Status.LOADING ->  progressBar.visibility = View.VISIBLE
                Status.COMPLETE ->  progressBar.visibility = View.GONE
            }
        })
    }
}