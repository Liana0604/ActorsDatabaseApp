package com.example.actorsdatabaseapp.data.sqlite.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.actorsdatabaseapp.R
import com.example.actorsdatabaseapp.data.sqlite.ui.actors.ActorsFragment
import com.example.actorsdatabaseapp.data.sqlite.ui.movies.MoviesFragment
import com.example.actorsdatabaseapp.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.actorFragment -> replaceFragment(ActorsFragment())
                R.id.movieFragment -> replaceFragment(MoviesFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}

