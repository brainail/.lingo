package org.brainail.EverboxingLingo.ui.home

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_suggestion.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.model.SuggestionViewModel
import org.brainail.EverboxingLingo.ui.home.LingoSearchSuggestionsAdapter.SuggestionViewHolder
import org.brainail.logger.L

class LingoSearchSuggestionsAdapter : ListAdapter<SuggestionViewModel, SuggestionViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        return SuggestionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_suggestion, parent, false))
    }

    // TODO: enter anim
    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class SuggestionViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindTo(item: SuggestionViewModel) {
            suggestionItemText.text = item.word
            suggestionItemDescription.text = item.description
            suggestionItemDescription.isVisible = !item.description.isEmpty()
            itemView.setOnClickListener {
                L.i("Click on $adapterPosition")
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SuggestionViewModel>() {
            override fun areItemsTheSame(oldItem: SuggestionViewModel, newItem: SuggestionViewModel): Boolean =
                    false

            override fun areContentsTheSame(oldItem: SuggestionViewModel, newItem: SuggestionViewModel): Boolean =
                    oldItem == newItem
        }
    }
}
