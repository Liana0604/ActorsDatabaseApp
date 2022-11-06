package com.example.actorsdatabaseapp.data.room.entities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.actorsdatabaseapp.data.room.ActorsDatabase
import com.example.actorsdatabaseapp.data.room.ActorsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    //   private val getActorsWithMoviesData : LiveData<List<ActorWithMovies>>
    private val repository: ActorsRepository

    init {
        val actorsDao = ActorsDatabase.getActorsDatabase(application).actorsDao()
        repository = ActorsRepository(actorsDao)
        //    getActorsWithMoviesData = repository.getActorsWithMoviesData
    }

    fun addMovie(movie: Movies) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovies(movie)
        }
    }
}