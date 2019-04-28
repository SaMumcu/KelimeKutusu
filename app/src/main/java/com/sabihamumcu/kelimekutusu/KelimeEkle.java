package com.sabihamumcu.kelimekutusu;

import android.arch.lifecycle.ViewModelProviders;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sabihamumcu.kelimekutusu.model.Word;
import com.sabihamumcu.kelimekutusu.viewmodel.WordViewModel;

public class KelimeEkle extends AppCompatActivity {

    EditText kelime;
    EditText tanim;
    private WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime_ekle);

        kelime = (EditText) findViewById(R.id.kelimeAdi);
        tanim = (EditText) findViewById(R.id.kelimeTanimi);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

    }


    public void addWord(View view) {
        if (isEmpty(kelime) || isEmpty(tanim)) {
            Toast.makeText(this, "Boşlukları doldurunuz.", Toast.LENGTH_SHORT).show();

        } else {
            if (kelime.getText().toString() != null && tanim.getText().toString() != null) {
                Toast.makeText(this, "Kelime ve tanim basariyla eklendi.", Toast.LENGTH_SHORT).show();
                Word word = new Word(kelime.getText().toString(), tanim.getText().toString());
                wordViewModel.insertWord(word);
            } else {
                Toast.makeText(this, "Kelime ve tanim eklerken hata oluştu.", Toast.LENGTH_SHORT).show();
            }
            kelime.setText("");
            tanim.setText("");
        }
    }


    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

}
