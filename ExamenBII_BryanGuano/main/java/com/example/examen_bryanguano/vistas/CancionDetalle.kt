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
import com.example.examen_bryanguano.controlador.ControladorCancion
import com.example.examen_bryanguano.entidad.Cancion

class CancionDetalle : AppCompatActivity() {
    private lateinit var controladorCancion: ControladorCancion
    private lateinit var tv_titulo_cancion: EditText
    private lateinit var tv_artista_cancion: EditText
    private lateinit var tv_genero_cancion: EditText
    private lateinit var tv_duracion_cancion: EditText
    private lateinit var btn_guardar_cancion: Button
    private var idAlbum: Int = 0
    private var idCancion: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cancion_detalle)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        controladorCancion = ControladorCancion(this)
        tv_titulo_cancion = findViewById(R.id.tv_titulo_cancion)
        tv_artista_cancion = findViewById(R.id.tv_artista_cancion)
        tv_genero_cancion = findViewById(R.id.tv_genero_cancion)
        tv_duracion_cancion = findViewById(R.id.tv_duracion_cancion)
        btn_guardar_cancion = findViewById(R.id.btn_guardar_cancion)
        val tv_formulario_cancion: TextView = findViewById(R.id.tv_formulario_cancion)

        idAlbum = intent.getIntExtra("idAlbum", 0)
        idCancion = intent.getIntExtra("idCancion", 0).takeIf { it != 0 }

        tv_formulario_cancion.text = if (idCancion != null) "Editar canción" else "Agregar canción"

        if (idCancion != null) {
            val cancion = controladorCancion.listarCancionesPorAlbum(idAlbum)
                .find { it.idCancion == idCancion }
            cancion?.let {
                tv_titulo_cancion.setText(it.titulo)
                tv_artista_cancion.setText(it.artista)
                tv_genero_cancion.setText(it.genero)
                tv_duracion_cancion.setText(it.duracion.toString())
            }
        }
        btn_guardar_cancion.setOnClickListener {
            guardarCancion()
        }
    }

    private fun guardarCancion() {
        val titulo = tv_titulo_cancion.text.toString()
        val artista = tv_artista_cancion.text.toString()
        val genero = tv_genero_cancion.text.toString()
        val duracion = tv_duracion_cancion.text.toString().toDoubleOrNull()

        if (titulo.isNotEmpty() && artista.isNotEmpty() && genero.isNotEmpty() && duracion != null) {
            if (idCancion != null) {
                controladorCancion.actualizarCancion (
                    Cancion(idCancion!!, titulo, artista, genero, duracion)
                )
                Toast.makeText(this, "Canción actualizada", Toast.LENGTH_SHORT).show()
            } else {
                controladorCancion.crearCancion(idAlbum, Cancion(0, titulo, artista, genero, duracion))
                Toast.makeText(this, "Canción creada", Toast.LENGTH_SHORT).show()
            }
            finish()
        } else {
            Toast.makeText(this, "Llenar todos los campos antes de actualizar.", Toast.LENGTH_SHORT).show()
        }
    }
}