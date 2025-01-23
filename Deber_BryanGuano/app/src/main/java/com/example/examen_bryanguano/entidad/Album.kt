package com.example.examen_bryanguano.entidad

import java.time.LocalDate

class Album(
    val idAlbum: Int,
    var nombre: String,
    var artista: String,
    var cantidadCanciones: Int,
    var genero: String,
    var duracionTotal: Double,
)