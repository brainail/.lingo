package org.brainail.everboxing.lingo.data.mapper

import org.brainail.everboxing.lingo.data.model.SuggestionEntity
import org.brainail.everboxing.lingo.domain.model.Suggestion
import javax.inject.Inject

/**
 * Map a [SuggestionEntity] to and from a [Suggestion] instance when data is passing between
 * this layer and the domain layer
 */
class SuggestionDataMapper @Inject constructor(): Mapper<SuggestionEntity, Suggestion> {
    override fun mapFromEntity(input: SuggestionEntity): Suggestion {
        return Suggestion(input.word, input.description, input.isRecent, input.id)
    }

    override fun mapToEntity(input: Suggestion): SuggestionEntity {
        return SuggestionEntity(input.word, input.description, input.isRecent, input.id)
    }
}
