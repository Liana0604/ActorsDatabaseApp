package com.example.actorsdatabaseapp.data.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.actorsdatabaseapp.data.room.entities.ActorWithMovies
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.data.room.entities.Movies
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActorsViewModel(application: Application) : AndroidViewModel(application) {

    private val getActorsData: LiveData<List<Actors>>
    private val repository: ActorsRepository

    init {
        val actorsDao = ActorsDatabase.getActorsDatabase(application).actorsDao()
        repository = ActorsRepository(actorsDao)
        getActorsData = repository.getActorsData
    }

    fun addActor(actor: Actors) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addActor(actor)
        }
    }
}