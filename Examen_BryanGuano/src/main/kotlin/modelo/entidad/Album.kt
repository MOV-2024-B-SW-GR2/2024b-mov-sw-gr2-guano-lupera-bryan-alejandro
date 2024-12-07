package org.example.modelo.entidad

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Album(
    val id: Int,
    var nombre: String,
    var artista: String,
    var cantidadCanciones: Int,
    var genero: String,
    var duracionTotal: Double,
    @Serializable(with = LocalDateSerializer::class) var fechaCreacion: LocalDate,
    val canciones: MutableList<Cancion> = mutableListOf()
) {

}
