package com.sabihamumcu.kelimekutusu.repo.localData.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sabihamumcu.kelimekutusu.model.Word
import com.sabihamumcu.kelimekutusu.repo.localData.WordDao

@Database(entities = [Word::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "db_words")
                        .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
