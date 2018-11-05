package org.brainail.everboxing.lingo.ui.home.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.fragment_search_result_details.*
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.ui.base.BaseViewModel
import org.brainail.everboxing.lingo.ui.base.ViewModelAwareFragment
import org.brainail.everboxing.lingo.util.MenuItemClickHandler
import org.brainail.everboxing.lingo.util.ViewClickHandler
import org.brainail.everboxing.lingo.base.util.lazyFast


class LingoSearchResultDetailsFragment
    : ViewModelAwareFragment(), MenuItemClickHandler, ViewClickHandler {

    override fun createPrimaryViewModels(): Array<BaseViewModel>? {
        return null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_result_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getParcelable<SearchResultModel>(PARAM_ITEM)
        searchResultWord.text = item?.word
        searchResultDefinition.text = item?.definition
        searchResultExamples.text = item?.example
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
        val layoutTag: String by lazyFast { LingoSearchResultDetailsFragment::class.java.simpleName }

        @JvmStatic
        fun makeArguments(item: SearchResultModel) = bundleOf(PARAM_ITEM to item)

        @JvmStatic
        fun newInstance() = LingoSearchResultDetailsFragment()
    }
}
