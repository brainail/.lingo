package org.brainail.everboxing.lingo.di.tasks

import dagger.Subcomponent
import dagger.android.AndroidInjector
import org.brainail.everboxing.lingo.tasks.InstallUrbanServiceDataTask

@Subcomponent
interface InstallUrbanServiceDataTaskSubcomponent : AndroidInjector<InstallUrbanServiceDataTask> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<InstallUrbanServiceDataTask>()
}
