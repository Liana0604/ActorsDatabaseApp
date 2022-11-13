package com.example.actorsdatabaseapp.data.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.actorsdatabaseapp.data.sqlite.entities.ActorEntity
import com.example.actorsdatabaseapp.data.sqlite.entities.MovieEntity
import com.example.actorsdatabaseapp.data.sqlite.models.ActorsModel
import com.example.actorsdatabaseapp.data.sqlite.models.ActorsMoviesModel
import com.example.actorsdatabaseapp.data.sqlite.models.MoviesModel
import com.example.actorsdatabaseapp.data.sqlite.models.Pet

class AppSQLiteHelper(private val context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "actors_db"
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        db?.setForeignKeyConstraintsEnabled(true)
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(ActorEntity.CREATE_ACTORS_TABLE)
        db?.execSQL(MovieEntity.CREATE_MOVIES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(ActorEntity.DESTROY)
        db?.execSQL(MovieEntity.DESTROY)
        onCreate(db)
    }

    fun addActor(actor: ActorsModel): Long {
        var result = -1L
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ActorEntity.NAME, actor.name)
        contentValues.put(ActorEntity.SURNAME, actor.surname)
        contentValues.put(ActorEntity.AGE, actor.age)
        contentValues.put(ActorEntity.PET, actor.pets.toString())
        try {
            result = db.insert(ActorEntity.TABLE_NAME, null, contentValues)
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        } finally {
            db.close()
        }
        return result
    }

    fun addMovie(movie: MoviesModel): Long {
        var result = -1L
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MovieEntity.NAME, movie.name)
        contentValues.put(MovieEntity.IMDBRATE, movie.imdbRate)
        contentValues.put(MovieEntity.ACTOR_ID, movie.actorId)
        try {
            result = db.insert(MovieEntity.TABLE_NAME, null, contentValues)
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        } finally {
            db.close()
        }
        return result
    }


    fun getActors(): ArrayList<ActorsModel> {
        val actorList: ArrayList<ActorsModel> = ArrayList()
        val selectQuery = "SELECT * FROM ${ActorEntity.TABLE_NAME}"
        val db = this.readableDatabase

        val cursor = db.rawQuery(selectQuery, null)
        cursor?.let {
            if (cursor.columnCount > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        val id = cursor.getInt((cursor.getColumnIndexOrThrow(ActorEntity.ID)))
                        val name = cursor.getString(cursor.getColumnIndexOrThrow(ActorEntity.NAME))
                        val surname =
                            cursor.getString(cursor.getColumnIndexOrThrow(ActorEntity.SURNAME))
                        val age = cursor.getInt(cursor.getColumnIndexOrThrow(ActorEntity.AGE))
                        val pet = cursor.getString(cursor.getColumnIndexOrThrow(ActorEntity.PET))
                        actorList.add(
                            ActorsModel(
                                id, name, surname, age, arrayListOf(Pet(pet, age, true))
                            )
                        )
                    } while (cursor.moveToNext())
                }
            }
            cursor.close()
            db.close()
        }
        return actorList
    }

    fun getMovie(): ArrayList<ActorsMoviesModel> {
        val moviesList = ArrayList<ActorsMoviesModel>()
        val db = this.readableDatabase

        val selectQuery =
            "SELECT * FROM ${MovieEntity.TABLE_NAME} AS T1 JOIN ${ActorEntity.TABLE_NAME} AS T2 ON T1.${MovieEntity.ACTOR_ID} = T2.${ActorEntity.ID} ORDER BY T1.${MovieEntity.ACTOR_ID} DESC"

        val cursor = db.rawQuery(selectQuery, null)
        cursor?.let {
            if (cursor.columnCount > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        val actorId =
                            cursor.getInt(cursor.getColumnIndexOrThrow(MovieEntity.ACTOR_ID))
                        val movieId = cursor.getInt((cursor.getColumnIndexOrThrow(MovieEntity.ID)))
                        val actorName =
                            cursor.getString(cursor.getColumnIndexOrThrow(ActorEntity.NAME))
                        val movieName =
                            cursor.getString(cursor.getColumnIndexOrThrow(MovieEntity.NAME))
                        val imdbRate =
                            cursor.getInt(cursor.getColumnIndexOrThrow(MovieEntity.IMDBRATE))


                        moviesList.add(
                            ActorsMoviesModel(
                                actorId,
                                movieId,
                                actorName,
                                movieName,
                                imdbRate
                            )
                        )
                    } while (cursor.moveToNext())
                }
            }
            cursor.close()
            db.close()
        }
        return moviesList
    }

    fun deleteactor(actor: ActorsModel): Int {
        val db = this.writableDatabase
        val whereClause = "${ActorEntity.ID} = ?"
        val whereArgs = arrayOf("${actor.id}")
        val result = db.delete(ActorEntity.TABLE_NAME, whereClause, whereArgs)
        db.close()
        return result

    }

    fun deleteMovie(movie: MoviesModel): Int {
        val db = this.writableDatabase
        val whereClause = "${MovieEntity.ID} = ?"
        val whereArgs = arrayOf("${movie.id}")
        val result = db.delete(MovieEntity.TABLE_NAME, whereClause, whereArgs)
        db.close()
        return result
    }
}
