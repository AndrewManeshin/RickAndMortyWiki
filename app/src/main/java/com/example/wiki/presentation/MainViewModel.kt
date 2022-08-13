package com.example.wiki.presentation

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.wiki.data.Character
import com.example.wiki.data.CharacterPagingRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    repository: CharacterPagingRepository
) : ViewModel() {
    val charactersFlow: Flow<PagingData<Character>> =
        repository.fetchPagedCharacters().cachedIn(viewModelScope)
}