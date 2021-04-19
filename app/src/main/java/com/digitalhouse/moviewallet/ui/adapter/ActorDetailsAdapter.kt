package com.digitalhouse.moviewallet.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digitalhouse.moviewallet.R
import com.digitalhouse.moviewallet.model.Actor

class ActorDetailsAdapter(val listActor: MutableList<Actor>) :
    RecyclerView.Adapter<ActorDetailsAdapter.ActorDetailsViewHolder>() {

    inner class ActorDetailsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvActor by lazy { view.findViewById<TextView>(R.id.tv_actors_details) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ActorDetailsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_details_actor, parent, false)
        )

    override fun onBindViewHolder(holder: ActorDetailsViewHolder, position: Int) {
        val actor = listActor[position]
        holder.tvActor.text = actor.name
    }

    override fun getItemCount() = listActor.size
}