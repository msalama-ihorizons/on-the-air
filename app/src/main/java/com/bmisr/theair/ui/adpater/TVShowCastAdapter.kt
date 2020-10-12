package com.bmisr.theair.ui.adpater

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.bmisr.theair.R
import com.bmisr.theair.api.response.Cast
import com.bumptech.glide.Glide
import com.leodroidcoder.genericadapter.BaseViewHolder
import com.leodroidcoder.genericadapter.GenericRecyclerViewAdapter
import com.leodroidcoder.genericadapter.OnRecyclerItemClickListener
import kotlinx.android.synthetic.main.tv_show_cast_item.view.*

/**
 * Created by Mohamed Salama on 10/12/2020.
 */
class TVShowCastAdapter(context: Context?, listener: OnRecyclerItemClickListener) : GenericRecyclerViewAdapter<Cast, OnRecyclerItemClickListener, TVShowCastAdapter.TVSHowCastViewHolder>(context, listener) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVSHowCastViewHolder {
        return TVSHowCastViewHolder(inflate(R.layout.tv_show_cast_item, parent), listener)
    }

    private var context: Context? = null

    init {
        this.context = context
    }


    inner class TVSHowCastViewHolder(itemView: View, listener: OnRecyclerItemClickListener?) :
        BaseViewHolder<Cast, OnRecyclerItemClickListener>(itemView, listener), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onBind(item: Cast) {

            itemView.castCharacter.text = item.character
            itemView.castName.text = item.name

            val profileUrl = context?.getString(R.string.tv_show_poster_url, item.profilePath)

            context?.let {
                Glide.with(it).load(profileUrl)
                    .into(itemView.castProfile)
            }
        }

        override fun onClick(p0: View?) {
            listener?.onItemClick(adapterPosition)
        }
    }
}