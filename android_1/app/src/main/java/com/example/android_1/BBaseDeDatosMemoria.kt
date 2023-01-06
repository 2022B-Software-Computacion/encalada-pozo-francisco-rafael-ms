package com.example.android_1

class BBaseDeDatosMemoria {
    val arregloBEntrenador = arrayListOf<BEntrenador>()
    init {
        arregloBEntrenador
            .add(
                BEntrenador("Francisco", "a@a.com")
            )
        arregloBEntrenador
            .add(
                BEntrenador("Vicente", "b@b.com")
            )
        arregloBEntrenador
            .add(
                BEntrenador("Carolina", "c@c.com")
            )
    }
}