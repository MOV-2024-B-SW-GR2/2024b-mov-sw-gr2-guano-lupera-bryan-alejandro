package controlador

import org.example.modelo.dao.CancionDAO
import org.example.modelo.entidad.Cancion
import java.io.File

class ControladorCancion(private var contadorCancion: Int = 1) {

    private val cancionDAO = CancionDAO()

    init {
        inicializarArchivoSiNoExiste()
    }

    private fun inicializarArchivoSiNoExiste() {
        val archivo = File(CancionDAO.CANCIONES_RUTA)
        if (!archivo.exists()) {
            archivo.writeText("[]")
        }
    }

    fun crearCancion(
        idAlbumAsociado: Int,
        titulo: String,
        artista: String,
        genero: String,
        duracion: Double,
        esPopular: Boolean
    ) {
        val canciones = cancionDAO.getCanciones().toMutableList()
        val nuevaCancion = Cancion(contadorCancion, idAlbumAsociado, titulo, artista, genero, duracion, esPopular)
        contadorCancion++
        canciones.add(nuevaCancion)
        cancionDAO.guardarCanciones(canciones)
    }

    fun leerCanciones(): List<Cancion> {
        return cancionDAO.getCanciones()
    }

    fun editarCancion(idCancion: Int, nuevaMetadata: List<String>) {
        cancionDAO.editarCancion(idCancion, nuevaMetadata)
    }

    fun eliminarCancion(idCancionAEliminar: Int): Boolean {
        val canciones = cancionDAO.getCanciones().toMutableList()
        val cancionAEliminar = buscarCancionPorId(canciones, idCancionAEliminar) ?: return false

        canciones.remove(cancionAEliminar)
        cancionDAO.guardarCanciones(canciones)
        return true
    }

    private fun buscarCancionPorId(canciones: List<Cancion>, id: Int): Cancion? {
        return canciones.find { it.idCancion == id }
    }
}
