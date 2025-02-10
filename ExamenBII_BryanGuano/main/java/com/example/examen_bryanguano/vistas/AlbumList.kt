package com.example.examen_bryanguano.vistas

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.examen_bryanguano.R
import com.example.examen_bryanguano.controlador.ControladorAlbum
import com.example.examen_bryanguano.entidad.Album

class AlbumList : AppCompatActivity() {
    private lateinit var controladorAlbum: ControladorAlbum
    private lateinit var lv_albumes: ListView
    private lateinit var btn_agregar_album: Button
    private var albumSeleccionado: Album? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_album_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        controladorAlbum = ControladorAlbum(this)

        insertarAlbumQuemado()

        lv_albumes = findViewById(R.id.lv_albumes)
        btn_agregar_album = findViewById(R.id.btn_agregar_album)

        btn_agregar_album.setOnClickListener {
            val intent = Intent(this, AlbumDetalle::class.java)
            startActivity(intent)
        }

        registerForContextMenu(lv_albumes)
        actualizarLista()
    }

    private fun insertarAlbumQuemado() {
        val existingAlbum = controladorAlbum.listarAlbum().find { it.nombre == "Álbum Quemado" }
        if (existingAlbum == null) {
            controladorAlbum.crearAlbum(Album(0, "Álbum Quemado", "Yo", 4, "pop", 4.4, -0.08884207491807569, -78.43977998196692))
        }
    }

    private fun actualizarLista() {
        val albumes = controladorAlbum.listarAlbum()

        if (albumes.isNotEmpty()) {
            val adapter =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, albumes.map { it.nombre })
            lv_albumes.adapter = adapter
        }
        lv_albumes.setOnItemClickListener { _, _, position, _ ->
            albumSeleccionado = albumes[position]
            lv_albumes.showContextMenu()
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_album, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.im_listar_canciones -> {
                val intent = Intent(this, CancionList::class.java)
                intent.putExtra("idAlbum", albumSeleccionado?.idAlbum)
                startActivity(intent)
            }
            R.id.im_editar_album -> {
                val intent = Intent(this, AlbumDetalle::class.java)
                intent.putExtra("idAlbum", albumSeleccionado?.idAlbum)
                startActivity(intent)
            }

            R.id.im_eliminar_album -> {
                if (albumSeleccionado != null) {
                    controladorAlbum.eliminarAlbum(albumSeleccionado!!.idAlbum)
                    Toast.makeText(this, "Álbum eliminado correctamente.", Toast.LENGTH_SHORT)
                        .show()
                    actualizarLista()
                }
            }
            R.id.im_mapa_disquera -> {
                val intent = Intent(this, MapsActivity::class.java)

                intent.putExtra("latitud", albumSeleccionado?.latitud)
                intent.putExtra("longitud", albumSeleccionado?.longitud)
                startActivity(intent)
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        actualizarLista()
    }
}