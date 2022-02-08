package com.example.roomcodelab.data.repositories

import androidx.annotation.WorkerThread
import com.example.roomcodelab.data.db.WordDAO
import com.example.roomcodelab.data.db.Word

class WordRepository(private val wordDAO: WordDAO) {

    val words = wordDAO.getOrderedWords()

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDAO.insert(word)
    }
}