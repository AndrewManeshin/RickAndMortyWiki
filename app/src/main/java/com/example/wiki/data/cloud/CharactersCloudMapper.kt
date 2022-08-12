package com.example.wiki.data.cloud

import com.example.wiki.core.Abstract
import com.example.wiki.data.Character
import com.example.wiki.data.ToCharacterMapper
import com.example.wiki.data.cloud.model.CharacterCloud

interface CharactersCloudMapper : Abstract.Mapper {

    fun map(characters: List<CharacterCloud>): List<Character>

    class Base(private val mapper: ToCharacterMapper) : CharactersCloudMapper {

        override fun map(characters: List<CharacterCloud>) = characters.map { characterCloud ->
            characterCloud.map(mapper)
        }
    }
}