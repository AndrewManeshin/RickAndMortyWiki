package com.example.wiki.data.cloud.model

import com.example.wiki.core.Abstract
import com.example.wiki.data.Character
import com.example.wiki.data.ToCharacterMapper
import com.google.gson.annotations.SerializedName

data class CharacterCloud(
    @SerializedName("id")
    private val id: Int,

    @SerializedName("name")
    private val name: String,

    @SerializedName("status")
    private val status: String,

    @SerializedName("image")
    private val image: String
) : Abstract.Object<Character, ToCharacterMapper> {

    override fun map(mapper: ToCharacterMapper) = mapper.map(id, name, status, image)
}