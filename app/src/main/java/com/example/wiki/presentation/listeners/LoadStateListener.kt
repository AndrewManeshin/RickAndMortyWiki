package com.example.wiki.presentation.listeners

import androidx.core.view.isVisible
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.wiki.databinding.FragmentCharacterListBinding
import com.example.wiki.presentation.mapper.ToUiFailMapper

interface LoadStateListener {

    fun stateHandle(state: CombinedLoadStates)

    class Base(
        private val binding: FragmentCharacterListBinding,
        private val failMapper: ToUiFailMapper,
        private val retryListener: () -> Unit
    ) : LoadStateListener {

        override fun stateHandle(state: CombinedLoadStates) {
            with(binding) {
                if (state.refresh is LoadState.Error) {
                    progress.isVisible = false
                    errorLayout.root.isVisible = true
                    failMapper.mapFail(
                        (state.refresh as LoadState.Error).error,
                        errorLayout.messageTextView
                    )
                    errorLayout.tryAgainButton.setOnClickListener {
                        retryListener()
                    }
                } else {
                    recyclerView.isVisible = state.refresh != LoadState.Loading
                    progress.isVisible = state.refresh == LoadState.Loading
                    errorLayout.root.isVisible = false
                }
            }
        }
    }
}