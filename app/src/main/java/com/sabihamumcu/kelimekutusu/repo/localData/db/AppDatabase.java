package com.sabihamumcu.kelimekutusu.repo.localData.db;

import android.app.Application;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.sabihamumcu.kelimekutusu.model.Word;
import com.sabihamumcu.kelimekutusu.repo.localData.WordDao;

@Database(entities = {Word.class}, version = 3, exportSchema = false)
public abstract class AppDatabase  extends RoomDatabase {

    private static AppDatabase instance;

    public abstract WordDao wordDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,
                    "db_words")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void,Void,Void>{

        private WordDao mWordDao;

        public PopulateDBAsyncTask(AppDatabase database) {
            mWordDao = database.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
