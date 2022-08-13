package com.example.wiki.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wiki.core.App
import com.example.wiki.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy{
        (application as App).mainViewModel
    }
    private val toUiFailMapper by lazy {
        (application as App).toUiFailMapper
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var characterAdapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchInput()
        loadData()
    }

    private fun setupRecyclerView() {
         val tryAgain = object : CharacterLoadStateAdapter.Retry {
            override fun tryAgain() {
                characterAdapter.retry()
            }
        }

        characterAdapter = CharacterAdapter()

        characterAdapter.addLoadStateListener { state ->
            with(binding) {
                recyclerView.isVisible = state.refresh != LoadState.Loading
                progress.isVisible = state.refresh == LoadState.Loading
            }
        }

        binding.recyclerView.adapter = characterAdapter.withLoadStateHeaderAndFooter(
            footer = CharacterLoadStateAdapter(tryAgain, toUiFailMapper),
            header = CharacterLoadStateAdapter(tryAgain, toUiFailMapper),
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupSearchInput() {
        binding.searchEditText.addTextChangedListener { inputText ->
            viewModel.setSearchBy(inputText.toString())
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.charactersFlow.collect {
                Log.d("AAA", "load: $it")
                characterAdapter.submitData(it)
            }
        }
    }
}