package com.example.wiki.presentation.mapper

import android.widget.TextView
import com.example.wiki.R
import com.example.wiki.core.ResourceProvider
import retrofit2.HttpException
import java.net.UnknownHostException

interface ToUiFailMapper {

    fun mapFail(e: Throwable?, view: TextView)

    class Base(
        private val resourceProvider: ResourceProvider
    ) : ToUiFailMapper {

        override fun mapFail(e: Throwable?, textView: TextView) {
            textView.text = when (e) {
                is UnknownHostException -> resourceProvider.getString(R.string.no_connection_message)
                is HttpException -> resourceProvider.getString(R.string.service_unavailable_message)
                else -> resourceProvider.getString(R.string.something_went_wrong)
            }
        }
    }
}