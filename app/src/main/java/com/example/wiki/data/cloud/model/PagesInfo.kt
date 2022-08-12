package com.example.wiki.data.cloud.model

import com.google.gson.annotations.SerializedName

data class PagesInfo(
    @SerializedName("count")
    private val count: Int,
    @SerializedName("pages")
    private val pages: Int,
    @SerializedName("next")
    private val nextPageUrl: String?,
    @SerializedName("prev")
    private val prevPageUrl: String?
) {
    /**
     * "next": "https://rickandmortyapi.com/api/character/?page=2"
     * return a number of next page, if it`s the last page return null
     */
    fun getNextPage() = nextPageUrl?.replace(Regex("\\D"), "")?.toInt()
}