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
import org.brainail.EverboxingLingo.model.SuggestionModel
import org.brainail.EverboxingLingo.ui.home.LingoSearchSuggestionsAdapter.SuggestionViewHolder
import org.brainail.EverboxingLingo.util.extensions.inflate

class LingoSearchSuggestionsAdapter(private val suggestionClickListener: SuggestionClickListener? = null)
    : ListAdapter<SuggestionModel, SuggestionViewHolder>(diffCallback) {

    private val listenersDelegate = ListenersDelegate()

    interface SuggestionClickListener {
        fun onSuggestionClick(item: SuggestionModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        return SuggestionViewHolder(parent.inflate(R.layout.item_suggestion), listenersDelegate)
    }

    // TODO: enter anim
    override fun onBindViewHolder(holder: SuggestionViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    class SuggestionViewHolder(
            override val containerView: View,
            private val suggestionClickListener: SuggestionClickListener)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private lateinit var suggestionItem: SuggestionModel

        fun bindTo(item: SuggestionModel) {
            suggestionItem = item
            suggestionItemText.text = item.word
            suggestionItemDescription.text = item.description
            suggestionItemDescription.isVisible = !item.description.isEmpty()
            itemView.setOnClickListener { suggestionClickListener.onSuggestionClick(suggestionItem) }
        }
    }

    inner class ListenersDelegate : LingoSearchSuggestionsAdapter.SuggestionClickListener {
        override fun onSuggestionClick(item: SuggestionModel) {
            suggestionClickListener?.onSuggestionClick(item)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<SuggestionModel>() {
            override fun areItemsTheSame(oldItem: SuggestionModel, newItem: SuggestionModel): Boolean =
                    false

            override fun areContentsTheSame(oldItem: SuggestionModel, newItem: SuggestionModel): Boolean =
                    oldItem == newItem
        }
    }
}
