package org.example.modelo.entidad

import kotlinx.serialization.Serializable

@Serializable
data class Cancion(
    val idCancion: Int,
    val idAlbumAsociado: Int,
    var titulo: String,
    var artista: String,
    var genero: String,
    var duracion: Double,
    var esPopular: Boolean
) {

}
