package org.example

import controlador.ControladorAlbum
import controlador.ControladorCancion
import org.example.modelo.entidad.*
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    var running = true
    val controladorAlbum = ControladorAlbum()
    val controladorCancion = ControladorCancion()

    while (running) {
        println("\n=== Menú Principal ===")
        println("1. Crear Álbum")
        println("2. Crear Canción")
        println("3. Ver Álbumes y Canciones")
        println("4. Editar Álbum")
        println("5. Editar Canción")
        println("6. Eliminar Álbum")
        println("7. Eliminar Canción")
        println("8. Salir")

        print("Seleccione una opción: ")
        val opcion = scanner.nextInt()
        scanner.nextLine()  // Limpiar el buffer de entrada

        when (opcion) {
            1 -> {
                print("Nombre del album: ")
                val nombreAlbum = scanner.nextLine()

                print("Artista: ")
                val artista = scanner.nextLine()

                print("Cantidad canciones: ")
                val cantidadCanciones = scanner.nextInt()
                scanner.nextLine()

                print("Género: ")
                val genero = scanner.nextLine()

                print("Duración total: ")
                val duracionTotal = scanner.nextDouble()
                scanner.nextLine()
                print("Fecha creación: ")
                val fechaCreacion = scanner.nextLine()

                controladorAlbum.crearAlbum(
                    nombreAlbum,
                    artista,
                    cantidadCanciones,
                    genero,
                    duracionTotal,
                    fechaCreacion
                )
                println("\n--Álbum creado--")
            }

            2 -> {
                print("Identificador de álbum asociado: ")
                val idAlbumAsociado = scanner.nextInt()
                scanner.nextLine()

                print("Título de la canción: ")
                val tituloCancion = scanner.nextLine()

                print("Artista: ")
                val artistaCancion = scanner.nextLine()

                print("Género: ")
                val generoCancion = scanner.nextLine()

                print("Duración de la canción: ")
                val duracionCancion = scanner.nextDouble()
                scanner.nextLine()

                print("Es popular (sí o no): ")
                val entradaUsuario = scanner.nextLine().trim().lowercase()

                val esPopularCancion = when (entradaUsuario) {
                    "sí", "si", "true" -> true
                    "no", "false" -> false
                    else -> {
                        println("Entrada inválida, se asignará false por defecto.")
                        false
                    }
                }

                controladorCancion.crearCancion(
                    idAlbumAsociado,
                    tituloCancion,
                    artistaCancion,
                    generoCancion,
                    duracionCancion,
                    esPopularCancion
                )
                controladorAlbum.agregarCancionAAlbum(idAlbumAsociado, controladorCancion.leerCanciones().last())
                println("\n--Canción creado--")
            }

            3 -> {
                try {
                    imprimirCanciones(controladorCancion.leerCanciones())
                    imprimirAlbumes(controladorAlbum.leerAlbumes())

                } catch (e: Exception) {
                    println(e.message)
                }
            }

            4 -> {
                println("Llene los datos que quiere editar, deje en blanco si no quiere editar")

                print("Identificador del álbum: ")
                val idAlbumAEditar = readLine() ?: "0"

                print("Nombre álbum: ")
                val nuevoNombre = readLine() ?: "0"

                print("Artista: ")
                val nuevoArtista = readLine() ?: "0"

                print("Cantidad de canciones: ")
                val nuevaCantidadCanciones = readLine() ?: "0"

                print("Género: ")
                val nuevoGenero = readLine() ?: "0"

                print("Duración total del álbum: ")
                val nuevaDuracionTotal = readLine() ?: "0"

                print("Fecha de creación: ")
                val nuevaFechaCreacion = readLine() ?: "0"


                val nuevaMetadata = arrayListOf(
                    nuevoNombre,
                    nuevoArtista,
                    nuevaCantidadCanciones,
                    nuevoGenero,
                    nuevaDuracionTotal,
                    nuevaFechaCreacion
                )

                controladorAlbum.editarAlbum(idAlbumAEditar.toInt(), nuevaMetadata)
                println("\n--Álbum editado--")
            }

            5 -> {
                println("Llene los datos que quiere editar, deje en blanco si no quiere editar")

                print("Identificador de la canción: ")
                val idCancionAEditar = readLine() ?: "0"

                print("Título de la canción: ")
                val tituloCancion = scanner.nextLine() ?: "0"

                print("Artista: ")
                val artistaCancion = scanner.nextLine() ?: "0"

                print("Género: ")
                val generoCancion = scanner.nextLine() ?: "0"

                print("Duración de la canción: ")
                val duracionCancion = scanner.nextLine() ?: "0"

                print("Es popular (sí o no): ")
                val esPopularCancion = scanner.nextLine().trim().lowercase()

                val nuevaMetadataCancion = arrayListOf(
                    tituloCancion,
                    artistaCancion,
                    generoCancion,
                    duracionCancion,
                    esPopularCancion
                )

                controladorCancion.editarCancion(idCancionAEditar.toInt(), nuevaMetadataCancion)
                println("\n--Canción editada--")
            }

            6 -> {
                print("Ingrese el id del album a eliminar: ")
                val idAlbumAEliminar = scanner.nextInt()
                controladorAlbum.eliminarAlbum(idAlbumAEliminar)
                println("\n--Álbum eliminado--")
            }

            7 -> {

                print("Ingrese el id del album en donde se encuentra la canción: ")
                val idAlbumAEliminar = scanner.nextInt()

                print("Ingrese el id de la canción a eliminar: ")
                val idCancionAEliminar = scanner.nextInt()
                
                controladorCancion.eliminarCancion(idCancionAEliminar)
                controladorAlbum.eliminarCancionDeAlbum(idAlbumAEliminar, idCancionAEliminar)
                println("\n--Canción eliminada--")
            }

            8 -> {
                println("Saliendo del programa...")
                running = false
            }

            else -> {
                println("Opción no válida. Por favor, intente de nuevo.")
            }
        }

    }
}

fun imprimirCanciones(canciones: List<Cancion>) {
    if (canciones.isEmpty())
        return

    println("Lista de canciones:")
    for (cancion in canciones) {
        println("   Album N.: ${cancion.idAlbumAsociado}")
        println("   Título: ${cancion.titulo}")
        println("   Artista: ${cancion.artista}")
        println("   Género: ${cancion.genero}")
        println("   Duración de la canción: ${cancion.duracion}")
        println("   Es popular: ${cancion.esPopular}")
        println("----------")
    }
}

fun imprimirAlbumes(albumes: List<Album>) {
    if (albumes.isEmpty())
        return

    println("Lista de álbumes:")
    for (album in albumes) {
        println("   Nombre: ${album.nombre}")
        println("   Artista: ${album.artista}")
        println("   Cantidad de canciones: ${album.cantidadCanciones}")
        println("   Género: ${album.genero}")
        println("   Duración del álbum: ${album.duracionTotal}")
        println("   Fecha de creación: ${album.fechaCreacion}")
        println("----------")
    }
}