package com.example.actorsdatabaseapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.actorsdatabaseapp.R
import com.example.actorsdatabaseapp.databinding.FragmentActorsBinding
import com.example.actorsdatabaseapp.databinding.FragmentMoviesBinding
import com.example.actorsdatabaseapp.modules.sqlite.ActorsModel
import com.example.actorsdatabaseapp.modules.sqlite.AppSQLiteHelper
import com.example.actorsdatabaseapp.modules.sqlite.MoviesModel


class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addMovieButton.setOnClickListener { view ->
            addMovie(view)
        }
    }

    fun addMovie(view: View) {
        val movieName = binding.movieNameEditText.toString()
        val imdbRate = binding.imdbRateEditTextEditText.text.toString()
        val sqLiteHelper: AppSQLiteHelper = AppSQLiteHelper(requireActivity())
        if (movieName.isNotEmpty() && imdbRate.isNotEmpty()) {
            val status = sqLiteHelper.addMovie(MoviesModel(0, movieName, imdbRate.toFloat()))
            if (status > -1) {
                Toast.makeText(requireContext(), "Record is saved", Toast.LENGTH_LONG).show()
                binding.movieNameEditText.text.clear()
                binding.imdbRateEditTextEditText.text.clear()
            }
        } else {
            Toast.makeText(requireContext(), "Name or imdbrate cannot be blank", Toast.LENGTH_LONG)
                .show()

        }
    }
}