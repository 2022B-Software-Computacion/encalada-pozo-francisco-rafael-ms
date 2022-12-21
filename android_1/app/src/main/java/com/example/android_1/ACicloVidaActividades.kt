package com.example.android_1

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.android_1.databinding.ActivityAcicloVidaActividadesBinding

class ACicloVidaActividades : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityAcicloVidaActividadesBinding
    var textoGlobal = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityAcicloVidaActividadesBinding.inflate(layoutInflater)
     setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_aciclo_vida_actividades)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        mostrarSnackbar("OnCreate")
    }
    override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_content_aciclo_vida_actividades)
    return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }

    fun mostrarSnackbar(texto:String){
        textoGlobal += texto
        Snackbar.make(findViewById(R.id.cl_ciclo_vida),
            textoGlobal, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun onStart (){
        super.onStart()
        mostrarSnackbar("onStart")
    }
    override fun onResume(){
        super.onResume()
        mostrarSnackbar("onResume")
    }
    override fun onRestart (){
        super.onRestart()
        mostrarSnackbar("onRestart")
    }
    override fun onPause(){
        super.onPause()
        mostrarSnackbar("onPause")
    }
    override fun onStop(){
        super.onStop()
        mostrarSnackbar("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        mostrarSnackbar("onDestroy")
    }


}