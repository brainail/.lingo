/*
 * Copyright 2018 Malyshev Yegor
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

package org.brainail.everboxing.lingo.domain.usecase

import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.repository.SuggestionsRepository
import javax.inject.Inject

class FindFavoriteSuggestionsUseCase @Inject constructor(
    appExecutors: AppExecutors,
    private val suggestionsRepository: SuggestionsRepository
) : BaseFindSuggestionsUseCase(appExecutors) {

    override fun getSuggestions(query: String) =
        suggestionsRepository.getFavoriteSuggestions(query, NUMBER_OF_RECENT, NUMBER_OF_OTHERS)
}
