package com.example.examen_bryanguano.vistas

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examen_bryanguano.R
import com.example.examen_bryanguano.controlador.ControladorAlbum
import com.example.examen_bryanguano.controlador.ControladorCancion
import com.example.examen_bryanguano.entidad.Cancion

class CancionList : AppCompatActivity() {
    private lateinit var controladorCancion: ControladorCancion
    private lateinit var controladorAlbum: ControladorAlbum
    private lateinit var tv_titulo_album_cancion: TextView
    private lateinit var lv_canciones: ListView
    private lateinit var btn_agregar_cancion: Button
    private var idAlbum: Int = 0
    private var cancionSeleccionada: Cancion? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cancion_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        controladorAlbum = ControladorAlbum(this)
        controladorCancion = ControladorCancion(this)

        tv_titulo_album_cancion = findViewById(R.id.tv_titulo_album_cancion)
        lv_canciones = findViewById(R.id.lv_canciones)
        btn_agregar_cancion = findViewById(R.id.btn_agregar_cancion)

        idAlbum = intent.getIntExtra("idAlbum", 0)

        val album = controladorAlbum.listarAlbum().find { it.idAlbum == idAlbum }
        if (album != null) {
            tv_titulo_album_cancion.text = album.nombre
        } else {
            tv_titulo_album_cancion.text = "Álbum no encontrado"
        }

        btn_agregar_cancion.setOnClickListener {
            val intent = Intent(this, CancionDetalle::class.java)
            intent.putExtra("idAlbum", idAlbum)
            startActivity(intent)
        }

        registerForContextMenu(lv_canciones)
        actualizarLista()
    }


    private fun actualizarLista() {
        val canciones = controladorCancion.listarCancionesPorAlbum(idAlbum)
        if (canciones.isEmpty()) {
            Toast.makeText(this, "No hay canciones en este album", Toast.LENGTH_SHORT).show()
        }
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, canciones.map { it.titulo })
        lv_canciones.adapter = adapter

        lv_canciones.setOnItemLongClickListener { _, _, position, _ ->
            cancionSeleccionada = canciones[position]
            false
        }
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_cancion, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.im_editar_cancion -> {
                val intent = Intent(this, CancionDetalle::class.java)
                intent.putExtra("idAlbum", idAlbum)
                intent.putExtra("idCancion", cancionSeleccionada?.idCancion)
                startActivity(intent)
            }

            R.id.im_eliminar_cancion -> {
                if (cancionSeleccionada != null) {
                    controladorCancion.eliminarCancion(cancionSeleccionada!!.idCancion)
                    Toast.makeText(this, "Cancion eliminada correctamente.", Toast.LENGTH_SHORT).show()
                    actualizarLista()
                }
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        controladorCancion.depurarCanciones()
        actualizarLista()
    }

}