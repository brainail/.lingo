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

package org.brainail.everboxing.lingo.cache.mapper

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.brainail.everboxing.lingo.cache.model.SearchResultCacheEntity
import org.brainail.everboxing.lingo.cache.model.SearchResultUrbanInstallEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchResultInstallDataMapper @Inject constructor(
    private val parser: Gson
) : Mapper<SearchResultCacheEntity, String> {

    override fun mapToCache(input: String): SearchResultCacheEntity {
        val entity = parser.fromJson(input, SearchResultUrbanInstallEntity::class.java)
        return SearchResultCacheEntity(
            0, entity.defid, entity.word,
            entity.definition, entity.example,
            entity.permalink, false
        )
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
