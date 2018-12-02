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

import org.brainail.everboxing.lingo.data.model.SuggestionEntity
import org.brainail.everboxing.lingo.domain.model.Suggestion
import javax.inject.Inject

/**
 * Map a [SuggestionEntity] to and from a [Suggestion] instance when data is passing between
 * this layer and the domain layer
 */
class SuggestionDataMapper @Inject constructor() : Mapper<SuggestionEntity, Suggestion> {
    override fun mapFromEntity(input: SuggestionEntity): Suggestion {
        return Suggestion(input.word, input.description, input.isRecent, input.id)
    }

    override fun mapToEntity(input: Suggestion): SuggestionEntity {
        return SuggestionEntity(input.word, input.description, input.isRecent, input.id)
    }
}
