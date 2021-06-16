package com.digitalhouse.moviewallet.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.squareup.picasso.Picasso

class ProviderDetailsAdapter(private val providers: MutableList<String>) :
    RecyclerView.Adapter<ProviderDetailsAdapter.ProviderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProviderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_details_provider, parent, false)
        )

    override fun onBindViewHolder(holder: ProviderViewHolder, position: Int) {
        val provider = providers[position]
        val configuration = SingletonConfiguration.config

        val imageUrl =
            "${configuration?.images?.secure_base_url}${configuration?.images?.logo_sizes?.get(1)}${provider}"
        val logo = holder.ivProvider
        Picasso.get().load(imageUrl).into(
            logo
        )
    }

    override fun getItemCount() = providers.size

    inner class ProviderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivProvider: ImageView? by lazy { view.findViewById<ImageView>(R.id.iv_provider_details) }
    }
}