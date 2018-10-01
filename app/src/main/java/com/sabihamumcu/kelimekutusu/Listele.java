package com.sabihamumcu.kelimekutusu;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Listele extends AppCompatActivity {

    DBHelper mydb;
    ListView lv;
    EditText aranacak_kelime;
    ArrayAdapter<String> adapter;
    Map<String,String> mHashMap;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listele);

        final List<String> your_array_list = new ArrayList<String>();
        final List<String> kelime=new ArrayList<String>();
        final List<String> tanimi=new ArrayList<String>();
        mHashMap = new HashMap<String, String>();

        mydb=new DBHelper(this);

        File path=Listele.this.getDatabasePath("words.db");
        mHashMap=mydb.getAllWords();
        if(mHashMap.isEmpty()){
            Toast.makeText(this,"Bos bir kelime kutunuz var.",Toast.LENGTH_SHORT).show();
        }else {
            for(Map.Entry<String,String> entry:mHashMap.entrySet()){
                String a=entry.getKey();
                String b=entry.getValue();
                kelime.add(a);
                tanimi.add(b);
                your_array_list.add(a+"\t\t\t"+b);
            }
//
//            String a=mHashMap.keySet().toString();
//            String b=mHashMap.values().toString();
//            your_array_list.add(a+"\t"+b);
            lv = (ListView) findViewById(R.id.lv);
            aranacak_kelime = (EditText) findViewById(R.id.inputSearch);
            adapter = new ArrayAdapter<String>(
                    this,
                    R.layout.list_item, R.id.product_name, your_array_list
            );
            lv.setAdapter(adapter);

            aranacak_kelime.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    Listele.this.adapter.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    final String s=lv.getItemAtPosition(i).toString();
                    Toast.makeText(Listele.this,""+s,Toast.LENGTH_SHORT).show();
                    final int position=i;
                    final String[] parts=s.split("\t\t\t");
                    final String klm=parts[0];
                    //final String tnm=parts[1];
//                    String klm=kelime.get(i);
//                    String tnm= tanimi.get(i);


                    new AlertDialog.Builder(Listele.this)
                            .setTitle("Değiştir yada Sil")
                            .setMessage("Bu kelimeyisilmek mi istiyorsun?")
                            .setPositiveButton("Sil", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(Listele.this,"Bu kelime siliniyor.."+s,Toast.LENGTH_SHORT).show();
                                    if(mydb.deleteWord(klm))
                                    {
                                        Toast.makeText(Listele.this,"Kelime başariyla silindi.",Toast.LENGTH_SHORT).show();
                                        adapter.remove(adapter.getItem(position));
                                        adapter.notifyDataSetChanged();
                                    }
                                }
                            })
//                            .setNegativeButton("Değiştir", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(Listele.this,"Databasede guncelleme yapilacak.",Toast.LENGTH_SHORT).show();
//                                }
//                            })
                            .setNeutralButton("Kapat", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    Toast.makeText(Listele.this,"Dialog kapatilacak.",Toast.LENGTH_SHORT).show();
                                    dialogInterface.dismiss();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    return true;
                }
            });
        }
    }
}
