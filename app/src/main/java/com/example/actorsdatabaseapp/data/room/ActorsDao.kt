package com.example.actorsdatabaseapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.actorsdatabaseapp.data.room.entities.ActorWithMovies
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.data.room.entities.Movies

@Dao
interface ActorsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addActor(actor: Actors)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovie(movie: Movies)

    @Query("SELECT * FROM actors_table")
    fun getActorsData(): LiveData<List<Actors>>

    @Transaction
    @Query("SELECT * FROM actors_table WHERE name = :actorName")
    fun getActorsWithMoviesData(actorName: String): LiveData<List<ActorWithMovies>>

}