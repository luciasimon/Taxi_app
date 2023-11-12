package com.example.taxiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.taxiapp.databinding.ActivityMainBinding

class PantallaInicio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TaxiApp)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        fun changeActivity() {
            val intent = Intent(this, Recogida::class.java)
            startActivity(intent)
        }

        binding.botonInicio.setOnClickListener{
            changeActivity()
        }



    }
}