package com.rolemodel.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rolemodel.R
import com.rolemodel.data.model.Person
import com.rolemodel.data.model.PersonDiffCallback
import com.rolemodel.util.upperCaseFirstLetter
import kotlinx.android.synthetic.main.item_artist.view.*

class ArtistsListAdapter : ListAdapter<Person, ArtistsListAdapter.ViewHolder>(PersonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
                inflater.inflate(
                        R.layout.item_artist,
                        parent,
                        false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(person: Person) {
            with(itemView) {
                tvName.text = person.name

                person.functions?.let { safeFunctions ->
                    tvFunction.text =
                            if (safeFunctions.isEmpty())
                                "Unknown" else
                                safeFunctions[0].upperCaseFirstLetter()

                }

                Glide.with(context)
                        .load(person.avatar)
                        .placeholder(R.drawable.ic_artist_placeholder)
                        .error(R.drawable.ic_artist_placeholder)
                        .into(ivAvatar)
            }
        }
    }
}