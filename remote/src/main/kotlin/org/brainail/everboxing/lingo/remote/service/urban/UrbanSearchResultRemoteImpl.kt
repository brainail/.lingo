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

package org.brainail.everboxing.lingo.remote.service.urban

import io.reactivex.Single
import org.brainail.everboxing.lingo.data.model.SearchResultEntity
import org.brainail.everboxing.lingo.data.repository.search.SearchResultRemote
import org.brainail.everboxing.lingo.remote.mapper.UrbanSearchResultRemoteMapper
import javax.inject.Inject

class UrbanSearchResultRemoteImpl @Inject constructor(
    private val urbanService: UrbanService,
    private val entityMapper: UrbanSearchResultRemoteMapper
) : SearchResultRemote {

    override fun getSearchResults(query: String): Single<List<SearchResultEntity>> {
        return urbanService.getSearchResults(query)
            .map { it.list.map { entityMapper.mapFromRemote(it) } }
    }
}
