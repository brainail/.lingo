package org.brainail.everboxing.lingo.cache.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.reactivex.Flowable
import org.brainail.everboxing.lingo.cache.model.SuggestionCacheEntity

@Dao
abstract class SuggestionDao : BaseDao<SuggestionCacheEntity> {
    @Transaction
    open fun saveSuggestions(suggestions: List<SuggestionCacheEntity>) {
        val (recent, new) = suggestions.partition { it.isRecent }
        insert(new)
        insertOrRecreate(recent) // move old/new to top
    }

    @Query("select * from suggestions where word like :query || '%' order by sg_id desc limit :limit")
    abstract fun getSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<SuggestionCacheEntity>>

    @Query("""
        select * from suggestions
        where is_recent = 1 and word like :query || '%'
        order by sg_id desc limit :limit""")
    abstract fun getRecentSuggestions(query: String, limit: Int = Int.MAX_VALUE): Flowable<List<SuggestionCacheEntity>>
}