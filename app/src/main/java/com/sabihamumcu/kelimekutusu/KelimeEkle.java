package com.sabihamumcu.kelimekutusu;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class KelimeEkle extends AppCompatActivity {

    DBHelper mydb;
    EditText kelime;
    EditText tanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime_ekle);

        kelime=(EditText) findViewById(R.id.kelimeAdi);
        tanim=(EditText) findViewById(R.id.kelimeTanimi);


        mydb=new DBHelper(this);


    }


    public void KelimeyiEkle(View view) {
        if(isEmpty(kelime) || isEmpty(tanim)){
            Toast.makeText(this,"Boşlukları doldurunuz.",Toast.LENGTH_SHORT).show();

        }
        else{
            if(mydb.insertWords(kelime.getText().toString(),tanim.getText().toString())){
                Toast.makeText(this,"Kelime ve tanim basariyla eklendi.",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Kelime ve tanim eklerken hata oluştu.",Toast.LENGTH_SHORT).show();
            }
            kelime.setText("");
            tanim.setText("");
        }
    }



    private boolean isEmpty(EditText editText){
        return editText.getText().toString().trim().length()==0;
    }

//    public void KelimeyiSil(View view) {
//        SQLiteDatabase db = mydb.getReadableDatabase();
//        Cursor res =  db.rawQuery( "select * from words", null );
//        res.moveToFirst();
//
//        for(int i=0;i<5;i++){
//            db.delete("words",
//                    "id = ? ",
//                    new String[]{Integer.toString(i)});
//        }
//    }
}
