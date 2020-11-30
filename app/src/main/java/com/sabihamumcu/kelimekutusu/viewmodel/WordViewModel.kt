package com.sabihamumcu.kelimekutusu.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sabihamumcu.kelimekutusu.model.Word
import com.sabihamumcu.kelimekutusu.repo.WordRepository

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val wordRepository: WordRepository
    private val allWords: LiveData<List<Word?>?>?

    fun insertWord(word: Word?) {
        wordRepository.insertWord(word)
    }

    fun updateWord(word: Word?) {
        wordRepository.update(word)
    }

    val words: LiveData<List<Word?>?>?
        get() = wordRepository.words

    fun deleteWord(word: Word?) {
        wordRepository.delete(word)
    }

    fun findWord(word: String?): Word {
        return wordRepository.find(word)
    }

    init {
        wordRepository = WordRepository(application)
        allWords = wordRepository.words
    }
}