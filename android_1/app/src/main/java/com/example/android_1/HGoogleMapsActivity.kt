package com.example.android_1

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*

class HGoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hgoogle_maps2)
        solicitarPermisos()
        iniciarLogicaMapa()
        val boton = findViewById<Button>(R.id.btn_ir_carolina)
        boton
            .setOnClickListener {
                irCarolina()
            }
    }

    fun irCarolina(){
        val carolina = LatLng(-0.1825684318486696,
            -78.58447277600916)
        val zoom = 17f
        moverCamaraConZoom(carolina, zoom)
    }

    fun iniciarLogicaMapa(){
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync{ googleMap ->
            if (googleMap != null){
                mapa = googleMap
                establecerConfiguraciónMapa()
                val zoom = 17f
                val quicentro = LatLng(
                    -0.1755,-78.4801
                )
                val titulo = "Quicentro"
                val markQuicentro = anadirMarcador(quicentro,titulo)
                markQuicentro.tag = titulo

                val poliLineaUno = googleMap
                    .addPolyline(
                        PolylineOptions()
                            .clickable(true)
                            .add(
                                LatLng(-0.1759,
                                    -78.4850),
                                LatLng(-0.1763,
                                -78.4826),
                                LatLng(-0.1774,
                                -78.4770)
                            )
                    )
                poliLineaUno.tag = "linea-1"

                //POLIGONO
                val poligonoUno = googleMap
                    .addPolygon(
                        PolygonOptions()
                            .clickable(true)
                            .add(
                                LatLng(-0.1759,
                                    -78.4850),
                                LatLng(-0.1763,
                                    -78.4826),
                                LatLng(-0.1774,
                                    -78.4770)
                            )
                    )
                poligonoUno.fillColor = -0xc771c4
                poligonoUno.tag = "poligono-2"
                escucharListeners()
            }
        }
    }

    fun establecerConfiguraciónMapa(){
        val contexto = this.applicationContext
        with(mapa){
            val permisosFineLocation = ContextCompat
                .checkSelfPermission(
                    contexto,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if (tienePermisos){
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    fun escucharListeners(){
        mapa.setOnPolygonClickListener {
            Log.i("mapa","setOnPolygonClickListener ${it}")
            it.tag //ID
        }
        mapa.setOnPolylineClickListener {
            Log.i("mapa", "setOnPolylineClickListener $it")
            it.tag
        }
        mapa.setOnMarkerClickListener {
            Log.i("mapa", "setOnMarkerClickListener $it")
            it.tag
            return@setOnMarkerClickListener true
        }
        mapa.setOnCameraMoveListener {
            Log.i("mapa", "setOnCameraMoveListener")
        }
        mapa.setOnCameraMoveStartedListener {
            Log.i("mapa", "setOnCameraMoveStartedListener $it")
        }
        mapa.setOnCameraIdleListener {
            Log.i("mapa", "setOnCameraIdleListener")
        }
    }

    fun anadirMarcador(latLng: LatLng, title: String): Marker {
        return mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )!!
    }

    fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng,zoom)
        )
    }

    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val permisosFineLocation = ContextCompat
            .checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if (tienePermisos){
            permisos = true
        }else{
            ActivityCompat.requestPermissions(
                this, //contexto
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
                1 // codigo de peticion de los permisos
            )
        }
    }
}