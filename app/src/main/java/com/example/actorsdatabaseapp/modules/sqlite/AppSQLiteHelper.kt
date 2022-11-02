package com.example.actorsdatabaseapp.modules.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class AppSQLiteHelper(val context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "actors_db"
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

        try {
            val contentValues = ContentValues()
            contentValues.put(ActorEntity.ID, actor.id)
            contentValues.put(ActorEntity.NAME, actor.name)
            contentValues.put(ActorEntity.SURNAME, actor.surname)
            contentValues.put(ActorEntity.AGE, actor.age)
            contentValues.put(ActorEntity.MOVIE_ID, actor.movieId)
            //   contentValues.put(AppSQLiteContract.ActorsTable.COLUMN_PET, actor.pets.toString())

            result = db.insert(ActorEntity.TABLE_NAME, null, contentValues)
            db.close()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
        return result
    }

    fun addMovie(movie: MoviesModel): Long {
        var result = -1L
        val db = this.writableDatabase
        try {
            val contentValues = ContentValues()
            contentValues.put(MovieEntity.ID, movie.id)
            contentValues.put(MovieEntity.NAME, movie.name)
            contentValues.put(MovieEntity.IMDBRATE, movie.imdbRate)

            result = db.insert(MovieEntity.TABLE_NAME, null, contentValues)
            db.close()
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
        }
        return result
    }

    fun updateActor(actor: ActorsModel): Int {
        var result = -1
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ActorEntity.ID, actor.id)
        contentValues.put(ActorEntity.NAME, actor.name)
        contentValues.put(ActorEntity.SURNAME, actor.surname)
        contentValues.put(ActorEntity.AGE, actor.age)
        contentValues.put(ActorEntity.MOVIE_ID, actor.movieId)
        val whereClause = "${ActorEntity.ID} = ?"
        val whereArgs = arrayOf("${actor.id}")
        result = db.update(ActorEntity.TABLE_NAME, contentValues, whereClause, whereArgs)

        db.close()
        return result
    }

    fun updateMovie(movie: MoviesModel): Int {
        var result = -1
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(MovieEntity.ID, movie.id)
        contentValues.put(MovieEntity.NAME, movie.name)
        contentValues.put(MovieEntity.IMDBRATE, movie.imdbRate)
        val whereClause = "${MovieEntity.ID} = ?"
        val whereArgs = arrayOf("${movie.id}")
        result = db.update(MovieEntity.TABLE_NAME, contentValues, whereClause, whereArgs)

        db.close()
        return result
    }

    fun getActors(): ArrayList<ActorsModel> {
        val actorList: ArrayList<ActorsModel> = ArrayList()
        val selectQuery = "SELECT * FROM ${ActorEntity.TABLE_NAME} ORDER BY ${ActorEntity.ID} DESC"
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
                        val movieId =
                            cursor.getInt(cursor.getColumnIndexOrThrow(ActorEntity.MOVIE_ID))
//                pets = cursor.getString(cursor.getColumnIndexOrThrow(AppSQLiteContract.ActorsTable.COLUMN_PET))
                        actorList.add(
                            ActorsModel(
                                id = id,
                                name = name,
                                surname = surname,
                                age = age,
                                movieId = movieId
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

    fun getMovie(): ArrayList<MoviesModel> {
        val moviesList: ArrayList<MoviesModel> = ArrayList()
        val selectQuery = "SELECT * FROM ${MovieEntity.TABLE_NAME}"
        val db = this.readableDatabase

        val cursor = db.rawQuery(selectQuery, null)
        cursor?.let {
            if (cursor.columnCount > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        val id = cursor.getInt((cursor.getColumnIndexOrThrow(MovieEntity.ID)))
                        val name = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntity.NAME))
                        val imdbRate =
                            cursor.getFloat(cursor.getColumnIndexOrThrow(MovieEntity.IMDBRATE))

                        moviesList.add(MoviesModel(id = id, name = name, imdbRate = imdbRate))
                    } while (cursor.moveToNext())
                }
            }
            cursor.close()
            db.close()
        }
        return moviesList
    }

    fun deleteactor(actor: ActorsModel): Int {
        var result = -1
        val db = this.writableDatabase
        // val contentValues = ContentValues()
        // contentValues.put(ActorEntity.ID,actor.id)
        val whereClause = "${ActorEntity.ID} = ?"
        val whereArgs = arrayOf("${actor.id}")
        result = db.delete(ActorEntity.TABLE_NAME, whereClause, whereArgs)
        db.close()
        return result

    }

    fun deleteMovie(movie: MoviesModel): Int {
        var result = -1
        val db = this.writableDatabase
//        val contentValues = ContentValues()
//        contentValues.put(ActorEntity.ID,movie.id)
        val whereClause = "${MovieEntity.ID} = ?"
        val whereArgs = arrayOf("${movie.id}")
        result = db.delete(MovieEntity.TABLE_NAME, whereClause, whereArgs)
        db.close()
        return result
    }
}

//, FOREIGN KEY ($MOVIE_ID) REFERENCES ${MovieEntity.TABLE_NAME}({${MovieEntity.ID}) ON DELETE CASCADE