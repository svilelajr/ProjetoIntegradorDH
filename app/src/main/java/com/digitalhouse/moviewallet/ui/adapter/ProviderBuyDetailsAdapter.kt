package com.digitalhouse.moviewallet.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Buy
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.digitalhouse.moviewallet.ui.viewholder.ProviderViewHolder
import com.squareup.picasso.Picasso

class ProviderBuyDetailsAdapter (val providers: MutableList<Buy>) :
    RecyclerView.Adapter<ProviderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProviderViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_details_provider, parent, false)
        )

    override fun onBindViewHolder(
        holder: ProviderViewHolder,
        position: Int
    ) {
        val provider = providers[position]
        val configuration = SingletonConfiguration.config

        val imageUrl =
            "${configuration?.images?.secure_base_url}${configuration?.images?.logo_sizes?.get(1)}${provider.logoPath}"
        val logo = holder.ivProvider
        Picasso.get().load(imageUrl).into(
            logo
        )
    }

    override fun getItemCount()= providers.size

}