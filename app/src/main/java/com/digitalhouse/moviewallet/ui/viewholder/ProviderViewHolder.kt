package com.digitalhouse.moviewallet.ui.viewholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R

class ProviderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val ivProvider: ImageView? by lazy { view.findViewById<ImageView>(R.id.iv_provider_details) }
}