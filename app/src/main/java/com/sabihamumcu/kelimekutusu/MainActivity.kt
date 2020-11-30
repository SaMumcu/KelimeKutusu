package com.sabihamumcu.kelimekutusu

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.sabihamumcu.kelimekutusu.MainActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun addWord(view: View?) {
        val intent = Intent(this@MainActivity, AddWord::class.java)
        startActivity(intent)
    }

    fun testing(view: View?) {
        val intent = Intent(this@MainActivity, Testing::class.java)
        startActivity(intent)
    }

    fun list(view: View?) {
        val intent = Intent(this@MainActivity, ListResult::class.java)
        startActivity(intent)
    }

    fun send(view: View?) {
        val intent = Intent(this@MainActivity, Send::class.java)
        startActivity(intent)
    }
}