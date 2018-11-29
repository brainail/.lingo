package org.brainail.everboxing.lingo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResultModel(
        val id: Int,
        val definitionId: String,
        val word: String,
        val definition: String,
        val example: String,
        val link: String,
        val favorite: Boolean) : Parcelable