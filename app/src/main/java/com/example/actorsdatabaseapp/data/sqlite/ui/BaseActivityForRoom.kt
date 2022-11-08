package com.example.actorsdatabaseapp.data.sqlite.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.actorsdatabaseapp.R
import com.example.actorsdatabaseapp.data.room.ui.actorsforroom.ActorsRFragment
import com.example.actorsdatabaseapp.data.room.ui.moviesforroom.MoviesRFragment
import com.example.actorsdatabaseapp.databinding.ActivityBaseForRoomBinding

class BaseActivityForRoom : AppCompatActivity() {
    private lateinit var binding: ActivityBaseForRoomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseForRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.actorFragment -> replaceFragment(ActorsRFragment())
                R.id.movieFragment -> replaceFragment(MoviesRFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }
}
