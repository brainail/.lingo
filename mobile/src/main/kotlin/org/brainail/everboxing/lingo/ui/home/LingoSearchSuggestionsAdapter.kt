package org.brainail.everboxing.lingo.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_suggestion.*
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.model.SuggestionModel
import org.brainail.everboxing.lingo.ui.home.LingoSearchSuggestionsAdapter.SuggestionViewHolder
import org.brainail.everboxing.lingo.util.extensions.inflate

class LingoSearchSuggestionsAdapter(private val suggestionClickListener: SuggestionClickListener? = null)
    : ListAdapter<SuggestionModel, SuggestionViewHolder>(diffCallback) {

    private val listenersDelegate = ListenersDelegate()

    interface SuggestionClickListener {
        fun onSuggestionClick(item: SuggestionModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionViewHolder {
        return SuggestionViewHolder(parent.inflate(R.layout.item_suggestion), listenersDelegate)
    }

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

            suggestionItemIcon.setImageDrawable(when (item.isRecent) {
                true -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_history_control_24dp)
                else -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_search_black_24dp)
            })

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
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: SuggestionModel, newItem: SuggestionModel): Boolean =
                    oldItem == newItem
        }
    }
}
