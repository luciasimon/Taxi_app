package com.example.taxiapp

import java.io.Serializable

class Viaje (
    val destino : String,
    val fechaSalida : String,
    val horaSalida : String,
    val nombre : String,
    val puntoPartida : String,
    val fechaRegreso : String,
    val horaRegreso : String,
    val idaYVuelta : Boolean
) : Serializable