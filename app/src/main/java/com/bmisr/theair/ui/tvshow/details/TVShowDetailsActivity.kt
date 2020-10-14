package com.bmisr.theair.ui.tvshow.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bmisr.theair.R
import com.bmisr.theair.api.response.TVShow
import com.bmisr.theair.model.Status
import com.bumptech.glide.Glide
import com.irozon.sneaker.Sneaker
import com.like.LikeButton
import com.like.OnLikeListener
import com.bmisr.theair.ui.tvshow.details.rating.RateBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_tv_show_details.*


@AndroidEntryPoint
class TVShowDetailsActivity : AppCompatActivity() {

    private val tvShowDetailsViewModel: TVShowDetailsViewModel by viewModels()
    private var tvShow: TVShow? = null

    companion object {
        const val EXTRA_TV_SHOW_ID = "extraTVShowID"

        fun newIntent(context: Context?, tvShowId: Int?): Intent {
            val intent = Intent(context, TVShowDetailsActivity::class.java)
            intent.putExtra(EXTRA_TV_SHOW_ID, tvShowId)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tv_show_details)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        handleActions()

        handleObservers()
    }

    private fun handleActions() {

        btnAddToFav.setOnLikeListener(object : OnLikeListener {
            override fun liked(likeButton: LikeButton?) {
                tvShowDetailsViewModel.addToFavourites(true, tvShow)
            }

            override fun unLiked(likeButton: LikeButton?) {
                tvShowDetailsViewModel.addToFavourites(false, tvShow)
            }

        })

    }

    private fun handleObservers() {
        tvShowDetailsViewModel.tvShowDetailsLiveData?.observe(this, Observer { resources ->

            when (resources.status) {
                Status.SUCCESS -> {

                    tvShow = resources.data

                    tvShow?.let { tvShow ->
                        title = tvShow.name

                        val posterUrl = getString(R.string.tv_show_poster_url, tvShow.backdropPath)
                        Glide.with(this).load(posterUrl)
                            .into(tvShowPoster)
                        tvShowTitle.text = tvShow.name
                        tvShowEpisodeNumber.text = getString(R.string.tv_show_episode_number, tvShow.episodeNumber)
                        tvShowHomePageUrl.text = tvShow.homepage
                        tvShowOverView.text = tvShow.overview
                        tvShowVoteAverage.text = tvShow.voteAverage.toString()
                        tvShowFirstAirDate.text = tvShow.firstAirDate
                        tvShowGenres.text = TextUtils.join(", ", tvShow.genres.map { genre-> genre.name })
                    }


                }
                Status.ERROR -> Sneaker.with(this)
                    .setTitle("Error")
                    .setMessage(resources.message?: "UnKnow Error")
                    .sneakError()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })

        tvShowDetailsViewModel.ratingTVShowLiveData.observe(this, Observer { resources ->
            when (resources.status) {
                Status.SUCCESS ->  Sneaker.with(this)
                    .setTitle("Success")
                    .setMessage("Rating is submitted!")
                    .sneakSuccess()
                Status.ERROR ->  Sneaker.with(this)
                    .setTitle("Error")
                    .setMessage("There is an error, please try again later")
                    .sneakError()
                Status.LOADING -> progressBar.visibility = View.VISIBLE
                Status.COMPLETE -> progressBar.visibility = View.GONE
            }
        })

        tvShowDetailsViewModel.favouriteTVShowLiveDate?.observe(this, Observer {
            btnAddToFav.isLiked = it
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.tv_show_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_rate -> {
                val rateBottomSheetFragment =
                    RateBottomSheetFragment()
                rateBottomSheetFragment.rateClickCallback =
                    object :
                        RateBottomSheetFragment.RateClickCallback {
                    override fun onRateBtnClick(rateValue: Float) {
                        tvShowDetailsViewModel.rateTVShow(rateValue)
                    }

                }

                rateBottomSheetFragment.show(supportFragmentManager, "rateBottomSheetFragment")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}