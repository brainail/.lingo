package org.brainail.EverboxingLingo.cache.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import io.reactivex.Flowable
import org.brainail.EverboxingLingo.cache.model.SuggestionCacheEntity

@Dao
abstract class SuggestionDao : BaseDao<SuggestionCacheEntity> {
    @Transaction
    open fun saveSuggestions(suggestions: List<SuggestionCacheEntity>) {
        val (recent, new) = suggestions.partition { it.isRecent }
        insert(new)
        insertOrRecreate(recent) // move old/new to top
    }

    @Query("""
        delete from suggestions
        where is_recent = 1
        and sg_id not in (select sg_id from suggestions where is_recent = 1 order by sg_id desc limit :limit)""")
    abstract fun keepRecent(limit: Int)

    @Query("select * from suggestions where word like :query || '%' order by sg_id desc limit :limit")
    abstract fun getSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<SuggestionCacheEntity>>

    @Query("""
        select * from suggestions
        where is_recent = 1 and word like :query || '%'
        order by sg_id desc limit :limit""")
    abstract fun getRecentSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<SuggestionCacheEntity>>
}