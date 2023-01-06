import java.util.Date

data class Genero(
    val nombreGenero: String,
    val periodoAparicion: Date,
    val lugarAparicion: String,
    val compasUsado: String,
    val rangoTempo: Pair<Int, Int>);

data class Banda(
    val nombreBanda: String,
    val fechaCreacion: Date,
    val lugarCreacion: String,

);