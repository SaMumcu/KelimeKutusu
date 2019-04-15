package com.sabihamumcu.kelimekutusu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Sonuc extends AppCompatActivity {

    ListView lv;
    TextView tv;
    Button btn;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc);
        lv = (ListView) findViewById(R.id.lv);
        tv = (TextView) findViewById(R.id.textView);
        btn = (Button) findViewById(R.id.testeDon);
        ArrayList<String> liste = (ArrayList<String>) getIntent().getSerializableExtra("list");
        adapter = new ArrayAdapter<String>(
                this,
                R.layout.list_item, R.id.product_name, liste
        );
        lv.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Sonuc.this, TestEt.class);
                startActivity(intent);
            }
        });
    }
}
