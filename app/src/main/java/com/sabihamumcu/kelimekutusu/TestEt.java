package com.sabihamumcu.kelimekutusu;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class TestEt extends AppCompatActivity{

    DBHelper mydb;
    Button b1,b2,b3,b4;
    TextView t1;
    Random rand=new Random();
    List<String> your_array_list;
    private static final String TAG = "TestEt";

    //ayni kelimenin tekrar sorulmamasi icin
    List<Integer> ayniKelimeKontrolListe=new ArrayList<Integer>();

    //ArrayList<String> array_list = new ArrayList<String>();
    //ArrayList<String> array_list;
    HashMap<String,String> hm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_et);

        t1=(TextView) findViewById(R.id.testKelimesi);

        b1=(Button) findViewById(R.id.f);
        b2=(Button) findViewById(R.id.s);
        b3=(Button) findViewById(R.id.t);
        b4=(Button) findViewById(R.id.fo);

        mydb=new DBHelper(this);
        //array_list=mydb.getAllWords();

        your_array_list= new ArrayList<String>();
        final List<String> kelime=new ArrayList<String>();
        final List<String> tanimi=new ArrayList<String>();
        //yanlis cevaplanan kelimeler icin
        final ArrayList<String> yanlisCevaplar=new ArrayList<String>();

        hm=mydb.getAllWords();

        if(hm.isEmpty()){
            Toast.makeText(this,"Bos bir kelime kutunuz var.",Toast.LENGTH_SHORT).show();
        }else if(hm.size()<5){
            Toast.makeText(this,"Test için kelime kutunuzda en az 5 kelime olmalı.",Toast.LENGTH_SHORT).show();
        }
        else {
            for (Map.Entry<String, String> entry : hm.entrySet()) {
                String a = entry.getKey();
                String b = entry.getValue();
                kelime.add(a);
                tanimi.add(b);
                your_array_list.add(a + "\t\t\t" + b);
            }
            final Handler h=new Handler();
            //todo1:buttonlarin hep aynı yerinde olmamlı cevap.asagidaki kodudeiştir.

//            int t=RastgeleSayi();
//            t1.setText(""+kelime.get(t));
//            String cevap=tanimi.get(t);

//            int j=RastgeleSayi();
//            while(ayniMi(t,j)){
//                j=RastgeleSayi();
//
//            }

//            b1.setText(""+tanimi.get(3));
//            b2.setText(""+tanimi.get(4));
//            b3.setText(""+tanimi.get(5));
//            b4.setText(""+tanimi.get(6));
            final List<Button> buttons=new ArrayList<Button>();
            final List<Integer> indexes=new ArrayList<Integer>();
            final List<Integer> cevaplarİcin=new ArrayList<Integer>();

            buttons.add(b1);
            buttons.add(b2);
            buttons.add(b3);
            buttons.add(b4);

            //ilk atama textview ve bir buton icin yapılıyor.

//            int ap=rand.nextInt(buttons.size());
//            int pa=rand.nextInt(tanimi.size());
//            t1.setText(kelime.get(pa));

            //indexes ve cevaplarİcin listeleri her döngüde 0 lanacak
//            indexes.add(ap);
//            cevaplarİcin.add(pa);
//
//            Button x=buttons.get(ap);
//            x.setText(tanimi.get(pa));


            //butun kelimeler sorulana kadar donen blok


            final int[] ap=ilkAtama(buttons,tanimi,indexes,cevaplarİcin,kelime,ayniKelimeKontrolListe);
            //final Button cevap=buttons.get(ap[0]);

            //kelime.remove(ap[1]);
            Log.v(TAG, "Kaldirilan kelime=" + kelime.size());
            ikinciAtama(tanimi,buttons,cevaplarİcin,ap[0],indexes);
            for(int i=0;i<4;i++){
                buttons.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(view==buttons.get(indexes.get(0))){
                            Log.v(TAG, "verilen cevap indeksi=" + view.getId());
                            Toast.makeText(TestEt.this,"Dogru bildiniz.",Toast.LENGTH_SHORT).show();
                            //view.setBackgroundColor(Color.GREEN);
                            //cevap.setBackgroundColor(Color.GREEN);
                            cevaplarİcin.clear();
                            indexes.clear();
                            //view.setBackgroundResource(android.R.drawable.btn_default);
                            Log.v(TAG, "indexes büyüklüğü=" + indexes.size());

                            if(ayniKelimeKontrolListe.size()==tanimi.size()){
                                Toast.makeText(TestEt.this,"Testi bitirdiniz.",Toast.LENGTH_SHORT).show();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i=new Intent(TestEt.this,Sonuc.class);
                                        i.putExtra("list",yanlisCevaplar);
                                        startActivity(i);
                                    }
                                },1000);
                            }else{
                                int[] ap=ilkAtama(buttons,tanimi,indexes,cevaplarİcin,kelime,ayniKelimeKontrolListe);
                                //kelime.remove(ap[1]);
                                Log.v(TAG, "Kaldirilan kelime=" + kelime.size());
                                ikinciAtama(tanimi,buttons,cevaplarİcin,ap[0],indexes);
                                Log.v(TAG, "indexes büyüklüğü" + indexes.size());
                            }

                        }
                        else{
                            Log.v(TAG, "verilen cevap indeksi=" + view.getId());
                            //view.setBackgroundColor(Color.RED);
                            yanlisCevaplar.add(t1.getText().toString());
                            cevaplarİcin.clear();
                            indexes.clear();
                            Log.v(TAG, "indexes büyüklüğü=" + indexes.size());

                            if(ayniKelimeKontrolListe.size()==tanimi.size()) {
                                Toast.makeText(TestEt.this,"Testi bitirdiniz.",Toast.LENGTH_SHORT).show();
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i=new Intent(TestEt.this,Sonuc.class);
                                        i.putExtra("list",yanlisCevaplar);
                                        startActivity(i);
                                    }
                                },1000);

                            }else {
                                int[] ap = ilkAtama(buttons, tanimi, indexes, cevaplarİcin, kelime, ayniKelimeKontrolListe);

                                //kelime.remove(ap[1]);
                                Log.v(TAG, "Kaldirilan kelime=" + kelime.size());
                                ikinciAtama(tanimi, buttons, cevaplarİcin, ap[0], indexes);
                                Log.v(TAG, "indexes büyüklüğü" + indexes.size());
                            }
                        }
                    }
                });
            }

            //ilk atamanin sonu
            //ikinci atama baslangici-diger butonlara deger atanmasi
