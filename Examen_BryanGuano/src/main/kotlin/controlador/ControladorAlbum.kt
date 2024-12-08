package controlador

import org.example.modelo.dao.AlbumDAO
import org.example.modelo.entidad.Album
import org.example.modelo.entidad.Cancion
import java.io.File
import java.time.LocalDate

class ControladorAlbum(private var contadorAlbum: Int = 1) {

    private val albumDAO = AlbumDAO()

    init {
        inicializarArchivoSiNoExiste()
    }

    private fun inicializarArchivoSiNoExiste() {
        val archivo = File(AlbumDAO.ALBUMES_RUTA)
        if (!archivo.exists()) {
            archivo.writeText("[]")
        }
    }

    fun crearAlbum(
        nombre: String,
        artista: String,
        cantidadCanciones: Int,
        genero: String,
        duracionTotal: Double,
        fechaCreacion: String,
        canciones: List<Cancion> = listOf()
    ) {
        val albumes = albumDAO.getAlbumes().toMutableList()

        val nuevoAlbum = Album(
            contadorAlbum,
            nombre,
            artista,
            cantidadCanciones,
            genero,
            duracionTotal,
            LocalDate.parse(fechaCreacion),
            canciones.toMutableList() // Inicializa la lista vacía
        )
        contadorAlbum++
        albumes.add(nuevoAlbum)
        albumDAO.guardarAlbumes(albumes)
    }

    fun leerAlbumes(): List<Album> {
        return albumDAO.getAlbumes()
    }

    fun editarAlbum(idAlbum: Int, nuevaMetadata: List<String>) {
        albumDAO.editarAlbum(idAlbum, nuevaMetadata)
    }

    fun eliminarAlbum(idAlbumAEliminar: Int): Boolean {
        val albumes = albumDAO.getAlbumes().toMutableList()
        val albumAEliminar = buscarAlbumPorId(albumes, idAlbumAEliminar) ?: return false

        albumes.remove(albumAEliminar)
        albumDAO.guardarAlbumes(albumes)
        return true
    }

    fun agregarCancionAAlbum(idAlbum: Int, cancion: Cancion) {
        val albumes = albumDAO.getAlbumes().toMutableList()
        val album = buscarAlbumPorId(albumes, idAlbum) ?: throw NoSuchElementException("Álbum no encontrado")

        album.canciones.add(cancion)
        albumDAO.guardarAlbumes(albumes)
    }

    fun eliminarCancionDeAlbum(idAlbum: Int, idCancion: Int): Boolean {
        val albumes = albumDAO.getAlbumes().toMutableList()
        val album = buscarAlbumPorId(albumes, idAlbum) ?: return false
        val cancionAEliminar = album.canciones.find { it.idCancion == idCancion } ?: return false

        album.canciones.remove(cancionAEliminar)
        albumDAO.guardarAlbumes(albumes)
        return true
    }

    private fun buscarAlbumPorId(albumes: List<Album>, id: Int): Album? {
        return albumes.find { it.id == id }
    }
}