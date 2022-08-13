package com.example.wiki.core

import android.app.Application
import com.example.wiki.data.CharacterPagingRepository
import com.example.wiki.data.ToCharacterMapper
import com.example.wiki.data.cloud.CharactersService
import com.example.wiki.presentation.*
import com.example.wiki.data.cloud.CharactersCloudMapper
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class App : Application() {

    private companion object {
        const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    lateinit var mainViewModel: MainViewModel
    lateinit var toUiFailMapper: ToUiFailMapper

    override fun onCreate() {
        super.onCreate()
        val client = OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(2, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .build()
        val service = retrofit.create(CharactersService::class.java)
        val gson = Gson()

        val toCharacterMapper = ToCharacterMapper.Base()
        val charactersCloudMapper = CharactersCloudMapper.Base(toCharacterMapper)

        val characterRepository = CharacterPagingRepository.Base(
            service,
            gson,
            charactersCloudMapper
        )

        mainViewModel = MainViewModel(characterRepository)

        val resourceProvider = ResourceProvider.Base(applicationContext)
        toUiFailMapper =ToUiFailMapper.Base(resourceProvider)
    }
}