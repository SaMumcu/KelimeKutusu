package com.sabihamumcu.kelimekutusu.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.sabihamumcu.kelimekutusu.model.Word;
import com.sabihamumcu.kelimekutusu.repo.WordRepository;

import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository wordRepository;
    private LiveData<List<Word>> allWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        wordRepository = new WordRepository(application);
        allWords=wordRepository.getWords();
    }

    public void insertWord(Word word) {
        wordRepository.insertWord(word);
    }

    public void updateWord(Word word) {
        wordRepository.update(word);
    }

    /*public LiveData<Word> getWord(String id) {
        return wordRepository(id);
    }*/

    public LiveData<List<Word>> getWords() {
        return wordRepository.getWords();
    }

    public void deleteWord(Word word){
        wordRepository.delete(word);
    }

    public Word findWord(String word){
        return wordRepository.find(word);
    }

}
