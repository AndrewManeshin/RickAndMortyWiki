package com.example.wiki.presentation.mapper

import com.bumptech.glide.Glide
import com.example.wiki.R
import com.example.wiki.core.Abstract
import com.example.wiki.databinding.ItemCharacterBinding

interface ToUiItemMapper : Abstract.Mapper {

    fun map(name: String, status: String, image: String)

    class Base(
        private val binding: ItemCharacterBinding
    ) : ToUiItemMapper {

        override fun map(name: String, status: String, image: String) {

            binding.name.text = name
            binding.status.text = status
            Glide.with(binding.image) //todo make better
                .load(image)
                .placeholder(R.drawable.image_holder)
                .into(binding.image)
        }
    }
}