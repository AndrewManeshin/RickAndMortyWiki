package com.example.wiki.presentation

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wiki.data.Character
import com.example.wiki.data.CharacterPagingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest

class MainViewModel(
    repository: CharacterPagingRepository
) : ViewModel() {

    private val searchName = MutableLiveData("")

    val charactersFlow: Flow<PagingData<Character>> = searchName
        .asFlow()
        .debounce(500)
        .flatMapLatest { name ->
            repository.fetchPagedCharacters(name)
        }
        .cachedIn(viewModelScope)

    fun setSearchBy(value: String) {
        if (this.searchName.value == value) return
        this.searchName.value = value
    }

    fun refresh() {
        this.searchName.postValue(this.searchName.value)
    }
}