package com.example.wiki.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wiki.core.App
import com.example.wiki.data.StatusName
import com.example.wiki.databinding.FragmentCharacterListBinding
import com.example.wiki.presentation.listeners.LoadStateListener
import com.example.wiki.presentation.adapter.CharacterAdapter
import com.example.wiki.presentation.adapter.CharacterLoadStateAdapter
import com.example.wiki.presentation.listeners.SearchViewListener
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment() {

    private val viewModel by lazy {
        (requireActivity().application as App).characterListViewModel
    }
    private val toUiFailMapper by lazy {
        (requireActivity().application as App).toUiFailMapper
    }
    private lateinit var binding: FragmentCharacterListBinding
    private lateinit var characterAdapter: CharacterAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)

        setupRecyclerView()
        setupSearchView()
        setupSearchStatus()
        loadData()
        return binding.root
    }

    private fun setupRecyclerView() {

        characterAdapter = CharacterAdapter()

        val tryAgain = object : CharacterLoadStateAdapter.Retry {
            override fun tryAgain() {
                characterAdapter.retry()
            }
        }

        val listener = LoadStateListener.Base(binding, toUiFailMapper) {
            characterAdapter.retry()
        }

        characterAdapter.addLoadStateListener { state ->
            listener.stateHandle(state)
        }

        binding.recyclerView.adapter = characterAdapter.withLoadStateHeaderAndFooter(
            footer = CharacterLoadStateAdapter(tryAgain, toUiFailMapper),
            header = CharacterLoadStateAdapter(tryAgain, toUiFailMapper),
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(SearchViewListener.Base { newText ->
            viewModel.setSearchName(newText ?: "")
        })
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