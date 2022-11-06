package com.example.actorsdatabaseapp.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.actorsdatabaseapp.data.room.entities.Actors
import com.example.actorsdatabaseapp.data.room.entities.Movies

@Database(entities = [Actors::class, Movies::class], version = 1, exportSchema = false)
abstract class ActorsDatabase : RoomDatabase() {

    abstract fun actorsDao(): ActorsDao

    companion object {
        @Volatile
        private var INSTANCE: ActorsDatabase? = null

        fun getActorsDatabase(context: Context): ActorsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ActorsDatabase::class.java,
                    "actors_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}