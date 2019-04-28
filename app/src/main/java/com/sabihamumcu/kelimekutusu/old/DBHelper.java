package com.sabihamumcu.kelimekutusu.old;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sabis on 2/20/2017.
 */
//before room
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "words.db";
    public static final String WORDS_TABLE_NAME = "words";
    public static final String WORDS_COLUMN_ID = "id";
    public static final String WORDS_COLUMN_WORD = "word";
    public static final String WORDS_COLUMN_DESCRIPTION = "description";

    private HashMap<String, String> hm;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table words " +
                        "(id integer primary key, word text,description text)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(sqLiteDatabase);
    }


    public boolean insertWords(String word, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("description", description);
        db.insert("words", null, contentValues);
        return true;
    }

    public boolean deleteWord(String s) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Cursor cursor=db.rawQuery("select * from words where word="+s,null);
        //db.delete(WORDS_TABLE_NAME,WORDS_COLUMN_ID+" =?",new String[]{String.valueOf(s)});
        db.delete(WORDS_TABLE_NAME, WORDS_COLUMN_WORD + " =?", new String[]{String.valueOf(s)});
        db.close();
        return true;
    }


    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from words where id=" + id + "", null);
        return res;
    }

    public boolean updateWords(Integer id, String word, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("word", word);
        contentValues.put("description", description);
        db.update("words", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public HashMap<String, String> getAllWords() {
        hm = new HashMap<String, String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from words", null);
        res.moveToFirst();
        while (res.isAfterLast() == false) {
            hm.put(res.getString(res.getColumnIndex(WORDS_COLUMN_WORD)), res.getString(res.getColumnIndex(WORDS_COLUMN_DESCRIPTION)));
            res.moveToNext();
        }

        return hm;
    }
}
