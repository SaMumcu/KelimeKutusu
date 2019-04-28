package com.sabihamumcu.kelimekutusu.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import com.sabihamumcu.kelimekutusu.model.Word;
import com.sabihamumcu.kelimekutusu.repo.localData.WordDao;
import com.sabihamumcu.kelimekutusu.repo.localData.db.AppDatabase;

import java.util.List;

public class WordRepository {

    private WordDao wordDao;
    private LiveData<List<Word>> allWords;

    public WordRepository(Application application){
        AppDatabase database=AppDatabase.getInstance(application);
        wordDao=database.wordDao();
        allWords=wordDao.loadAll();
    }

    public void insertWord(Word word) {

        new insertWordAsyncTask(wordDao).execute(word);
    }

    public void update(Word word){
        new updateWordAsyncTask(wordDao).execute(word);
    }

    public void delete(Word word){
        new deleteWordAsyncTask(wordDao).execute(word);
    }


    public Word find(String word){
        return wordDao.get(word);
    }

    /*public void insertWord(final Word word) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                database.wordDao().insert(word);
                return null;
            }
        }.execute();
    }

    public void deleteWord(final Word word) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                roomDatabase.wordDao().delete(word);
                return null;
            }
        }.execute();
    }*/

   /* public LiveData<Word> getWord(String id) {
        return roomDatabase.workDao().getWord(id);
    }*/

    public LiveData<List<Word>> getWords() {
        return allWords;
    }


    private static class insertWordAsyncTask extends AsyncTask<Word,Void,Void>{

        private WordDao mWordDao;

        public insertWordAsyncTask(WordDao wordDao) {
            mWordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mWordDao.insert(words[0]);
            return null;
        }
    }

    private static class updateWordAsyncTask extends AsyncTask<Word,Void,Void>{

        private WordDao mWordDao;

        public updateWordAsyncTask(WordDao wordDao) {
            mWordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mWordDao.update(words[0]);
            return null;
        }
    }

    private static class deleteWordAsyncTask extends AsyncTask<Word,Void,Void>{

        private WordDao mWordDao;

        public deleteWordAsyncTask(WordDao wordDao) {
            mWordDao = wordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mWordDao.delete(words[0]);
            return null;
        }
    }
}
