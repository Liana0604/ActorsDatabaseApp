package com.example.actorsdatabaseapp.data.room.ui.moviesforroom

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.actorsdatabaseapp.data.room.MoviesViewModel
import com.example.actorsdatabaseapp.data.room.entities.Movies
import com.example.actorsdatabaseapp.data.room.ui.adapter.MoviesRAdapter
import com.example.actorsdatabaseapp.databinding.FragmentMoviesRBinding

class MoviesRFragment : Fragment() {
    private lateinit var binding: FragmentMoviesRBinding
    private lateinit var moviesViewModel: MoviesViewModel
    private lateinit var movieAdapter: MoviesRAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesRBinding.inflate(inflater, container, false)
        moviesViewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MoviesRAdapter() { action, movie ->
            when (action) {
                MoviesRAdapter.ActionEnum.DELETE -> {
                    deleteMovie(movie)
                }
            }
        }
        moviesViewModel.getActorsWithMoviesData.observe(viewLifecycleOwner) { movie ->
            movieAdapter.updateData(movie.flatMap { aa ->
                mutableListOf<Movies>().also {
                    it.addAll(
                        aa.movies
                    )
                }
            } as MutableList<Movies>)
        }
        binding.moviesRecyclerView.adapter = movieAdapter
    }

    private fun deleteMovie(movies: Movies) {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure you want to delete this actor")
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            moviesViewModel.deleteMovie(movies)
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        builder.create().show()
    }
}
