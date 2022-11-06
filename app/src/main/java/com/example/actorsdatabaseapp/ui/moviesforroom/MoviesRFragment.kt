package com.example.actorsdatabaseapp.ui.moviesforroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.actorsdatabaseapp.databinding.FragmentMoviesRBinding

class MoviesRFragment : Fragment() {
    private lateinit var binding: FragmentMoviesRBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesRBinding.inflate(layoutInflater)
        return binding.root
    }
}