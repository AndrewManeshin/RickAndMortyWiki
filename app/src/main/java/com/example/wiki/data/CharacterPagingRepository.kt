package com.example.wiki.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.wiki.data.cloud.CharactersCloudMapper
import com.example.wiki.data.cloud.CharactersPagingSource
import com.example.wiki.data.cloud.CharactersService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow

interface CharacterPagingRepository {

    fun fetchPagedCharacters(name: String): Flow<PagingData<Character>>

    class Base(
        private val service: CharactersService,
        private val gson: Gson,
        private val mapper: CharactersCloudMapper
    ) : CharacterPagingRepository {

        override fun fetchPagedCharacters(name: String): Flow<PagingData<Character>> {
            return Pager(
                config = PagingConfig(
                    pageSize = PAGE_SIZE,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { CharactersPagingSource(service, gson, mapper, name) }
            ).flow
        }

        private companion object {
            const val PAGE_SIZE = 20
        }
    }
}