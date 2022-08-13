package com.example.wiki.data.cloud

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.wiki.data.Character
import com.example.wiki.data.cloud.model.CharactersCloud
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.net.UnknownHostException

class CharactersPagingSource(
    private val service: CharactersService,
    private val gson: Gson,
    private val mapper: CharactersCloudMapper,
    private val name: String
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> = try {
        val currentPage = params.key ?: 1
        val charactersCloud: CharactersCloud = gson.fromJson(
            service.fetchCharacters(currentPage, name).string(),
            object : TypeToken<CharactersCloud>() {}.type
        )
        val characters = charactersCloud.map(mapper)
        LoadResult.Page(
            data = characters,
            prevKey = if (currentPage == 1) null else currentPage - 1,
            nextKey = charactersCloud.getNextPage()
        )
    } catch (e: HttpException) {
        LoadResult.Error(e)
    } catch (e: UnknownHostException) {
        LoadResult.Error(e)
    } catch (e: Exception) {
        LoadResult.Error(e)
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }
}