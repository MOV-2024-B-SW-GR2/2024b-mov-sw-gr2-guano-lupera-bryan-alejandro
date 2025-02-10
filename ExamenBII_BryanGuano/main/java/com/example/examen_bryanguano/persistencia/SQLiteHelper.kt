package com.example.examen_bryanguano.persistencia

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper(context: Context?) : SQLiteOpenHelper(
    context,
    "AppMusicaDB",
    null,
    5
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSqlCrearAlbum = """
            CREATE TABLE Album (
                idAlbum INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(250),
                artista VARCHAR(250),
                cantidadCanciones INTEGER,
                genero VARCHAR(250),
                duracionTotal REAL,
                longitud REAL NOT NULL,
                latitud REAL
            )
        """.trimIndent()
        db?.execSQL(scriptSqlCrearAlbum)

        val scriptSqlCrearCancion = """
            CREATE TABLE Cancion (
                idCancion INTEGER PRIMARY KEY AUTOINCREMENT,
                titulo VARCHAR(250),
                artista VARCHAR(250),
                genero VARCHAR(250),
                duracion REAL,
                idAlbum INTEGER,                
                FOREIGN KEY (idAlbum) REFERENCES Album(idAlbum) ON DELETE CASCADE
            )
        """.trimIndent()
        db?.execSQL(scriptSqlCrearCancion)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS Cancion")
            db?.execSQL("DROP TABLE IF EXISTS Album")
            onCreate(db)
        }
    }

    override fun onConfigure(db: SQLiteDatabase?) {
        super.onConfigure(db)
        db?.setForeignKeyConstraintsEnabled(true) // Habilita las claves foráneas
    }
}
