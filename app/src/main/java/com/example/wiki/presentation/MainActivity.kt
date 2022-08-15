package com.example.wiki.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wiki.core.App
import com.example.wiki.data.StatusName
import com.example.wiki.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel by lazy {
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

        binding.searchView.setOnQueryTextListener(this)

        setupSearchStatus()
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

    override fun onQueryTextSubmit(query: String?) = false


    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.setSearchName(newText ?: "")
        return true
    }

    private fun setupSearchStatus() {
        binding.radioAlive.setOnClickListener { viewModel.setSearchStatus(StatusName.Alive) }
        binding.radioDead.setOnClickListener { viewModel.setSearchStatus(StatusName.Dead) }
        binding.radioUnknown.setOnClickListener { viewModel.setSearchStatus(StatusName.Unknown) }
        binding.radioAll.setOnClickListener { viewModel.setSearchStatus(StatusName.Default) }
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.charactersFlow.collect {
                characterAdapter.submitData(it)
            }
        }
    }
}