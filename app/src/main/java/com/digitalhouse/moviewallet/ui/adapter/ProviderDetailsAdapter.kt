package com.digitalhouse.moviewallet.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Flatrate
import com.digitalhouse.moviewallet.model.Providers
import com.digitalhouse.moviewallet.repository.SingletonConfiguration
import com.squareup.picasso.Picasso

class ProviderDetailsAdapter(val providers: MutableList<Flatrate>) :
    RecyclerView.Adapter<ProviderDetailsAdapter.ActorDetailsViewHolder>() {

    inner class ActorDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvProvider by lazy { view.findViewById<TextView>(R.id.tv_provider_details) }
        val ivProvider by lazy { view.findViewById<ImageView>(R.id.iv_provider_details) }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ActorDetailsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_details_actor, parent, false)
        )

    override fun onBindViewHolder(holder: ActorDetailsViewHolder, position: Int) {
        val provider = providers[position]
        val configuration = SingletonConfiguration.config

        val imageUrl =
            "${configuration?.images?.secure_base_url}${configuration?.images?.logo_sizes?.last()}${provider.logoPath}"
        val logo = holder.ivProvider
        Picasso.get().load(imageUrl).into(
            logo
        )

    }

    override fun getItemCount() = providers.size
}