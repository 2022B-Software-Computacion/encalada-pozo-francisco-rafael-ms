package com.example.android_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class CIntentExplicitoParametros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cintent_explicito_parametros)
        val nombre = intent.getStringExtra("nombre")
        val apellido = intent.getBundleExtra("apellido")
        val edad = intent.getIntExtra("edad", 0)
        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)
        boton
            .setOnClickListener{devolverRespuesta()}
    }

    fun devolverRespuesta(){
        val intentDevolverParametros = Intent()
        intentDevolverParametros.putExtra("nombreModificado", "Rafa")
        intentDevolverParametros.putExtra("edadModificado", 25)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }
}