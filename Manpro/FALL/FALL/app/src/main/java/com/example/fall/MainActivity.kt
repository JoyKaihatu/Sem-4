package com.example.fall

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTitle("FALL 'Forum All in One'")
        setContentView(R.layout.activity_main)

        val img = findViewById<ImageView>(R.id.landImg)
        val txtCl = findViewById<TextView>(R.id.clickAtC)

        img.setOnClickListener {
            val toLogin = Intent(this@MainActivity, Login::class.java)
            startActivity(toLogin)
        }

        txtCl.setOnClickListener {
            val toLogin = Intent(this@MainActivity, Login::class.java)
            startActivity(toLogin)
        }
    }
}