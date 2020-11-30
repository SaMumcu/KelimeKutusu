package com.sabihamumcu.kelimekutusu

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*

class Result : AppCompatActivity() {

    var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        val liste = intent.getSerializableExtra("list") as ArrayList<String>
        adapter = ArrayAdapter(
                this,
                R.layout.list_item, R.id.product_name, liste
        )
        lv!!.adapter = adapter
        testeDon!!.setOnClickListener {
            val intent = Intent(this@Result, Testing::class.java)
            startActivity(intent)
        }
    }
}