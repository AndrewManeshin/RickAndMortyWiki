package com.example.wiki.data.cloud.model

import com.example.wiki.core.Abstract
import com.example.wiki.data.Character
import com.example.wiki.data.cloud.CharactersCloudMapper
import com.google.gson.annotations.SerializedName

data class CharactersCloud(
    @SerializedName("info")
    private val info: PagesInfo,
    @SerializedName("results")
    private val result: List<CharacterCloud>
) : Abstract.Object<List<Character>, CharactersCloudMapper> {

    override fun map(mapper: CharactersCloudMapper) = mapper.map(result)

    fun getNextPage() = info.getNextPage()
}