package com.sabihamumcu.kelimekutusu

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.sabihamumcu.kelimekutusu.model.Word
import com.sabihamumcu.kelimekutusu.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_kelime_ekle.*

class AddWord : AppCompatActivity() {

    private var wordViewModel: WordViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kelime_ekle)
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
    }

    fun addWord(view: View) {
        if (isEmpty(word) || isEmpty(wordDescription)) {
            Toast.makeText(this, getString(R.string.please_fill_blanks), Toast.LENGTH_SHORT).show()
        } else {
            if (word.text.toString() != null && wordDescription.text.toString() != null) {
                val snackbar = Snackbar.make(activity_kelime_ekle!!, getString(R.string.word_and_description_added_successfully), Snackbar.LENGTH_INDEFINITE)
                snackbar.show()
                val word = Word(word.text.toString(), wordDescription.text.toString())
                wordViewModel!!.insertWord(word)
            } else {
                val snackbar = Snackbar.make(activity_kelime_ekle!!, "", Snackbar.LENGTH_INDEFINITE)
                snackbar.show()
            }
            word!!.setText("")
            wordDescription!!.setText("")
        }
    }

    private fun isEmpty(editText: EditText?): Boolean {
        return editText!!.text.toString().trim { it <= ' ' }.length == 0
    }
}