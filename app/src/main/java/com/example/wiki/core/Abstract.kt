package com.example.wiki.core

interface Abstract {

    interface Object<T, M : Mapper> {

        fun map(mapper: M) : T
    }

    interface Mapper

    interface Comparing<T> {
        fun sameContent(character: T): Boolean
        fun same(character: T): Boolean
    }
}