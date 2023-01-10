package com.example.android_1

class BEntrenador(
    val nombre: String?,
    val descripcion: String?,
) {
    override fun toString(): String {
        return "$nombre - $descripcion"
    }
}