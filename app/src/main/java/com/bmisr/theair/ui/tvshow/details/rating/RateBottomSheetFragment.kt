package com.bmisr.theair.ui.tvshow.details.rating

import android.os.Bundle
import android.view.InflateException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bmisr.theair.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_rate_bottom_sheet.*

/**
 * Created by Mohamed Salama on 10/12/2020.
 */

class RateBottomSheetFragment: BottomSheetDialogFragment() {

    var rateClickCallback: RateClickCallback? = null

    var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (root != null) {
            val parent = root?.parent as ViewGroup
            parent.removeView(root)
        }
        try {
            root = inflater.inflate(R.layout.fragment_rate_bottom_sheet, container, false)
        } catch (e: InflateException) {
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnRate.setOnClickListener {
            rateClickCallback?.onRateBtnClick(ratingBar.rating)
            dismiss()
        }
    }


    interface RateClickCallback {
        fun onRateBtnClick(rateValue: Float)
    }
}