package org.brainail.EverboxingLingo.ui.home.explore

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.extensions.LayoutContainer
import org.brainail.EverboxingLingo.R

class LingoSearchResultsAdapter : RecyclerView.Adapter<LingoSearchResultsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false))
    }

    // TODO: enter anim
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTo()
    }

    override fun getItemCount(): Int {
        return 10
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindTo() {
        }
    }
}
