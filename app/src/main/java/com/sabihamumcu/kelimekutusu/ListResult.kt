package com.sabihamumcu.kelimekutusu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sabihamumcu.kelimekutusu.adapter.WordAdapter
import com.sabihamumcu.kelimekutusu.model.Word
import com.sabihamumcu.kelimekutusu.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_list_result.*
import java.util.*

class ListResult : AppCompatActivity() {

    private var wordViewModel: WordViewModel? = null
    var wordAdapter: WordAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_result)

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)
        val your_array_list: MutableList<String> = ArrayList()
        val wordList: MutableList<Word> = ArrayList()
        wordViewModel!!.words?.observe(this, Observer { words ->
            for (i in words!!.indices) {
                your_array_list.add(words[i]?.word + "\t\t\t" + words[i]?.description)
                wordList.add(words[i]!!)
            }
            wordAdapter = WordAdapter(wordList, this@ListResult, wordViewModel!!)
            layoutManager = LinearLayoutManager(applicationContext)
            word_list.setLayoutManager(layoutManager)
            word_list.setAdapter(wordAdapter)
        })
        inputSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                wordAdapter!!.filter.filter(charSequence)
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }
}