package com.example.examen_bryanguano.vistas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.examen_bryanguano.R
import com.example.examen_bryanguano.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapa: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mapa = googleMap

        val latitud = intent.getDoubleExtra("latitud", 0.0)
        val longitud = intent.getDoubleExtra("longitud", 0.0)

        with(mapa.uiSettings) {
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        val ubicacion = LatLng(latitud, longitud)

        mapa.addMarker(
            MarkerOptions()
                .position(ubicacion)
                .snippet("Coordenadas: $latitud, $longitud")
        )

        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 15f))


        mapa.setOnMarkerClickListener { marker ->
            marker.showInfoWindow()
            true
        }
    }
}