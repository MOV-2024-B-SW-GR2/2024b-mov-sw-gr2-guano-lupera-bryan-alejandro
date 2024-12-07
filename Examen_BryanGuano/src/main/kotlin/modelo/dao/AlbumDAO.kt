package org.example.modelo.dao

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.modelo.entidad.Album
import java.io.File
import java.time.LocalDate

class AlbumDAO {
    companion object {
        const val ALBUMES_RUTA = "albumes.json"
    }

    fun guardarAlbumes(albumes: List<Album>) {
        val jsonContenido = Json.encodeToString(albumes)
        escribirContenidoEnArchivo(cargarArchivoJSON(), jsonContenido)
    }

    fun getAlbumes(): List<Album> = cargarAlbumesDesdeJson()

    fun editarAlbum(idAlbum: Int, nuevosMetadatos: List<String>) {
        val albumes = cargarAlbumesDesdeJson().toMutableList()
        val albumAEditar = encontrarAlbumPorId(albumes, idAlbum)

        actualizarMetadataAlbum(albumAEditar, nuevosMetadatos)
        guardarAlbumesEnJSON(albumes)
    }

    private fun cargarAlbumesDesdeJson(): List<Album> {
        val archivoJSON = File(ALBUMES_RUTA)
        if (!archivoJSON.exists()) {
            return emptyList()
        }
        val contenidoJSON = obtenerContenidoJSON(archivoJSON)
        return Json.decodeFromString(contenidoJSON)
    }

    private fun obtenerContenidoJSON(archivoJSON: File): String = archivoJSON.readText()

    private fun actualizarMetadataAlbum(album: Album, nuevosMetadatos: List<String>) {
        nuevosMetadatos.getOrNull(0)?.takeIf { it != "0" }?.let { album.nombre = it }
        nuevosMetadatos.getOrNull(1)?.takeIf { it != "0" }?.let { album.artista = it }
        nuevosMetadatos.getOrNull(2)?.takeIf { it != "0" }?.let { album.cantidadCanciones = it.toInt() }
        nuevosMetadatos.getOrNull(3)?.takeIf { it != "0" }?.let { album.genero = it }
        nuevosMetadatos.getOrNull(4)?.takeIf { it != "0" }?.let { album.duracionTotal = it.toDouble() }
        nuevosMetadatos.getOrNull(5)?.takeIf { it != "0" }?.let { album.fechaCreacion = LocalDate.parse(it) }
    }

    private fun encontrarAlbumPorId(albumes: List<Album>, idAlbum: Int): Album {
        return albumes.find { it.id == idAlbum } ?: throw NoSuchElementException("Álbum con ID $idAlbum no encontrado")
    }

    private fun escribirContenidoEnArchivo(archivo: File, contenido: String) {
        archivo.writeText(contenido)
    }

    private fun guardarAlbumesEnJSON(albumes: List<Album>) {
        val archivoJSON = File(ALBUMES_RUTA)
        val albumesEnJSON = Json.encodeToString(albumes)
        archivoJSON.writeText(albumesEnJSON)
    }

    private fun cargarArchivoJSON(): File = File(ALBUMES_RUTA)
}
