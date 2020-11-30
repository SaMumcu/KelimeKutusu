package com.sabihamumcu.kelimekutusu

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sabihamumcu.kelimekutusu.Send
import com.sabihamumcu.kelimekutusu.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_send.*
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.*

class Send : AppCompatActivity() {

    var gonderilecekListe = ArrayList<String>()

    private var wordViewModel: WordViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send)
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        anaEkranaDon!!.setOnClickListener {
            val intent = Intent(this@Send, MainActivity::class.java)
            startActivity(intent)
        }
        val path = getDatabasePath("db_words.db")
        wordViewModel!!.words?.observe(this, Observer { words ->
            for (i in words!!.indices) {
                gonderilecekListe.add(words[i]?.word + " " + words[i]?.description)
            }
            if (gonderilecekListe.isEmpty()) {
                Toast.makeText(this@Send, "You have an empty word box.", Toast.LENGTH_SHORT).show()
            }
        })
        try {
            val fileName = "output.txt"
            val root = File(Environment.getExternalStorageDirectory(), "testDir")
            if (!root.exists()) {
                root.mkdirs()
            }
            val gpxfile = File(root, fileName)
            val writer = FileWriter(gpxfile)
            for (i in gonderilecekListe.indices) {
                writer.write(gonderilecekListe[i])
                writer.write("\n")
            }
            writer.flush()
            writer.close()
            sendEmail(gpxfile)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    protected fun sendEmail(file: File?) {
        val path = Uri.fromFile(file)
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_SUBJECT, "Kelime Kutusu")
        i.putExtra(Intent.EXTRA_TEXT, "Kelime Kutunuz")
        i.putExtra(Intent.EXTRA_STREAM, path) // Include the path
        try {
            startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }
}