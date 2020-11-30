package com.sabihamumcu.kelimekutusu.repo.localData

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sabihamumcu.kelimekutusu.model.Word

@Dao
interface WordDao {
    @Insert
    fun insert(word: Word?)

    @Update
    fun update(word: Word?)

    @Delete
    fun delete(word: Word?)

    @Query("Select * FROM word")
    fun loadAll(): LiveData<List<Word?>?>?

    @Query("Select word FROM word where id =:id")
    fun getWordTitle(id: String?): String?

    @Query("Select * FROM word where id =:id")
    fun getWord(id: String?): LiveData<Word?>?

    @Query("Select * from word where word=:word")
    operator fun get(word: String?): Word?
}