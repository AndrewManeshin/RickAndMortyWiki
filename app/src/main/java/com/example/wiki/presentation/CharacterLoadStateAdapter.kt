package com.example.wiki.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wiki.R

class CharacterLoadStateAdapter(
    private val retry: Retry,
    private val toUiFailMapper: ToUiFailMapper
) : LoadStateAdapter<CharacterLoadStateAdapter.ItemViewHolder>() {


    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.NotLoading -> error("Not supported")
        LoadState.Loading -> 0
        is LoadState.Error -> 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = when (getStateViewType(loadState)) {
        0 -> ItemViewHolder.Progress(R.layout.item_progress.makeView(parent))
        1 -> ItemViewHolder.Error(R.layout.item_error.makeView(parent), retry, toUiFailMapper)
        else -> error("Not supported")
    }

    override fun onBindViewHolder(holder: ItemViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    abstract class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        open fun bind(loadState: LoadState) = Unit

        class Progress(view: View) : ItemViewHolder(view)

        class Error constructor(
            view: View,
            private val retry: Retry,
            private val toUiFailMapper: ToUiFailMapper
        ) : ItemViewHolder(view) {
            private val message = itemView.findViewById<TextView>(R.id.messageTextView)
            private val button = itemView.findViewById<Button>(R.id.tryAgainButton)

            override fun bind(loadState: LoadState) {
                if (loadState is LoadState.Error) {
                    Log.e("AAA", "${loadState.error}")
                    toUiFailMapper.mapFail(loadState.error, message)
                }
                button.setOnClickListener {
                    retry.tryAgain()
                }
            }
        }
    }

    interface Retry {
        fun tryAgain()
    }

    private fun Int.makeView(parent: ViewGroup) =
        LayoutInflater.from(parent.context).inflate(this, parent, false)
}