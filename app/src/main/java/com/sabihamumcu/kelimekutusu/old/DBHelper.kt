package com.sabihamumcu.kelimekutusu.old

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.*

/**
 * Created by sabis on 2/20/2017.
 */
//before room
class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    private var hm: HashMap<String, String>? = null
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table words " +
                        "(id integer primary key, word text,description text)"
        )
    }

    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts")
        onCreate(sqLiteDatabase)
    }

    fun insertWords(word: String?, description: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("word", word)
        contentValues.put("description", description)
        db.insert("words", null, contentValues)
        return true
    }

    fun deleteWord(s: String): Boolean {
        val db = this.writableDatabase
        //Cursor cursor=db.rawQuery("select * from words where word="+s,null);
        //db.delete(WORDS_TABLE_NAME,WORDS_COLUMN_ID+" =?",new String[]{String.valueOf(s)});
        db.delete(WORDS_TABLE_NAME, "$WORDS_COLUMN_WORD =?", arrayOf(s))
        db.close()
        return true
    }

    fun getData(id: Int): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("select * from words where id=$id", null)
    }

    fun updateWords(id: Int?, word: String?, description: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("word", word)
        contentValues.put("description", description)
        db.update("words", contentValues, "id = ? ", arrayOf(Integer.toString(id!!)))
        return true
    }

    val allWords: HashMap<String, String>
        get() {
            hm = HashMap()
            val db = this.readableDatabase
            val res = db.rawQuery("select * from words", null)
            res.moveToFirst()
            while (res.isAfterLast == false) {
                hm!![res.getString(res.getColumnIndex(WORDS_COLUMN_WORD))] = res.getString(res.getColumnIndex(WORDS_COLUMN_DESCRIPTION))
                res.moveToNext()
            }
            return hm!!
        }

    companion object {
        const val DATABASE_NAME = "words.db"
        const val WORDS_TABLE_NAME = "words"
        const val WORDS_COLUMN_ID = "id"
        const val WORDS_COLUMN_WORD = "word"
        const val WORDS_COLUMN_DESCRIPTION = "description"
    }
}