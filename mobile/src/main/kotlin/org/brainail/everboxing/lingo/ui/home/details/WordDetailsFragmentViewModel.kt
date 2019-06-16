/*
 * Copyright 2019 Malyshev Yegor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brainail.everboxing.lingo.ui.home.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.brainail.everboxing.lingo.ui.base.PartialViewStateChange
import org.brainail.everboxing.lingo.ui.base.RxAwareViewModel
import org.brainail.everboxing.lingo.ui.base.ViewModelSavedState

import org.brainail.everboxing.lingo.ui.home.details.WordDetailsFragmentViewState.InitWordDetails
import javax.inject.Inject

class WordDetailsFragmentViewModel @Inject constructor() : RxAwareViewModel() {

    private val viewState = MutableLiveData<WordDetailsFragmentViewState>()

    fun viewState(): LiveData<WordDetailsFragmentViewState> = viewState

    init {
        viewState.value = WordDetailsFragmentViewState.INITIAL
    }

    override fun initState(viewModelSavedState: ViewModelSavedState?) {
        super.initState(viewModelSavedState)

        getInitialArgs<WordDetailsFragmentInitialArgs>()?.takeIf { isFirstRestore() }?.run {
            applyChanges(InitWordDetails(getWordItem()))
        }
    }

    private fun applyChanges(
        partialViewStateChange: PartialViewStateChange<WordDetailsFragmentViewState>
    ): WordDetailsFragmentViewState {
        viewState.value = partialViewStateChange.applyTo(viewState.value!!)
        return viewState.value!!
    }
}
