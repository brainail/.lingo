package org.brainail.everboxing.lingo.cache.db.dao

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.cache.model.SearchResultCacheEntity

@Dao
abstract class SearchResultDao : BaseDao<SearchResultCacheEntity> {
    @Query("select * from search_results where word like :query || '%' order by sr_id desc")
    abstract fun getSearchResults(query: String): Flowable<List<SearchResultCacheEntity>>

    @Query("delete from search_results")
    abstract fun deleteAll()

    @Query("update search_results set favorite = 1 - favorite where sr_id = :id")
    abstract fun favoriteSearchResult(id: Int)

    @Query("delete from search_results where sr_id = :id")
    abstract fun forgetSearchResult(id: Int)
}