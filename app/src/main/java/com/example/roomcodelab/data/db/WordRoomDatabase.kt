package com.example.roomcodelab.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase: RoomDatabase() {

    abstract fun wordDAO(): WordDAO

    companion object {

        @Volatile
        private var DB_INSTANCE: WordRoomDatabase? = null

        fun getDB(context: Context): WordRoomDatabase {

            return DB_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).build()
                DB_INSTANCE = instance
                instance
            }
        }

    }
}