package com.example.actorsdatabaseapp.data.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.actorsdatabaseapp.data.room.entities.ActorWithMovies
import com.example.actorsdatabaseapp.data.room.entities.Movies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    val getActorsWithMoviesData: LiveData<MutableList<ActorWithMovies>>
    private val repository: ActorsRepository

    init {
        val actorsDao = ActorsDataBase.getActorsDatabase(application).actorsDao()
        repository = ActorsRepository(actorsDao)
        getActorsWithMoviesData = repository.getActorsWithMoviesData
    }

    fun addMovie(movie: Movies) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovies(movie)
        }
    }

    fun deleteMovie(movie: Movies) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(movie)
        }
    }

    fun deleteAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllMovies()
        }
    }
}