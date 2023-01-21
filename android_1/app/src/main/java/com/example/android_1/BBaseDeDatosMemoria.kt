package com.example.android_1

class BBaseDeDatosMemoria {
    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador
                .add(
                    BEntrenador(1,"Francisco", "a@a.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(2,"Vicente", "b@b.com")
                )
            arregloBEntrenador
                .add(
                    BEntrenador(3,"Carolina", "c@c.com")
                )
        }
    }
}