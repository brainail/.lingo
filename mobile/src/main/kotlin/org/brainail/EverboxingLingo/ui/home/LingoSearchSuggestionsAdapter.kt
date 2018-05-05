package org.brainail.EverboxingLingo.ui.home

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_suggestion.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.extensions.inflate
import org.brainail.EverboxingLingo.model.SuggestionViewModel
import org.brainail.EverboxingLingo.ui.home.LingoSearchSuggestionsAdapter.SuggestionViewHolder

class LingoSearchSuggestionsAdapter(private val suggestionClickListener: SuggestionClickListener)
    : ListAdapter<SuggestionViewModel, SuggestionViewHolder>(diffCallback) {

    interface SuggestionClickListener {
        fun onSuggestionClick(item: SuggestionViewModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        return SuggestionViewHolder(parent.inflate(R.layout.item_suggestion))
    }

    // TODO: enter anim
    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    inner class SuggestionViewHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private lateinit var suggestionItem: SuggestionViewModel

        fun bindTo(item: SuggestionViewModel) {
            suggestionItem = item
            suggestionItemText.text = item.word
            suggestionItemDescription.text = item.description
            suggestionItemDescription.isVisible = !item.description.isEmpty()
            itemView.setOnClickListener { suggestionClickListener.onSuggestionClick(suggestionItem) }
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
