package com.example.examen_bryanguano.controlador

import android.content.ContentValues
import android.content.Context
import com.example.examen_bryanguano.entidad.Album
import com.example.examen_bryanguano.persistencia.SqliteHelper

class ControladorAlbum(context: Context) {
    private val dbHelper = SqliteHelper(context)

    fun crearAlbum(album: Album) {
        dbHelper.writableDatabase.use { db ->
            db.insert("Album", null, album.toContentValues())
        }
    }

    fun listarAlbum(): List<Album> {
        val albumes = mutableListOf<Album>()
        dbHelper.readableDatabase.use { db ->
            db.rawQuery("SELECT * FROM Album", null).use { cursor ->
                while (cursor.moveToNext()) {
                    albumes.add(cursor.toAlbum())
                }
            }
        }
        return albumes
    }

    fun actualizarAlbum(album: Album): Boolean {
        return dbHelper.writableDatabase.use { db ->
            val rowsUpdated = db.update(
                "Album",
                album.toContentValues(),
                "idAlbum = ?",
                arrayOf(album.idAlbum.toString())
            )
            rowsUpdated > 0
        }
    }

    fun eliminarAlbum(idAlbum: Int): Boolean {
        return dbHelper.writableDatabase.use { db ->
            val rowsDeleted = db.delete(
                "Album",
                "idAlbum = ?",
                arrayOf(idAlbum.toString())
            )
            rowsDeleted > 0
        }
    }

    private fun Album.toContentValues(): ContentValues {
        return ContentValues().apply {
            put("nombre", nombre)
            put("artista", artista)
            put("cantidadCanciones", cantidadCanciones)
            put("genero", genero)
            put("duracionTotal", duracionTotal)
        }
    }

    private fun android.database.Cursor.toAlbum(): Album {
        return Album(
            getInt(getColumnIndexOrThrow("idAlbum")),
            getString(getColumnIndexOrThrow("nombre")),
            getString(getColumnIndexOrThrow("artista")),
            getInt(getColumnIndexOrThrow("cantidadCanciones")),
            getString(getColumnIndexOrThrow("genero")),
            getDouble(getColumnIndexOrThrow("duracionTotal"))
        )
    }
//    private val dbHelper = SqliteHelper(context)
//
//    fun crearAlbum(album: Album) {
//        val db = dbHelper.writableDatabase
//        val valores = ContentValues().apply {
//            put("nombre", album.nombre)
//            put("artista", album.artista)
//            put("cantidadCanciones", album.cantidadCanciones)
//            put("genero", album.genero)
//            put("duracionTotal", album.duracionTotal)
//        }
//        db.insert("Album", null, valores)
//        db.close()
//    }
//
//    fun listarAlbum(): List<Album> {
//        val db = dbHelper.writableDatabase
//        val cursor = db.rawQuery("SELECT * FROM Album", null)
//        val albumes = mutableListOf<Album>()
//        while (cursor.moveToNext()) {
//            val idAlbum = cursor.getInt(0)
//            val nombre = cursor.getString(1)
//            val artista = cursor.getString(2)
//            val cantidadCanciones = cursor.getInt(3)
//            val genero = cursor.getString(4)
//            val duracionTotal = cursor.getDouble(5)
//
//            albumes.add(Album(idAlbum, nombre, artista, cantidadCanciones, genero, duracionTotal))
//        }
//        cursor.close()
//        db.close()
//        return albumes
//    }
//
//    fun actualizarAlbum(album: Album): Boolean {
//        val db = dbHelper.writableDatabase
//        val valores = ContentValues().apply {
//            put("nombre", album.nombre)
//            put("artista", album.artista)
//            put("cantidadCanciones", album.cantidadCanciones)
//            put("genero", album.genero)
//            put("duracionTotal", album.duracionTotal)
//        }
//        val rows = db.update(
//            "Album",
//            valores,
//            "idAlbum = ?",
//            arrayOf(album.idAlbum.toString())
//        )
//        db.close()
//        return rows > 0
//    }
//
//    fun eliminarAlbum(idAlbum: Int): Boolean {
//        val db = dbHelper.writableDatabase
//        val rows = db.delete(
//            "Album",
//            "idAlbum = ?",
//            arrayOf(idAlbum.toString())
//        )
//        db.close()
//        return rows > 0
//    }
}
