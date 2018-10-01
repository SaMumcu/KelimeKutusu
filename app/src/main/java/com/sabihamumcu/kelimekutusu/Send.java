package com.sabihamumcu.kelimekutusu;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Send extends AppCompatActivity {

    ArrayList<String> gonderilecekListe = new ArrayList<>();
    DBHelper mydb;
    Map<String, String> mHashMap;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        b = (Button) findViewById(R.id.anaEkranaDon);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Send.this, MainActivity.class);
                startActivity(intent);
            }
        });
        mHashMap = new HashMap<String, String>();

        mydb = new DBHelper(this);
        File path = Send.this.getDatabasePath("words.db");
        mHashMap = mydb.getAllWords();
        if (mHashMap.isEmpty()) {
            Toast.makeText(this, "Bos bir kelime kutunuz var.", Toast.LENGTH_SHORT).show();
        } else {
            for (Map.Entry<String, String> entry : mHashMap.entrySet()) {
                String a = entry.getKey();
                String b = entry.getValue();
                gonderilecekListe.add(a + "\t\t\t" + b);
            }
        }
        try {
            String fileName = "output.txt";
            File root = new File(Environment.getExternalStorageDirectory(), "testDir");
            if (!root.exists()) {
                root.mkdirs();
            }

            File gpxfile = new File(root, fileName);
            FileWriter writer = new FileWriter(gpxfile);
//            for(String str: gonderilecekListe) {
//                writer.write(str);
//            }
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

        MobileAds.initialize(getApplicationContext(), "");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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
