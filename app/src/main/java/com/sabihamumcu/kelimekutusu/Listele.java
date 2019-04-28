package com.sabihamumcu.kelimekutusu;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.sabihamumcu.kelimekutusu.adapter.WordAdapter;
import com.sabihamumcu.kelimekutusu.model.Word;
import com.sabihamumcu.kelimekutusu.viewmodel.WordViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Listele extends AppCompatActivity {

    EditText aranacak_kelime;
    private WordViewModel wordViewModel;
    RecyclerView mRecyclerView;
    WordAdapter wordAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listele);

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        final List<String> your_array_list = new ArrayList<String>();
        final List<Word> wordList = new ArrayList<>();
        aranacak_kelime = (EditText) findViewById(R.id.inputSearch);
        mRecyclerView = findViewById(R.id.word_list);
        wordViewModel.getWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {

                for (int i = 0; i < words.size(); i++) {
                    your_array_list.add(words.get(i).getWord() + "\t\t\t" + words.get(i).getDescription());
                    wordList.add(words.get(i));
                }

                wordAdapter = new WordAdapter(wordList, Listele.this, wordViewModel);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(layoutManager);
                mRecyclerView.setAdapter(wordAdapter);
            }
        });

        aranacak_kelime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Listele.this.adapter.getFilter().filter(charSequence);
                Listele.this.wordAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
