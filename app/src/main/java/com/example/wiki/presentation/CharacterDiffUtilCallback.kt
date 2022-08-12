package com.example.wiki.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.wiki.data.Character

class CharacterDiffUtilCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.same(newItem)
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.sameContent(newItem)
    }
}