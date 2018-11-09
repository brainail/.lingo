package org.brainail.everboxing.lingo.ui.home.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_word_details.*
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.base.util.lazyFast
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.ui.base.BaseViewModel
import org.brainail.everboxing.lingo.ui.base.ViewModelAwareFragment
import org.brainail.everboxing.lingo.util.MenuItemClickHandler
import org.brainail.everboxing.lingo.util.ViewClickHandler


class WordDetailsFragment
    : ViewModelAwareFragment(), MenuItemClickHandler, ViewClickHandler {

    override fun createPrimaryViewModels(): Array<BaseViewModel>? {
        return null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_word_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val detailsArgs = WordDetailsFragmentArgs.fromBundle(arguments)
        searchResultWord.text = detailsArgs.wordItem.word
        searchResultDefinition.text = detailsArgs.wordItem.definition
        searchResultExamples.text = detailsArgs.wordItem.example
    }

    override fun handleMenuItemClick(menuId: Int): Boolean {
        return super.handleMenuItemClick(menuId)
    }

    override fun handleViewClick(viewId: Int): Boolean {
        return super.handleViewClick(viewId)
    }

    companion object {
        private const val PARAM_ITEM = "item"

        @JvmStatic
        val layoutTag: String by lazyFast { "${WordDetailsFragment::class.java.simpleName}.FragmentTag" }

        @JvmStatic
        fun makeArguments(item: SearchResultModel) = bundleOf(PARAM_ITEM to item)

        @JvmStatic
        fun newInstance() = WordDetailsFragment()
    }
}
