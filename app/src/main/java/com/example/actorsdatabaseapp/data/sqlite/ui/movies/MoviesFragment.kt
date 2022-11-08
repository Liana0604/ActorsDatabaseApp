package com.example.actorsdatabaseapp.data.sqlite.ui.movies

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.actorsdatabaseapp.MyApplication
import com.example.actorsdatabaseapp.data.sqlite.AppSQLiteHelper
import com.example.actorsdatabaseapp.data.sqlite.models.ActorsMoviesModel
import com.example.actorsdatabaseapp.data.sqlite.models.MoviesModel
import com.example.actorsdatabaseapp.data.sqlite.ui.adapters.MoviesAdapter
import com.example.actorsdatabaseapp.databinding.FragmentMoviesBinding

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
        setUpListOfMoviesIntoRecyclerView()
    }

    private fun setUpListOfMoviesIntoRecyclerView() {
        if (getItemsList().size > 0) {
            moviesAdapter = MoviesAdapter { action, movie ->
                when (action) {
                    MoviesAdapter.ActionEnum.ACTION_DELETE -> {
                        deleteMovie(movie)
                    }
                }
                moviesAdapter.updateData(getItemsList())
            }
            moviesAdapter.movies = getItemsList()
            binding.moviesRecyclerView.visibility = View.VISIBLE
            binding.moviesRecyclerView.adapter = moviesAdapter
        } else {
            binding.moviesRecyclerView.visibility = View.GONE
        }
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
                setUpListOfMoviesIntoRecyclerView()
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