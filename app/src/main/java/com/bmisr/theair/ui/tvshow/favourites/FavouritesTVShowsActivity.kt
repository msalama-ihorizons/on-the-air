package com.bmisr.theair.ui.tvshow.favourites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bmisr.theair.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by Mohamed Salama on 10/12/2020.
 */
@AndroidEntryPoint
class FavouritesTVShowsActivity: AppCompatActivity() {

    companion object {
        fun newIntent(context: Context?): Intent {
            return Intent(context, FavouritesTVShowsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favouites)
        title = "Favourites"

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
