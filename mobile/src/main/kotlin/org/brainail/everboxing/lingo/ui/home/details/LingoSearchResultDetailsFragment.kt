package org.brainail.everboxing.lingo.ui.home.details

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_search_result_details.*
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.ui.base.BaseViewModel
import org.brainail.everboxing.lingo.ui.base.ViewModelAwareFragment
import org.brainail.everboxing.lingo.util.extensions.lazyFast
import org.brainail.everboxing.lingo.widget.AppCompatBottomAppBar


class LingoSearchResultDetailsFragment : ViewModelAwareFragment() {
    override fun createPrimaryViewModels(): Array<BaseViewModel>? {
        return null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search_result_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = arguments?.getParcelable<SearchResultModel>("item")
        searchResultWord.text = item?.word
        searchResultDefinition.text = item?.definition
        searchResultExamples.text = item?.example
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val searchView = requireActivity().findViewById<AppBarLayout>(R.id.appBarView)
        searchView.setExpanded(false)
        val bottomView = requireActivity().findViewById<AppCompatBottomAppBar>(R.id.bottomAppBarView)
        bottomView.show()
        bottomView.replaceMenu(R.menu.menu_details_bottom_bar)
        Handler().postDelayed({ bottomView.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER }, 200)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.floatingActionButtonView)
        fab.setImageResource(R.drawable.ic_twotone_favorite_24dp)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val searchView = requireActivity().findViewById<AppBarLayout>(R.id.appBarView)
        searchView.setExpanded(true)
        val bottomView = requireActivity().findViewById<BottomAppBar>(R.id.bottomAppBarView)
        bottomView.replaceMenu(R.menu.menu_home_bottom_bar)
        Handler().postDelayed({ bottomView.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END }, 200)
        val fab = requireActivity().findViewById<FloatingActionButton>(R.id.floatingActionButtonView)
        fab.setImageResource(R.drawable.ic_search_black_24dp)
    }

    companion object {
        @JvmStatic
        val layoutTag: String by lazyFast { LingoSearchResultDetailsFragment::class.java.simpleName }

        @JvmStatic
        fun newInstance() = LingoSearchResultDetailsFragment()
    }
}
