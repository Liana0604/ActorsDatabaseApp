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

    @Query("SELECT * FROM actors_table ORDER BY id ASC")
    fun getActorsData(): LiveData<MutableList<Actors>>

    @Query("SELECT * FROM movies_table ORDER BY id ASC")
    fun getMoviessData(): LiveData<MutableList<Movies>>

    @Delete
    suspend fun deleteActor(actor: Actors)

    @Delete
    suspend fun deleteMovie(movie: Movies)

    @Query("DELETE FROM actors_table")
    suspend fun deleteAllActors()

    @Query("DELETE FROM movies_table")
    suspend fun deleteAllMovies()

    @Transaction
    @Query("SELECT * FROM actors_table")
    fun getActorsWithMoviesData(): LiveData<MutableList<ActorWithMovies>>

}