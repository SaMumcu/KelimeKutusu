package com.sabihamumcu.kelimekutusu.repo.localData;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sabihamumcu.kelimekutusu.model.Word;

import java.util.List;

@Dao
public interface WordDao {

    @Insert
    void insert(Word word);

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);

    @Query("Select * FROM word")
    LiveData<List<Word>> loadAll();

    @Query("Select word FROM word where id =:id")
    String getWordTitle(String id);

    @Query("Select * FROM word where id =:id")
    LiveData<Word> getWord(String id);

    @Query("Select * from word where word=:word")
    Word get(String word);

}
