package com.example.taxiapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.taxiapp.databinding.ActivityNuevoViajeBinding
import java.text.SimpleDateFormat
import java.util.regex.Pattern

class Recogida : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivityNuevoViajeBinding = DataBindingUtil.setContentView(this, R.layout.activity_nuevo_viaje)

        //Creamos el spinner con las ciudades
        val ciudades = arrayOf("Madrid", "Santiago", "Santander")
        val adaptador: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ciudades)
        binding.spinner.adapter = adaptador

        //Si se selecciona "ida y vuelta", tendrán que aparecer más campos
        var idaYVuelta = false
        binding.idaVuelta.setOnCheckedChangeListener{ buttonView, isChecked ->
            idaYVuelta = isChecked
            if (isChecked) {
                binding.fechaVueltaTexto.visibility = View.VISIBLE
                binding.fechaRegreso.visibility = View.VISIBLE
                binding.horaRegreso.visibility = View.VISIBLE
            } else {
                binding.fechaVueltaTexto.visibility = View.GONE
                binding.fechaRegreso.visibility = View.GONE
                binding.horaRegreso.visibility = View.GONE
            }
        }

        //Cuando pulse "Reservar", se recogerán los datos, se almacenarán en el objeto viajero y se mandarán a la otra activity
        fun changeActivity(){
            val destino = binding.spinner.selectedItem.toString()
            val fechaSalida = binding.fechaIda.text.toString()
            val horaSalida = binding.horaIda.text.toString()
            val nombre = binding.nombre.text.toString()
            val puntoPartida = binding.puntoRecogidaCampo.text.toString()
            val fechaRegreso = binding.fechaRegreso.text.toString()
            val horaRegreso = binding.horaRegreso.text.toString()
            val dni = binding.dni.text.toString()

            //Comprobamos que la fecha de regreso no sea previa a la fecha de ida
                //primero, los convertimos en objetos Date para poder compararlos
            val fechaSalidaValidada = SimpleDateFormat("dd/MM/yyyy").parse(fechaSalida)
            val fechaRegresoValidada = if(idaYVuelta && fechaRegreso.isNotEmpty()){SimpleDateFormat("dd/MM/yyyy").parse(fechaRegreso)
            }else{null}

            //Validamos el DNI con un patrón regex
                //este es el patrón que debe seguir
            val validacionDni = Pattern.compile("[0-9]{8}[A-Z]")
                //¿coincide el dni con el patrón que debe seguir?
            val dniValidado = validacionDni.matcher(dni)

                //comparamos fechas: ¿la de regreso es posterior a la de ida?
            if(fechaRegresoValidada != null && fechaSalidaValidada.after(fechaRegresoValidada)) {
                Toast.makeText(this, "Fecha regreso no puede ser anterior a fecha ida", Toast.LENGTH_SHORT).show()
            }else {
                //si el dni es válido, y el destino no igual que el punto de partida...
                if (dniValidado.matches() && destino != puntoPartida) {
                    //creamos el objeto viajero con los datos y lo pasamos a la activity ResumenViaje
                    val viajero = Viaje(destino, fechaSalida, horaSalida, nombre, puntoPartida, fechaRegreso, horaRegreso, idaYVuelta)
                    val intent = Intent(this, ResumenViaje::class.java)
                    intent.putExtra("viajero", viajero)

                    //Entonces, y solo entonces, pasaríamos a la siguiente activity
                    startActivity(intent)
                } else {
                    if (!dniValidado.matches()) {
                        Toast.makeText(this, "DNI no válido", Toast.LENGTH_SHORT).show()
                    }
                    if (destino == puntoPartida) {
                        Toast.makeText(this, "Punto de partida no válido", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        //Realizamos el datepicker en cada uno de los campos de fecha
        fun showDatePickerDialog(view : View) {
            val datePicker = DatePicker { day, month, year -> when(view.id){
                    R.id.fechaIda -> binding.fechaIda.setText("$day/$month/$year")
                    R.id.fechaRegreso -> binding.fechaRegreso.setText("$day/$month/$year")
                }
            }
            datePicker.show(supportFragmentManager, "datePicker")
        }

        //Realizamos el timepicker en cada uno de los campos de fecha
        fun onTimeSelected(time:String, campoHora: View){
            when(campoHora){
                binding.horaIda -> binding.horaIda.setText("$time")
                binding.horaRegreso -> binding.horaRegreso.setText("$time")
            }
        }
        fun showTimePickerDialog(campoHora:View) {
            val timePicker = TimePicker{onTimeSelected(it, campoHora)}
            timePicker.show(supportFragmentManager, "time")
        }

        //cuando se seleccionen estos campos, se llamarán a estas {funciones}
        binding.fechaIda.setOnClickListener{showDatePickerDialog(it)}
        binding.fechaRegreso.setOnClickListener{showDatePickerDialog(it)}
        binding.horaIda.setOnClickListener{showTimePickerDialog(binding.horaIda)}
        binding.horaRegreso.setOnClickListener{showTimePickerDialog(binding.horaRegreso)}
        binding.botonReservar.setOnClickListener{changeActivity()}
    }
}
