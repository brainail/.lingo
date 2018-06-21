package org.brainail.EverboxingLingo.cache.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import org.brainail.EverboxingLingo.cache.model.SearchResultCacheEntity

@Dao
abstract class SearchResultDao : BaseDao<SearchResultCacheEntity> {
    @Query("select * from search_results where word like :query || '%'")
    abstract fun getSearchResults(query: String): Flowable<List<SearchResultCacheEntity>>

    @Query("delete from search_results")
    abstract fun deleteAll()
}