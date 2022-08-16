package com.example.wiki.presentation.listeners

import androidx.appcompat.widget.SearchView

interface SearchViewListener : SearchView.OnQueryTextListener {

    override fun onQueryTextSubmit(query: String?) = false

    class Base(
        private val callback: (newText: String?) -> Unit
    ) : SearchViewListener {

        override fun onQueryTextChange(newText: String?): Boolean {
            callback.invoke(newText)
            return true
        }
    }
}