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

package org.brainail.everboxing.lingo.mapper

import org.brainail.everboxing.lingo.domain.model.SearchResult
import org.brainail.everboxing.lingo.model.SearchResultModel
import javax.inject.Inject

class SearchResultModelMapper @Inject constructor() : Mapper<SearchResult, SearchResultModel> {
    override fun mapT(input: SearchResultModel): SearchResult {
        return SearchResult(
            input.id, input.definitionId, input.word,
            input.definition, input.example, input.link, input.favorite
        )
    }

    override fun mapF(input: SearchResult): SearchResultModel {
        return SearchResultModel(
            input.id, input.definitionId, input.word,
            input.definition, input.example, input.link, input.favorite
        )
    }
}
