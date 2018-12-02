package org.brainail.everboxing.lingo.data.repository.search

import android.annotation.SuppressLint
import io.reactivex.Completable
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.data.mapper.SearchResultDataMapper
import org.brainail.everboxing.lingo.data.model.SearchResultEntity
import org.brainail.everboxing.lingo.data.source.search.SearchResultDataSourceFactory
import org.brainail.everboxing.lingo.domain.executor.AppExecutors
import org.brainail.everboxing.lingo.domain.model.SearchResult
import org.brainail.everboxing.lingo.domain.repository.SearchResultRepository
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

    override fun favoriteSearchResult(id: Int): Completable {
        return dataSourceFactory.obtainCacheDataSource().favoriteSearchResult(id)
    }

    override fun forgetSearchResult(id: Int): Completable {
        return dataSourceFactory.obtainCacheDataSource().forgetSearchResult(id)
    }

    private fun saveSearchResultEntities(searchResults: List<SearchResultEntity>): Completable {
        return dataSourceFactory.obtainCacheDataSource().saveSearchResults(searchResults)
    }

    override fun installUrbanSearchResult(pathToData: String): Completable {
        return dataSourceFactory.obtainCacheDataSource().installUrbanSearchResult(pathToData)
    }
}
