package com.sabihamumcu.kelimekutusu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sabihamumcu.kelimekutusu.model.Word;
import com.sabihamumcu.kelimekutusu.viewmodel.WordViewModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Send extends AppCompatActivity {

    ArrayList<String> gonderilecekListe = new ArrayList<>();
    Button b;
    private WordViewModel wordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);

        b = (Button) findViewById(R.id.anaEkranaDon);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Send.this, MainActivity.class);
                startActivity(intent);
            }
        });

        File path = Send.this.getDatabasePath("db_words.db");
        wordViewModel.getWords().observe(this, new Observer<List<Word>>() {
            @Override
            public void onChanged(@Nullable List<Word> words) {
                for (int i = 0; i < words.size(); i++) {
                    gonderilecekListe.add(words.get(i).getWord() + " " + words.get(i).getDescription());
                }
                if (gonderilecekListe.isEmpty()) {
                    Toast.makeText(Send.this, "You have an empty word box.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        try {
            String fileName = "output.txt";
            File root = new File(Environment.getExternalStorageDirectory(), "testDir");
            if (!root.exists()) {
                root.mkdirs();
            }

            File gpxfile = new File(root, fileName);
            FileWriter writer = new FileWriter(gpxfile);
            for (int i = 0; i < gonderilecekListe.size(); i++) {
                writer.write(gonderilecekListe.get(i));
                writer.write("\n");
            }
            writer.flush();
            writer.close();
            sendEmail(gpxfile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void sendEmail(File file) {
        Uri path = Uri.fromFile(file);

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_SUBJECT, "Kelime Kutusu");
        i.putExtra(Intent.EXTRA_TEXT, "Kelime Kutunuz");
        i.putExtra(Intent.EXTRA_STREAM, path); // Include the path
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
