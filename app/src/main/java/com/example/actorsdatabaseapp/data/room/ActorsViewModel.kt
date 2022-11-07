package com.example.actorsdatabaseapp.data.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.actorsdatabaseapp.data.room.entities.Actors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActorsViewModel(application: Application) : AndroidViewModel(application) {

    var getActorsData: LiveData<MutableList<Actors>>
    private val repository: ActorsRepository

    init {
        val actorsDao = ActorsDataBase.getActorsDatabase(application).actorsDao()
        repository = ActorsRepository(actorsDao)
        getActorsData = repository.getActorsData
    }


    fun addActor(actor: Actors) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addActor(actor)
        }
    }

    fun deleteActor(actor: Actors) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteActor(actor)
        }
    }

    fun deleteAllActor() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllActors()
        }
    }
}