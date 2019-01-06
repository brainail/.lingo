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

package org.brainail.everboxing.lingo.data.mapper

import org.brainail.everboxing.lingo.data.model.SearchResultEntity
import org.brainail.everboxing.lingo.domain.model.SearchResult
import javax.inject.Inject

class SearchResultDataMapper @Inject constructor() : Mapper<SearchResultEntity, SearchResult> {
    override fun mapF(input: SearchResultEntity): SearchResult {
        return SearchResult(
            input.id, input.definitionId, input.word,
            input.definition, input.example, input.link, input.favorite
        )
    }

    override fun mapT(input: SearchResult): SearchResultEntity {
        return SearchResultEntity(
            input.definitionId, input.word, input.definition,
            input.example, input.link, input.id, input.favorite
        )
    }
}
