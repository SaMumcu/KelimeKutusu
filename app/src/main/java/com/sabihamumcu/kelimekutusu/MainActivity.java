package com.sabihamumcu.kelimekutusu;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void yeniKelime(View view) {
        Intent intent = new Intent(MainActivity.this, KelimeEkle.class);
        startActivity(intent);
    }

    public void testEt(View view) {
        Intent intent = new Intent(MainActivity.this, TestEt.class);
        startActivity(intent);
    }

    public void listele(View view) {
        Intent intent = new Intent(MainActivity.this, Listele.class);
        startActivity(intent);
    }

    public void Gonder(View view) {
        Intent intent = new Intent(MainActivity.this, Send.class);
        startActivity(intent);
    }

}
