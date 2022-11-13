package com.example.actorsdatabaseapp.data.room

import androidx.lifecycle.LiveData
import com.example.actorsdatabaseapp.data.room.entities.ActorWithMovies
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.data.room.entities.Movies

class ActorsRepository(private val actorsDao: ActorsDao) {

    val getActorsData: LiveData<MutableList<Actors>> = actorsDao.getActorsData()
    val getActorsWithMoviesData: LiveData<MutableList<ActorWithMovies>> =
        actorsDao.getActorsWithMoviesData()

    suspend fun addActor(actor: Actors) {
        actorsDao.addActor(actor)
    }

    suspend fun addMovies(movie: Movies) {
        actorsDao.addMovie(movie)
    }

    suspend fun deleteActor(actor: Actors) {
        actorsDao.deleteActor(actor)
    }

    suspend fun deleteMovie(movie: Movies) {
        actorsDao.deleteMovie(movie)
    }

}