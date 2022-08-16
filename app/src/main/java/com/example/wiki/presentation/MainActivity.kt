package com.example.wiki.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wiki.R
import com.example.wiki.databinding.ActivityMainBinding
import com.example.wiki.presentation.screens.CharacterListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, CharacterListFragment())
                .commit()
        }
    }
}