//            for(int i=0;i<4;i++){
////                //rastgele bir buton secilir
////                int ap=rand.nextInt(buttons.size());
////                //cevabin butonlar arasında farkli olmasi icin listede tutulur
////                int k=ayniOlmayan(indexes,ap,buttons);
////                indexes.add(k);
////
////                Button x=buttons.get(k);
////                buttonaDegerAta(t,tanimi,x);
//
//                if(i==ap){
//
//                }
//                else{
//                    butonuSetEt(tanimi,buttons,i,cevaplarİcin);
//                }
//
//
//            }



            //ikinci atama sonu

//
//            Button y=bosButton(buttons);
//            y.setText(cevap);

//            b1.setText(""+kelime.get(j));
//
//            int k=rand.nextInt(your_array_list.size());
//            if(t!=k){
//
//            }
        }
        //Set<String> set=hm.keySet();

        //t1.setText(""+array_list.get(2));
        //t1.setText(""+hm.get(set));

//        b1.setText(""+array_list.get(3));
//        b2.setText(""+array_list.get(4));
//        b3.setText(""+array_list.get(5));


    }



    public  int RastgeleSayi(){
        int t=rand.nextInt(your_array_list.size());
        return t;
    }
//    public  int ayniOlmayan(List<Integer> indexes,int c,List<Button> buttons){
//
//        for(int k=0;k<indexes.size();k++){
//            while(c==indexes.get(k))
//                c=rand.nextInt(buttons.size());
//
//        }
//
//        return c;
//    }

//    public boolean ayniMi(int a,int b){
//        if(a==b)
//            return true;
//        else
//            return false;
//    }
//
//    public void buttonaDegerAta(int cevapIndeksi,List<String> tanimi,Button b){
//        int j=RastgeleSayi();
//        while(ayniMi(cevapIndeksi,j)){
//            j=RastgeleSayi();
//        }
//
//        b.setText(""+tanimi.get(j));
//    }
    public void butonuSetEt(List<String> tanimi,List<Button> buttons,int i,List<Integer> cevaplarIcin,List<Integer> indexes){
        int t=rand.nextInt(tanimi.size());
        while (listedeAra(t,cevaplarIcin)){
            t=rand.nextInt(tanimi.size());
        }
        cevaplarIcin.add(t);
        indexes.add(i);
        buttons.get(i).setText(tanimi.get(t));
    }
    public boolean listedeAra(int t,List<Integer> cevaplarIcin){
        for(int i=0;i<cevaplarIcin.size();i++){
            if(t==cevaplarIcin.get(i))
            {
                return true;
            }
        }
        return false;
    }
    public boolean kelimeListesindeAra(int t,List<Integer> ayniKelimeKontrolListe){
        for(int i=0;i<ayniKelimeKontrolListe.size();i++){
            if(t==ayniKelimeKontrolListe.get(i))
            {
                return true;
            }
        }
        return false;
    }

    //ilk atama textview ve bir buton icin yapılıyor.
    public int[] ilkAtama(List<Button> buttons,List<String> tanimi,List<Integer> indexes,
                         List<Integer> cevaplarİcin,List<String> kelime,List<Integer> ayniKelimeKontrolListe){
        int[] W=new int[2];
        int ap=rand.nextInt(buttons.size());
        int pa=rand.nextInt(kelime.size());

        //ayni kelimenin sorulmamasi icin kontrol noktasi
        while (listedeAra(pa,ayniKelimeKontrolListe)){
            pa=rand.nextInt(kelime.size());
        }
        ayniKelimeKontrolListe.add(pa);
        t1.setText(kelime.get(pa));
        indexes.add(ap);


        cevaplarİcin.add(pa);
        Log.v(TAG, "Cevap indeksi=" + pa);
        Button x=buttons.get(ap);
        x.setText(tanimi.get(pa));
        Log.v(TAG, "İLKATAMAindexes büyüklüğü" + indexes.size());
        W[0]=ap;
        W[1]=pa;

        return W;

    }

    public void ikinciAtama(List<String> tanimi,List<Button> buttons,List<Integer> cevaplarİcin,int kontrol,List<Integer> indexes){
        for(int i=0;i<4;i++){
            if(i==kontrol){

            }
            else{
                butonuSetEt(tanimi,buttons,i,cevaplarİcin,indexes);
            }
        }

    }




//    public Button bosButton(List<Button> buttons){
//        for(int i=0;i<4;i++){
//            if(buttons.get(i)==null){
//                return buttons.get(i);
//            }
//        }
//        return buttons.get(0);
//    }
//    public int bosIn(List<Integer> indexes){
//        int t;
//        int y[]=new int[3];
//        for(int i=0;i<3;i++){
//            t=indexes.get(i);
//            y[i]=t;
//        }
//
//    }


}
