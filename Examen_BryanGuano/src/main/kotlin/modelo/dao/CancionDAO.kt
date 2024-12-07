package org.example.modelo.dao

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.modelo.entidad.Cancion
import java.io.File

class CancionDAO {

    companion object {
        const val CANCIONES_RUTA = "canciones.json"
    }

    fun guardarCanciones(canciones: List<Cancion>) {
        val cancionesJSON = convertirACadenaJSON(canciones)
        escribirArchivo(cargarArchivoJSON(), cancionesJSON)
    }

    fun editarCancion(idCancion: Int, nuevosMetadatos: List<String>) {
        val canciones = cargarCancionesDesdeJson()
        val cancionAEditar = canciones.find { it.idCancion == idCancion }
            ?: throw NoSuchElementException("Canción con ID $idCancion no encontrada")

        actualizarCancion(cancionAEditar, nuevosMetadatos)
        guardarCanciones(canciones)
    }

    fun getCanciones(): List<Cancion> = cargarCancionesDesdeJson()

    private fun actualizarCancion(cancion: Cancion, nuevosMetadatos: List<String>) {
        nuevosMetadatos.getOrNull(0)?.takeIf { it != "0" }?.let { cancion.titulo = it }
        nuevosMetadatos.getOrNull(1)?.takeIf { it != "0" }?.let { cancion.artista = it }
        nuevosMetadatos.getOrNull(2)?.takeIf { it != "0" }?.let { cancion.genero = it }
        nuevosMetadatos.getOrNull(3)?.takeIf { it != "0" }?.let { cancion.duracion = it.toDouble() }
        nuevosMetadatos.getOrNull(4)?.takeIf { it != "0" }?.let { cancion.esPopular = it.toBoolean() }
    }

    private fun cargarCancionesDesdeJson(): List<Cancion> {
        val archivoJSON = cargarArchivoJSON()
        if (!archivoJSON.exists()) return emptyList()

        val contenidoJSON = archivoJSON.readText()
        return Json.decodeFromString(contenidoJSON)
    }

    private fun convertirACadenaJSON(canciones: List<Cancion>): String {
        return Json.encodeToString(canciones)
    }

    private fun escribirArchivo(archivo: File, contenido: String) {
        archivo.writeText(contenido)
    }

    private fun cargarArchivoJSON() = File(CANCIONES_RUTA)
}
