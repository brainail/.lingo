package org.brainail.everboxing.lingo.ui.home.explore

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import org.brainail.everboxing.lingo.R
import org.brainail.everboxing.lingo.model.SearchResultModel
import org.brainail.everboxing.lingo.navigator.SceneNavigator

class ExploreFragmentNavigator(activity: AppCompatActivity) : SceneNavigator(activity) {
    fun openWordDetails(item: SearchResultModel) {
        val navController = activity.findNavController(R.id.lingoHomeNavigationFragment)
        navController.navigate(ExploreFragmentDirections.openWordDetailsAction(item))
    }
}
