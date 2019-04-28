package com.sabihamumcu.kelimekutusu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sabihamumcu.kelimekutusu.model.Word;
import com.sabihamumcu.kelimekutusu.viewmodel.WordViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestEt extends AppCompatActivity {

    Button b1, b2, b3, b4;
    TextView t1;
    Random rand = new Random();
    List<String> your_array_list;
    private static final String TAG = "TestEt";
    private WordViewModel wordViewModel;

    List<Integer> repeatedWordControlList = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_et);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        t1 = (TextView) findViewById(R.id.testKelimesi);

        b1 = (Button) findViewById(R.id.f);
        b2 = (Button) findViewById(R.id.s);
        b3 = (Button) findViewById(R.id.t);
        b4 = (Button) findViewById(R.id.fo);

        wordViewModel.getWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                your_array_list = new ArrayList<String>();
                final List<String> wordList = new ArrayList<String>();
                final List<String> descriptionList = new ArrayList<String>();
                for (int i = 0; i < words.size(); i++) {
                    your_array_list.add(words.get(i).getWord() + " " + words.get(i).getDescription());
                    wordList.add(words.get(i).getWord());
                    descriptionList.add(words.get(i).getDescription());
                }

                final ArrayList<String> wrongAnswers = new ArrayList<String>();

                if (your_array_list.isEmpty()) {
                    Toast.makeText(TestEt.this, "You have an empty word box.", Toast.LENGTH_SHORT).show();
                } else if (your_array_list.size() < 5) {
                    Toast.makeText(TestEt.this, "You must have at least 5 words to test yourself.", Toast.LENGTH_SHORT).show();
                } else {

                    final Handler h = new Handler();
                    final List<Button> buttons = new ArrayList<Button>();
                    final List<Integer> indexes = new ArrayList<Integer>();
                    final List<Integer> forAnswers = new ArrayList<Integer>();

                    buttons.add(b1);
                    buttons.add(b2);
                    buttons.add(b3);
                    buttons.add(b4);

                    final int[] ap = ilkAtama(buttons, descriptionList, indexes, forAnswers, wordList, repeatedWordControlList);

                    Log.v(TAG, "Kaldirilan kelime=" + wordList.size());
                    ikinciAtama(descriptionList, buttons, forAnswers, ap[0], indexes);
                    for (int i = 0; i < 4; i++) {
                        buttons.get(i).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (view == buttons.get(indexes.get(0))) {
                                    Log.v(TAG, "verilen cevap indeksi=" + view.getId());
                                    Toast.makeText(TestEt.this, "Dogru bildiniz.", Toast.LENGTH_SHORT).show();
                                    //view.setBackgroundColor(Color.GREEN);
                                    //cevap.setBackgroundColor(Color.GREEN);
                                    forAnswers.clear();
                                    indexes.clear();
                                    //view.setBackgroundResource(android.R.drawable.btn_default);
                                    Log.v(TAG, "indexes büyüklüğü=" + indexes.size());

                                    if (repeatedWordControlList.size() == descriptionList.size()) {
                                        Toast.makeText(TestEt.this, "Testi bitirdiniz.", Toast.LENGTH_SHORT).show();
                                        h.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent i = new Intent(TestEt.this, Sonuc.class);
                                                i.putExtra("list", wrongAnswers);
                                                startActivity(i);
                                            }
                                        }, 1000);
                                    } else {
                                        int[] ap = ilkAtama(buttons, descriptionList, indexes, forAnswers, wordList, repeatedWordControlList);
                                        //kelime.remove(ap[1]);
                                        Log.v(TAG, "Kaldirilan kelime=" + wordList.size());
                                        ikinciAtama(descriptionList, buttons, forAnswers, ap[0], indexes);
                                        Log.v(TAG, "indexes büyüklüğü" + indexes.size());
                                    }

                                } else {
                                    Log.v(TAG, "verilen cevap indeksi=" + view.getId());
                                    //view.setBackgroundColor(Color.RED);
                                    wrongAnswers.add(t1.getText().toString());
                                    forAnswers.clear();
                                    indexes.clear();
                                    Log.v(TAG, "indexes büyüklüğü=" + indexes.size());

                                    if (repeatedWordControlList.size() == descriptionList.size()) {
                                        Toast.makeText(TestEt.this, "Testi bitirdiniz.", Toast.LENGTH_SHORT).show();
                                        h.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent i = new Intent(TestEt.this, Sonuc.class);
                                                i.putExtra("list", wrongAnswers);
                                                startActivity(i);
                                            }
                                        }, 1000);

                                    } else {
                                        int[] ap = ilkAtama(buttons, descriptionList, indexes, forAnswers, wordList, repeatedWordControlList);

                                        //kelime.remove(ap[1]);
                                        Log.v(TAG, "Kaldirilan kelime=" + wordList.size());
                                        ikinciAtama(descriptionList, buttons, forAnswers, ap[0], indexes);
                                        Log.v(TAG, "indexes büyüklüğü" + indexes.size());
                                    }
                                }
                            }
                        });
                    }

                }
            }
        });

    }

    public void butonuSetEt(List<String> tanimi, List<Button> buttons, int i, List<Integer> forAnswers, List<Integer> indexes) {
        int t = rand.nextInt(tanimi.size());
        while (listedeAra(t, forAnswers)) {
            t = rand.nextInt(tanimi.size());
        }
        forAnswers.add(t);
        indexes.add(i);
        buttons.get(i).setText(tanimi.get(t));
    }

    public boolean listedeAra(int t, List<Integer> cevaplarIcin) {
        for (int i = 0; i < cevaplarIcin.size(); i++) {
            if (t == cevaplarIcin.get(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean kelimeListesindeAra(int t, List<Integer> ayniKelimeKontrolListe) {
        for (int i = 0; i < ayniKelimeKontrolListe.size(); i++) {
            if (t == ayniKelimeKontrolListe.get(i)) {
                return true;
            }
        }
        return false;
    }

    //ilk atama textview ve bir buton icin yapılıyor.
    public int[] ilkAtama(List<Button> buttons, List<String> tanimi, List<Integer> indexes,
                          List<Integer> forAnswers, List<String> kelime, List<Integer> ayniKelimeKontrolListe) {
        int[] W = new int[2];
        int ap = rand.nextInt(buttons.size());
        int pa = rand.nextInt(kelime.size());

        //ayni kelimenin sorulmamasi icin kontrol noktasi
        while (listedeAra(pa, ayniKelimeKontrolListe)) {
            pa = rand.nextInt(kelime.size());
        }
        ayniKelimeKontrolListe.add(pa);
        t1.setText(kelime.get(pa));
        indexes.add(ap);


        forAnswers.add(pa);
        Log.v(TAG, "Cevap indeksi=" + pa);
        Button x = buttons.get(ap);
        x.setText(tanimi.get(pa));
        Log.v(TAG, "İLKATAMAindexes büyüklüğü" + indexes.size());
        W[0] = ap;
        W[1] = pa;

        return W;

    }

    public void ikinciAtama(List<String> tanimi, List<Button> buttons, List<Integer> forAnswers, int kontrol, List<Integer> indexes) {
        for (int i = 0; i < 4; i++) {
            if (i == kontrol) {

            } else {
                butonuSetEt(tanimi, buttons, i, forAnswers, indexes);
            }
        }

    }

}
