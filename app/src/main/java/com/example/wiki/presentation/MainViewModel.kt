package com.example.wiki.presentation

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wiki.data.Character
import com.example.wiki.data.CharacterPagingRepository
import com.example.wiki.data.StatusName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest


class MainViewModel(
    repository: CharacterPagingRepository
) : ViewModel() {

    private val searchBy = MutableLiveData(CharacterFilter())


    val charactersFlow: Flow<PagingData<Character>> = searchBy
        .asFlow()
        .debounce(500)
        .flatMapLatest { filter ->
            repository.fetchPagedCharacters(filter.name, filter.status)
        }
        .cachedIn(viewModelScope)

    fun setSearchName(name: String) {
        if (searchBy.value?.name == name) return
        searchBy.value = searchBy.value?.copy(name = name)
    }

    fun setSearchStatus(status: StatusName) {
        if (searchBy.value?.status == status) return
        searchBy.value = searchBy.value?.copy(status = status)
    }

    private data class CharacterFilter(
        val name: String = "",
        val status: StatusName = StatusName.Default
    )
}