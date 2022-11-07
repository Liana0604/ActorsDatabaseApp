package com.example.actorsdatabaseapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.actorsdatabaseapp.data.room.entities.ActorWithMovies
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.data.room.entities.Movies

@Dao
interface ActorsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addActor(actor: Actors)

    //
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovie(movie: Movies)

    @Query("SELECT * FROM actors_table ORDER BY id ASC")
    fun getActorsData(): LiveData<MutableList<Actors>>

    @Query("SELECT * FROM movies_table ORDER BY id ASC")
    fun getMoviessData(): LiveData<MutableList<Movies>>

    @Delete
    fun deleteActor(actor: Actors)

    @Delete
    fun deleteMovie(movie: Movies)

    @Query("DELETE FROM actors_table")
    fun deleteAllActors()

    @Query("DELETE FROM movies_table")
    fun deleteAllMovies()

    @Transaction
    @Query("SELECT * FROM actors_table")
    fun getActorsWithMoviesData(): LiveData<MutableList<ActorWithMovies>>

}

//WHERE name = :actorName