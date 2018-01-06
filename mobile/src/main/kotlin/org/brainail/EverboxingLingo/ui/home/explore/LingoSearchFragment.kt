package org.brainail.EverboxingLingo.ui.home.explore

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_lingo_search.*
import org.brainail.EverboxingLingo.R
import org.brainail.EverboxingLingo.ui.ParcelableViewModelAwareFragment
import org.brainail.EverboxingLingo.ui.home.SearchViewModel
import org.brainail.EverboxingLingo.util.NavigableBack
import org.jetbrains.anko.toast

class LingoSearchFragment : ParcelableViewModelAwareFragment<LingoSearchFragmentViewModel>(), NavigableBack {

    lateinit private var searchViewModel: SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lingo_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        searchViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(SearchViewModel::class.java)
        searchViewModel.searchForResultsCall.observe(this, Observer {
            activity?.toast("Search for results where query = $it")
        })

        initSearchResults()
    }

    private fun initSearchResults() {
        searchResultsRecyclerView.adapter = LingoSearchResultsAdapter()
    }

    override fun viewModelType() = LingoSearchFragmentViewModel::class.java

    companion object {
        const val FRAGMENT_TAG = "LingoSearchFragmentTag"

        @JvmStatic
        fun newInstance(): LingoSearchFragment = LingoSearchFragment()
    }

}
