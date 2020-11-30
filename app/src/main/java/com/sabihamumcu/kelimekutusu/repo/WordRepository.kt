package com.sabihamumcu.kelimekutusu.repo

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.sabihamumcu.kelimekutusu.model.Word
import com.sabihamumcu.kelimekutusu.repo.localData.WordDao
import com.sabihamumcu.kelimekutusu.repo.localData.db.AppDatabase

class WordRepository(application: Application?) {

    private val wordDao: WordDao
    val words: LiveData<List<Word?>?>?

    fun insertWord(word: Word?) {
        insertWordAsyncTask(wordDao).execute(word)
    }

    fun update(word: Word?) {
        updateWordAsyncTask(wordDao).execute(word)
    }

    fun delete(word: Word?) {
        deleteWordAsyncTask(wordDao).execute(word)
    }

    fun find(word: String?): Word {
        return wordDao[word]!!
    }

    private class insertWordAsyncTask(private val mWordDao: WordDao) : AsyncTask<Word?, Void?, Void?>() {
        override fun doInBackground(vararg params: Word?): Void? {
            mWordDao.insert(params[0])
            return null
        }
    }

    private class updateWordAsyncTask(private val mWordDao: WordDao) : AsyncTask<Word?, Void?, Void?>() {
        override fun doInBackground(vararg params: Word?): Void? {
            mWordDao.update(params[0])
            return null
        }
    }

    private class deleteWordAsyncTask(private val mWordDao: WordDao) : AsyncTask<Word?, Void?, Void?>() {
        override fun doInBackground(vararg params: Word?): Void? {
            mWordDao.delete(params[0])
            return null
        }
    }

    init {
        val database = AppDatabase.getInstance(application!!.baseContext)
        wordDao = database!!.wordDao()
        words = wordDao.loadAll()
    }
}