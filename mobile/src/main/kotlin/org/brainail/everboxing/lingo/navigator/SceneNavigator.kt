package org.brainail.everboxing.lingo.navigator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import org.brainail.everboxing.lingo.base.util.safeTry

abstract class SceneNavigator(protected val activity: AppCompatActivity) : Navigator(activity) {
    protected inline fun startActivityForResult(requestCode: Int, intentFactory: () -> Intent) {
        safeTry { activity.startActivityForResult(intentFactory(), requestCode) }
    }
}
