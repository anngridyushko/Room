package com.example.roomcodelab

import androidx.annotation.WorkerThread
import java.util.concurrent.Flow

class WordRepository(private val wordDAO: WordDAO) {

    val words = wordDAO.getOrderedWords()

    @WorkerThread
    suspend fun insert(word: Word) {
        wordDAO.insert(word)
    }
}