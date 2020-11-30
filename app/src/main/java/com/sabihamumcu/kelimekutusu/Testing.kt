package com.sabihamumcu.kelimekutusu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.sabihamumcu.kelimekutusu.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_testing.*
import java.util.*

class Testing : AppCompatActivity() {

    var rand = Random()
    var your_array_list: MutableList<String>? = null
    private var wordViewModel: WordViewModel? = null
    var repeatedWordControlList: MutableList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_testing)

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel::class.java)

        wordViewModel!!.words?.observe(this, Observer { words ->
            your_array_list = ArrayList()
            val wordList: MutableList<String> = ArrayList()
            val descriptionList: MutableList<String> = ArrayList()
            for (i in words!!.indices) {
                (your_array_list as ArrayList<String>).add(words[i]?.word + " " + words[i]?.description)
                wordList.add(words[i]?.word.toString())
                descriptionList.add(words[i]?.description.toString())
            }

            val wrongAnswers = ArrayList<String>()
            if ((your_array_list as ArrayList<String>).isEmpty()) {
                val snackbar = Snackbar.make(activity_test_et, getString(R.string.you_have_an_empty_word_box), Snackbar.LENGTH_INDEFINITE)
                snackbar.show()
            } else if ((your_array_list as ArrayList<String>).size < 5) {
                val snackbar = Snackbar.make(activity_test_et, getString(R.string.you_must_have_atleast_5_words_to_test_yourself), Snackbar.LENGTH_INDEFINITE)
                snackbar.show()
            } else {
                val h = Handler()
                val buttons: MutableList<Button?> = ArrayList()
                val indexes: MutableList<Int> = ArrayList()
                val forAnswers: MutableList<Int> = ArrayList()
                buttons.add(f)
                buttons.add(s)
                buttons.add(t)
                buttons.add(fo)
                val ap = ilkAtama(buttons, descriptionList, indexes, forAnswers, wordList, repeatedWordControlList)
                Log.v(TAG, "Kaldirilan kelime=" + wordList.size)
                ikinciAtama(descriptionList, buttons, forAnswers, ap[0], indexes)
                for (i in 0..3) {
                    buttons[i]!!.setOnClickListener { view ->
                        if (view === buttons[indexes[0]]) {
                            Log.v(TAG, "verilen cevap indeksi=" + view.id)
                            val snackbar = Snackbar.make(activity_test_et, "Dogru bildiniz.", Snackbar.LENGTH_INDEFINITE)
                            snackbar.show()
                            //view.setBackgroundColor(Color.GREEN);
                            //cevap.setBackgroundColor(Color.GREEN);
                            forAnswers.clear()
                            indexes.clear()
                            //view.setBackgroundResource(android.R.drawable.btn_default);
                            Log.v(TAG, "indexes büyüklüğü=" + indexes.size)
                            if (repeatedWordControlList.size == descriptionList.size) {
                                val snackbar2 = Snackbar.make(activity_test_et, "Testi bitirdiniz.", Snackbar.LENGTH_INDEFINITE)
                                snackbar2.show()
                                h.postDelayed({
                                    val i = Intent(this@Testing, Result::class.java)
                                    i.putExtra("list", wrongAnswers)
                                    startActivity(i)
                                }, 1000)
                            } else {
                                val ap = ilkAtama(buttons, descriptionList, indexes, forAnswers, wordList, repeatedWordControlList)
                                //kelime.remove(ap[1]);
                                Log.v(TAG, "Kaldirilan kelime=" + wordList.size)
                                ikinciAtama(descriptionList, buttons, forAnswers, ap[0], indexes)
                                Log.v(TAG, "indexes büyüklüğü" + indexes.size)
                            }
                        } else {
                            Log.v(TAG, "verilen cevap indeksi=" + view.id)
                            //view.setBackgroundColor(Color.RED);
                            wrongAnswers.add(testKelimesi!!.text.toString())
                            forAnswers.clear()
                            indexes.clear()
                            Log.v(TAG, "indexes büyüklüğü=" + indexes.size)
                            if (repeatedWordControlList.size == descriptionList.size) {
                                val snackbar2 = Snackbar.make(activity_test_et, "Testi bitirdiniz.", Snackbar.LENGTH_INDEFINITE)
                                snackbar2.show()
                                h.postDelayed({
                                    val i = Intent(this@Testing, Result::class.java)
                                    i.putExtra("list", wrongAnswers)
                                    startActivity(i)
                                }, 1000)
                            } else {
                                val ap = ilkAtama(buttons, descriptionList, indexes, forAnswers, wordList, repeatedWordControlList)

                                //kelime.remove(ap[1]);
                                Log.v(TAG, "Kaldirilan kelime=" + wordList.size)
                                ikinciAtama(descriptionList, buttons, forAnswers, ap[0], indexes)
                                Log.v(TAG, "indexes büyüklüğü" + indexes.size)
                            }
                        }
                    }
                }
            }
        })
    }

    fun butonuSetEt(tanimi: List<String>, buttons: List<Button?>, i: Int, forAnswers: MutableList<Int>, indexes: MutableList<Int>) {
        var t = rand.nextInt(tanimi.size)
        while (listedeAra(t, forAnswers)) {
            t = rand.nextInt(tanimi.size)
        }
        forAnswers.add(t)
        indexes.add(i)
        buttons[i]!!.text = tanimi[t]
    }

    fun listedeAra(t: Int, cevaplarIcin: List<Int>): Boolean {
        for (i in cevaplarIcin.indices) {
            if (t == cevaplarIcin[i]) {
                return true
            }
        }
        return false
    }

    fun kelimeListesindeAra(t: Int, ayniKelimeKontrolListe: List<Int>): Boolean {
        for (i in ayniKelimeKontrolListe.indices) {
            if (t == ayniKelimeKontrolListe[i]) {
                return true
            }
        }
        return false
    }

    //ilk atama textview ve bir buton icin yapılıyor.
    fun ilkAtama(buttons: List<Button?>, tanimi: List<String>, indexes: MutableList<Int>,
                 forAnswers: MutableList<Int>, kelime: List<String>, ayniKelimeKontrolListe: MutableList<Int>): IntArray {
        val W = IntArray(2)
        val ap = rand.nextInt(buttons.size)
        var pa = rand.nextInt(kelime.size)

        //ayni kelimenin sorulmamasi icin kontrol noktasi
        while (listedeAra(pa, ayniKelimeKontrolListe)) {
            pa = rand.nextInt(kelime.size)
        }
        ayniKelimeKontrolListe.add(pa)
        testKelimesi!!.text = kelime[pa]
        indexes.add(ap)
        forAnswers.add(pa)
        Log.v(TAG, "Cevap indeksi=$pa")
        val x = buttons[ap]
        x!!.text = tanimi[pa]
        Log.v(TAG, "İLKATAMAindexes büyüklüğü" + indexes.size)
        W[0] = ap
        W[1] = pa
        return W
    }

    fun ikinciAtama(tanimi: List<String>, buttons: List<Button?>, forAnswers: MutableList<Int>, kontrol: Int, indexes: MutableList<Int>) {
        for (i in 0..3) {
            if (i == kontrol) {
            } else {
                butonuSetEt(tanimi, buttons, i, forAnswers, indexes)
            }
        }
    }

    companion object {
        private const val TAG = "TestEt"
    }
}