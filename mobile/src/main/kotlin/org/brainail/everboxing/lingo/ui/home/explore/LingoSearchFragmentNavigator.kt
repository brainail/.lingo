package org.brainail.everboxing.lingo.ui.home.explore

import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.navigator.SceneNavigator
import org.brainail.everboxing.lingo.ui.home.details.LingoSearchResultDetailsFragment
import org.brainail.everboxing.lingo.util.extensions.openFragment

class LingoSearchFragmentNavigator(activity: AppCompatActivity) : SceneNavigator(activity) {
    fun openSearchResultDetails(item: SearchResultModel) {
        activity.openFragment(LingoSearchResultDetailsFragment.layoutTag, R.id.containerView, openOnTop = true) {
            LingoSearchResultDetailsFragment.newInstance().apply {
                arguments = bundleOf("item" to item)
            }
        }
    }
}