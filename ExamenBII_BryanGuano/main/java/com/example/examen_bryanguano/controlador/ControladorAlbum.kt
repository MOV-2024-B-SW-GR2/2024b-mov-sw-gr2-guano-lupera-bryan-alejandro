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
            put("longitud", longitud)
            put("latitud", latitud)
        }
    }

    private fun android.database.Cursor.toAlbum(): Album {
        return Album(
            getInt(getColumnIndexOrThrow("idAlbum")),
            getString(getColumnIndexOrThrow("nombre")),
            getString(getColumnIndexOrThrow("artista")),
            getInt(getColumnIndexOrThrow("cantidadCanciones")),
            getString(getColumnIndexOrThrow("genero")),
            getDouble(getColumnIndexOrThrow("duracionTotal")),
            getDouble(getColumnIndexOrThrow("longitud")),
            getDouble(getColumnIndexOrThrow("latitud"))

        )
    }

    fun obtenerAlbumPorId(idAlbum: Int): Album? {
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Album WHERE idAlbum = ?", arrayOf(idAlbum.toString()))

        var album: Album? = null
        if (cursor.moveToFirst()) {
            val idAlbum = cursor.getInt(0)
            val nombre = cursor.getString(1)
            val artista = cursor.getString(2)
            val cantidadCanciones = cursor.getInt(3)
            val genero = cursor.getString(4)
            val duracionTotal = cursor.getDouble(5)
            val longitud = cursor.getDouble(6)
            val latitud = cursor.getDouble(7)
            album = Album(
                idAlbum,
                nombre,
                artista,
                cantidadCanciones,
                genero,
                duracionTotal,
                longitud,
                latitud
            )
        }

        cursor.close()
        db.close()
        return album
    }
}
