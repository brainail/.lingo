package org.brainail.everboxing.lingo.cache.mapper

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.brainail.everboxing.lingo.cache.model.SearchResultCacheEntity
import org.brainail.everboxing.lingo.cache.model.SearchResultUrbanInstallEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchResultInstallDataMapper @Inject constructor(private val parser: Gson)
    : Mapper<SearchResultCacheEntity, String> {

    override fun mapToCache(input: String): SearchResultCacheEntity {
        val entity = parser.fromJson(input, SearchResultUrbanInstallEntity::class.java)
        return SearchResultCacheEntity(0, entity.defid, entity.word,
                entity.definition, entity.example, entity.permalink, false)
    }

    override fun mapFromCache(input: SearchResultCacheEntity) = throw UnsupportedOperationException()
}

object SearchResultInstallDataMapperFactory {
    fun makeMapper() = SearchResultInstallDataMapper(makeGson())

    private fun makeGson(): Gson {
        return GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }
}
