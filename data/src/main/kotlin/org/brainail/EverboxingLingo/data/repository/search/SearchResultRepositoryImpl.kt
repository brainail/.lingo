package org.brainail.EverboxingLingo.data.repository.search

import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.EverboxingLingo.data.mapper.SearchResultDataMapper
import org.brainail.EverboxingLingo.data.model.SearchResultEntity
import org.brainail.EverboxingLingo.data.source.search.SearchResultDataSourceFactory
import org.brainail.EverboxingLingo.domain.model.SearchResult
import org.brainail.EverboxingLingo.domain.repository.SearchResultRepository
import javax.inject.Inject

class SearchResultRepositoryImpl @Inject constructor(
        private val dataSourceFactory: SearchResultDataSourceFactory,
        private val searchResultMapper: SearchResultDataMapper) : SearchResultRepository {

    override fun clearSearchResults(): Completable {
        return dataSourceFactory.obtainCacheDataSource().clearSearchResults()
    }

    override fun saveSearchResults(searchResults: List<SearchResult>): Completable {
        val searchResultEntities = searchResults.map { searchResultMapper.mapToEntity(it) }
        return saveSearchResultEntities(searchResultEntities)
    }

    override fun getSearchResults(query: String): Flowable<List<SearchResult>> {
        return Flowable.just((1..3).map {
            SearchResult("$it", "word_$it", "def_$it", "ex_$it")
        })
    }

    private fun saveSearchResultEntities(searchResults: List<SearchResultEntity>): Completable {
        return dataSourceFactory.obtainCacheDataSource().saveSearchResults(searchResults)
    }
}