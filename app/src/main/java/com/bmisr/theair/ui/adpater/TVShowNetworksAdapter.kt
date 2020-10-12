package com.bmisr.theair.ui.adpater

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bmisr.theair.R
import com.bmisr.theair.api.response.Network
import com.bumptech.glide.Glide
import com.leodroidcoder.genericadapter.BaseViewHolder
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import kotlinx.android.synthetic.main.tv_show_network_item.view.*

class TVShowNetworksAdapter(context: Context?, listener: OnRecyclerItemClickListener) : GenericRecyclerViewAdapter<Network, OnRecyclerItemClickListener, TVShowNetworksAdapter.TVShowNetworkViewHolder>(context, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowNetworkViewHolder {
        return TVShowNetworkViewHolder(inflate(R.layout.tv_show_network_item, parent), listener)
    }

    private var context: Context? = null

    init {
        this.context = context
    }


    inner class TVShowNetworkViewHolder(itemView: View, listener: OnRecyclerItemClickListener?) :
        BaseViewHolder<Network, OnRecyclerItemClickListener>(itemView, listener), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onBind(item: Network) {
            itemView.networkName.text = item.name

            val networkLogoUrl = context?.getString(R.string.tv_show_poster_url, item.logoPath)

            context?.let {
                Glide.with(it).load(networkLogoUrl)
                    .into(itemView.networkLogo)
            }
        }

        override fun onClick(p0: View?) {
            listener?.onItemClick(adapterPosition)
        }
    }
}