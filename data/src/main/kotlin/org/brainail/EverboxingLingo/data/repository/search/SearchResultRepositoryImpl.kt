package org.brainail.EverboxingLingo.data.repository.search

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.EverboxingLingo.data.mapper.SearchResultDataMapper
import org.brainail.EverboxingLingo.data.model.SearchResultEntity
import org.brainail.EverboxingLingo.data.source.search.SearchResultDataSourceFactory
import org.brainail.EverboxingLingo.domain.executor.AppExecutors
import org.brainail.EverboxingLingo.domain.model.SearchResult
import org.brainail.EverboxingLingo.domain.repository.SearchResultRepository
import javax.inject.Inject

class SearchResultRepositoryImpl @Inject constructor(
        private val dataSourceFactory: SearchResultDataSourceFactory,
        private val searchResultMapper: SearchResultDataMapper,
        private val appExecutors: AppExecutors) : SearchResultRepository {

    override fun clearSearchResults(): Completable {
        return dataSourceFactory.obtainCacheDataSource().clearSearchResults()
    }

    override fun saveSearchResults(searchResults: List<SearchResult>): Completable {
        val searchResultEntities = searchResults.map { searchResultMapper.mapToEntity(it) }
        return saveSearchResultEntities(searchResultEntities)
    }

    @SuppressLint("CheckResult")
    override fun getSearchResults(query: String): Flowable<List<SearchResult>> {
        dataSourceFactory.obtainRemoteDataSource()
                .getSearchResults(query)
                .subscribeOn(appExecutors.backgroundScheduler) // run on a new thread in parallel
                .subscribe({ saveSearchResultEntities(it).subscribe() }, { /* Ignored */ })

        return dataSourceFactory.obtainCacheDataSource()
                .getSearchResults(query)
                .map { it.map { searchResultMapper.mapFromEntity(it) } }
    }

    private fun saveSearchResultEntities(searchResults: List<SearchResultEntity>): Completable {
        return dataSourceFactory.obtainCacheDataSource().saveSearchResults(searchResults)
    }
}