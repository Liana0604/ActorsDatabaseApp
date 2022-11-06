package com.example.actorsdatabaseapp.ui.adapters

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.actorsdatabaseapp.MyApplication
import com.example.actorsdatabaseapp.databinding.FragmentMoviesBinding
import com.example.actorsdatabaseapp.data.models.ActorsMoviesModel
import com.example.actorsdatabaseapp.data.sqlite.AppSQLiteHelper
import com.example.actorsdatabaseapp.data.models.MoviesModel

class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private val sqLiteHelper: AppSQLiteHelper by lazy {
        (requireActivity().application as MyApplication).sqLiteHelper
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMoviesList()
    }

    private fun getMoviesList(): ArrayList<ActorsMoviesModel> {
        val moviesList: ArrayList<ActorsMoviesModel> = arrayListOf()
        if (getItemsList().size > 0) {
            moviesAdapter = MoviesAdapter { action, movie ->
                when (action) {
                    MoviesAdapter.ActionEnum.ACTION_DELETE -> {
                        deleteMovie(movie)
                    }
                }
            }
            moviesAdapter.movies = getItemsList()
            binding.moviesRecyclerView.visibility = View.VISIBLE
            binding.moviesRecyclerView.adapter = moviesAdapter
        } else {
            binding.moviesRecyclerView.visibility = View.GONE
        }
        return moviesList
    }

    private fun getItemsList(): ArrayList<ActorsMoviesModel> {
        return sqLiteHelper.getMovie()
    }

    private fun deleteMovie(movie: ActorsMoviesModel) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete Record")
        builder.setMessage("Are you sure you want to delete ${movie.movieId} ?")
        builder.setPositiveButton("Yes") { dialogInterface, _ ->
            val result = sqLiteHelper.deleteMovie(MoviesModel(movie.movieId, "", 0, 0))
            if (result > -1) {
                Toast.makeText(requireContext(), "Record deleted successfully.", Toast.LENGTH_LONG)
                    .show()
                getMoviesList()
            }
            dialogInterface.dismiss()
        }
        builder.setNegativeButton("No") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}