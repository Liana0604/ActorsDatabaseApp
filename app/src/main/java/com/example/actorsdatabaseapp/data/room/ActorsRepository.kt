package com.example.actorsdatabaseapp.data.room

import androidx.lifecycle.LiveData
import com.example.actorsdatabaseapp.data.room.entities.ActorWithMovies
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.data.room.entities.Movies

class ActorsRepository(private val actorsDao: ActorsDao) {

    val getActorsData: LiveData<List<Actors>> = actorsDao.getActorsData()
    // val getActorsWithMoviesData : LiveData<List<ActorWithMovies>> = actorsDao.getActorsWithMoviesData(actorName =)

    suspend fun addActor(actor: Actors) {
        actorsDao.addActor(actor)
    }

    suspend fun addMovies(movie: Movies) {
        actorsDao.addMovie(movie)
    }
}