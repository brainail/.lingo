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

import io.reactivex.Flowable
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.model.SearchResult
import org.brainail.everboxing.lingo.domain.repository.SearchResultRepository
import javax.inject.Inject

class FindSearchResultsUseCase @Inject constructor(
    private val appExecutors: AppExecutors,
    private val searchResultRepository: SearchResultRepository
) {

    fun execute(query: String): Flowable<List<SearchResult>> {
        return searchResultRepository.getSearchResults(query)
            .onErrorReturn { emptyList() }
            .map { it.filter { it.word.isNotBlank() } }
            .compose(appExecutors.applyFlowableBackgroundSchedulers())
    }
}
