package com.example.wiki.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wiki.data.Character
import com.example.wiki.databinding.ItemCharacterBinding

class CharacterAdapter :
    PagingDataAdapter<Character, CharacterAdapter.ViewHolder>(CharacterDiffUtilCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return ViewHolder.Base(binding)
    }

    abstract class ViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {

        abstract fun bind(character: Character?)

        class Base(
            private val binding: ItemCharacterBinding
        ) : ViewHolder(binding.root) {

            override fun bind(character: Character?) {
                character?.map(ToUiItemMapper.Base(binding)) ?: return
            }
        }
    }
}