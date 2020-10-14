package com.bmisr.theair.ui.tvshow.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bmisr.theair.R
import com.bmisr.theair.api.response.TVShow
import com.bumptech.glide.Glide
import com.leodroidcoder.genericadapter.BaseViewHolder
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import kotlinx.android.synthetic.main.tv_show_item.view.*

class TVShowsAdapter(context: Context?, listener: OnRecyclerItemClickListener) : GenericRecyclerViewAdapter<TVShow, OnRecyclerItemClickListener, TVShowsAdapter.TVShowViewHolder>(context, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        return TVShowViewHolder(inflate(R.layout.tv_show_item, parent), listener)
    }

    private var context: Context? = null

    init {
        this.context = context
    }


    inner class TVShowViewHolder(itemView: View, listener: OnRecyclerItemClickListener?) :
        BaseViewHolder<TVShow, OnRecyclerItemClickListener>(itemView, listener), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onBind(item: TVShow) {
            itemView.tvShowTitle.text = item.name
            itemView.tvShowFirstAirDate.text = item.firstAirDate
            itemView.tvShowVoteAverage.text = item.voteAverage.toString()

            val posterUrl = context?.getString(R.string.tv_show_poster_url, item.posterPath)

            context?.let {
                Glide.with(it).load(posterUrl)
                    .into(itemView.tvShowPoster)
            }
        }

        override fun onClick(p0: View?) {
            listener?.onItemClick(adapterPosition)
        }
    }
}