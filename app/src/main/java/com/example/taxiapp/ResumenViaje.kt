package com.example.taxiapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.taxiapp.databinding.ActivityResumenViajeBinding

class ResumenViaje : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityResumenViajeBinding = DataBindingUtil.setContentView(this, R.layout.activity_resumen_viaje)

        //Recogemos el objeto de la otra activity
        val viajero: Viaje = intent.getSerializableExtra("viajero") as Viaje

        //Imprimimos por pantalla los datos del objeto
        binding.ciudadOrigen.text = viajero.puntoPartida
        binding.ciudadDestino.text = viajero.destino
        binding.fechaSalida.text = viajero.fechaSalida
        binding.horaSalida.text = viajero.horaSalida
        binding.nombreUsuario.text = viajero.nombre

        //Si en la primera activity se seleccionó "ida y vuelta", se mostrará también esa información. Si no, se ocultará
        if(viajero.idaYVuelta){
            binding.textView6.visibility = View.VISIBLE
            binding.textView9.visibility = View.VISIBLE
            binding.fechaVuelta.visibility = View.VISIBLE
            binding.horaVuelta.visibility = View.VISIBLE
            binding.fechaVuelta.text = viajero.fechaRegreso
            binding.horaVuelta.text = viajero.horaRegreso
        }else{
            binding.textView6.visibility = View.GONE
            binding.textView9.visibility = View.GONE
            binding.fechaVuelta.visibility = View.GONE
            binding.horaVuelta.visibility = View.GONE
        }

        fun changeActivity() {
            val intent = Intent(this, PantallaFinal::class.java)
            startActivity(intent)
        }

        binding.botonTerminar.setOnClickListener() {
            changeActivity()
        }
    }
}