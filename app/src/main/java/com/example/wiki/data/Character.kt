package com.example.wiki.data

import com.example.wiki.core.Abstract
import com.example.wiki.presentation.ToUiItemMapper

data class Character(
    private val id: Int,
    private val name: String,
    private val status: String,
    private val image: String
) : Abstract.Object<Unit, ToUiItemMapper>, Abstract.Comparing<Character> {

    override fun map(mapper: ToUiItemMapper) = mapper.map(name, status, image)

    override fun sameContent(character: Character) =
        character.name == name && character.status == status && character.image == image

    override fun same(character: Character) = id == character.id
}