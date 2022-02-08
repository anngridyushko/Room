package com.example.roomcodelab.ui

import android.app.Application
import com.example.roomcodelab.data.db.WordRoomDatabase
import com.example.roomcodelab.data.repositories.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { WordRoomDatabase.getDB(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDAO()) }
}