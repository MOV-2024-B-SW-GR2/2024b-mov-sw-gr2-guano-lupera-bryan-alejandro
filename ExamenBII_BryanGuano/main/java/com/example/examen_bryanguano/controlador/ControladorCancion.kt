package com.example.examen_bryanguano.controlador

import android.content.ContentValues
import android.content.Context
import com.example.examen_bryanguano.entidad.Cancion
import com.example.examen_bryanguano.persistencia.SqliteHelper

class ControladorCancion(context: Context) {
    private val dbHelper = SqliteHelper(context)

    fun crearCancion(idAlbum: Int, cancion: Cancion) {
        dbHelper.writableDatabase.use { db ->
            db.insertOrThrow("Cancion", null, cancion.toContentValues(idAlbum))
        }
    }

    fun listarCancionesPorAlbum(idAlbum: Int): List<Cancion> {
        val canciones = mutableListOf<Cancion>()
        dbHelper.readableDatabase.use { db ->
            db.rawQuery(
                "SELECT * FROM Cancion WHERE idAlbum = ?",
                arrayOf(idAlbum.toString())
            ).use { cursor ->
                while (cursor.moveToNext()) {
                    canciones.add(cursor.toCancion())
                }
            }
        }
        return canciones
    }

    fun actualizarCancion(cancion: Cancion): Boolean {
        return dbHelper.writableDatabase.use { db ->
            val rowsUpdated = db.update(
                "Cancion",
                cancion.toContentValues(),
                "idCancion = ?",
                arrayOf(cancion.idCancion.toString())
            )
            rowsUpdated > 0
        }
    }

    fun eliminarCancion(idCancion: Int): Boolean {
        return dbHelper.writableDatabase.use { db ->
            val rowsDeleted = db.delete(
                "Cancion",
                "idCancion = ?",
                arrayOf(idCancion.toString())
            )
            rowsDeleted > 0
        }
    }

    fun depurarCanciones() {
        dbHelper.readableDatabase.use { db ->
            db.rawQuery("SELECT * FROM Cancion", null).use { cursor ->
                while (cursor.moveToNext()) {
                    println(cursor.toCancion().formatToString())
                }
            }
        }
    }

    private fun Cancion.toContentValues(idAlbum: Int? = null): ContentValues {
        return ContentValues().apply {
            put("titulo", titulo)
            put("artista", artista)
            put("genero", genero)
            put("duracion", duracion)
            idAlbum?.let {
                put("idAlbum", it)
            }
        }
    }

    private fun android.database.Cursor.toCancion(): Cancion {
        return Cancion(
            getInt(getColumnIndexOrThrow("idCancion")),
            getString(getColumnIndexOrThrow("titulo")),
            getString(getColumnIndexOrThrow("artista")),
            getString(getColumnIndexOrThrow("genero")),
            getDouble(getColumnIndexOrThrow("duracion"))
        )
    }

    private fun Cancion.formatToString(): String {
        return "Cancion: $idCancion, Título: $titulo, Artista: $artista, Género: $genero, Duración: $duracion"
    }
}