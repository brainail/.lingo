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

package org.brainail.everboxing.lingo.data.source.suggestion

import org.brainail.everboxing.lingo.data.repository.suggestion.SuggestionDataSource
import javax.inject.Inject

class SuggestionsDataSourceFactory @Inject constructor(
    private val suggestionsCacheDataSource: SuggestionsCacheDataSource,
    private val suggestionsRemoteDataSource: SuggestionsRemoteDataSource
) {

    fun obtainCacheDataSource(): SuggestionDataSource {
        return suggestionsCacheDataSource
    }

    fun obtainRemoteDataSource(): SuggestionDataSource {
        return suggestionsRemoteDataSource
    }
}
