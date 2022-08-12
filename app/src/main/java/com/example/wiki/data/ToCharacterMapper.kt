package com.example.wiki.data

import com.example.wiki.core.Abstract

interface ToCharacterMapper : Abstract.Mapper {

    fun map(id: Int, name: String, status: String, image: String): Character

    class Base : ToCharacterMapper {

        override fun map(id: Int, name: String, status: String, image: String) =
            Character(id, name, status, image)
    }
}