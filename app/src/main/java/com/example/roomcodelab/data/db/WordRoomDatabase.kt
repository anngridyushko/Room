package com.example.roomcodelab.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase: RoomDatabase() {

    abstract fun wordDAO(): WordDAO

    companion object {

        @Volatile
        private var DB_INSTANCE: WordRoomDatabase? = null

        fun getDB(context: Context, scope: CoroutineScope): WordRoomDatabase {

            return DB_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                ).addCallback(WordDBCallback(scope))
                    .build()
                DB_INSTANCE = instance
                instance
            }
        }

    }

    private class WordDBCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        suspend fun populateDatabase(wordDao: WordDAO) {

            wordDao.deleteAll()

            var word = Word("Hello")
            wordDao.insert(word)
            word = Word("World")
            wordDao.insert(word)

        }

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            DB_INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.wordDAO())
                }
            }
        }

    }
}