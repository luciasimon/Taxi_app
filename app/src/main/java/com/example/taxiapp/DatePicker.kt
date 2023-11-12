package com.example.taxiapp

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePicker(val listener:(day:Int, month:Int, year:Int) -> Unit):DialogFragment(), DatePickerDialog.OnDateSetListener {
    //la clase datepickerfragment extiende de dialog fragment, y va a devolver tres parámetros: dia, mes y año
    //implementa datepickerdialog y hará algo cuando se pulse
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener(dayOfMonth, month, year) //cuando el usuario selecciona una fecha, llamará a este método, que llamará a este listener
        //el listener ejecutará el código que tenemos en la clase Recogida (showDatePickerDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c:Calendar = Calendar.getInstance()
        val day = c.get(Calendar.DAY_OF_MONTH)
        val month = c.get(Calendar.MONTH)
        val year = c.get(Calendar.YEAR)

        val picker = DatePickerDialog(activity as Context,this, year, month, day)
        //podría llevar un R.style.datePickerTheme detrás de Context para cambiar el color
        return picker
    }
}