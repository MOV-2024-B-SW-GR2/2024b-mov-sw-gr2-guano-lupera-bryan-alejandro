package com.example.examen_bryanguano.vistas

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examen_bryanguano.R
import com.example.examen_bryanguano.controlador.ControladorAlbum
import com.example.examen_bryanguano.entidad.Album

class AlbumDetalle : AppCompatActivity() {
    private var idAlbum: Int? = null
    private lateinit var controladorAlbum: ControladorAlbum
    private lateinit var tv_nombre_album: EditText
    private lateinit var tv_artista_album: EditText
    private lateinit var tv_cantidad_canciones_album: EditText
    private lateinit var tv_genero_album: EditText
    private lateinit var tv_duracion_total_album: EditText
    private lateinit var tv_longitud: EditText
    private lateinit var tv_latitud: EditText
    private lateinit var btn_guardar_album: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_album_detalle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        controladorAlbum = ControladorAlbum(this)

        tv_nombre_album = findViewById(R.id.tv_nombre_album)
        tv_artista_album = findViewById(R.id.tv_artista_album)
        tv_cantidad_canciones_album = findViewById(R.id.tv_cantidad_canciones_album)
        tv_genero_album = findViewById(R.id.tv_genero_album)
        tv_duracion_total_album = findViewById(R.id.tv_duracion_total_album)
        tv_longitud = findViewById(R.id.tv_longitud)
        tv_latitud = findViewById(R.id.tv_latitud)

        btn_guardar_album = findViewById(R.id.btn_guardar_album)
        val tv_formulario_album: TextView = findViewById(R.id.tv_formulario_album)

        idAlbum = intent.getIntExtra("idAlbum", -1).takeIf { it != -1 }

        if (idAlbum != null) {
            tv_formulario_album.text = "Editar el Álbum"
            val album = controladorAlbum.listarAlbum().find { it.idAlbum == idAlbum }
            album?.let {
                tv_nombre_album.setText(it.nombre)
                tv_artista_album.setText(it.artista)
                tv_cantidad_canciones_album.setText(it.cantidadCanciones.toString())
                tv_genero_album.setText(it.genero)
                tv_duracion_total_album.setText(it.duracionTotal.toString())
                tv_longitud.setText(it.longitud.toString())
                tv_latitud.setText(it.latitud.toString())
            }
        } else {
            tv_formulario_album.text = "Agregar Álbum"
        }

        btn_guardar_album.setOnClickListener {
            val nombre = tv_nombre_album.text.toString()
            val artista = tv_artista_album.text.toString()
            val cantidadCanciones = tv_cantidad_canciones_album.text.toString().toIntOrNull()
            val genero = tv_genero_album.text.toString()
            val duracionTotal = tv_duracion_total_album.text.toString().toDoubleOrNull()
            val longitud = tv_longitud.text.toString().toDoubleOrNull() ?: 0.0
            val latitud = tv_latitud.text.toString().toDoubleOrNull() ?: 0.0

            if (nombre.isNotEmpty()
                && artista.isNotEmpty()
                && cantidadCanciones != null
                && genero.isNotEmpty()
                && duracionTotal != null
                && longitud != null
                && latitud != null
            ) {
                if (idAlbum != null) {
                    controladorAlbum.actualizarAlbum(
                        Album(
                            idAlbum!!,
                            nombre,
                            artista,
                            cantidadCanciones,
                            genero,
                            duracionTotal,
                            longitud,
                            latitud
                        )
                    )
                    Toast.makeText(this, "Álbum actualizado correctamente", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    controladorAlbum.crearAlbum(
                        Album(
                            0,
                            nombre,
                            artista,
                            cantidadCanciones,
                            genero,
                            duracionTotal,
                            longitud,
                            latitud
                        )
                    )
                    Toast.makeText(this, "Álbum creado", Toast.LENGTH_SHORT).show()
                }
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Llenar todos los campos antes de actualizar.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}