package org.brainail.EverboxingLingo.navigator

import android.content.ActivityNotFoundException
import android.content.Intent
import android.support.v7.app.AppCompatActivity

abstract class SceneNavigator(protected val activity: AppCompatActivity): Navigator(activity) {
    protected inline fun startActivityForResult(requestCode: Int, intentFactory: () -> Intent) {
        try {
            activity.startActivityForResult(intentFactory(), requestCode)
        } catch (exception: ActivityNotFoundException) {
            // do nothing
        }
    }
}