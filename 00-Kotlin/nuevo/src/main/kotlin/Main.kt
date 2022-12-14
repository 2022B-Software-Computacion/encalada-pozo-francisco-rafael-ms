import java.util.*

// Main.kt
fun main() {
    println("Hola")

    //TIPOS DE VARIABLES

    //Inmutables (No se reasignan)
    val inmutable: String = "Rafael";
    // No permite -> inmutable = "Francisco";

    //Mutables
    var mutable: String = "Rafael";
    mutable = "Francisco";

    //SINTAXIS DUCK TYPING
    val ejemploVariable = "Ejemplo";
    val edadEjemplo: Int = 12;
    ejemploVariable.trim();

    //VARIABLES PRIMITIVAS
    val nombreProfesor: String = "Adrian Eguez"
    val sueldo: Double = 1.2;
    val estadoCivil: Char = 'S'
    val mayorEdad: Boolean = true;

    //VARIABLES JAVA
    val fechaNacimiento: Date = Date();

    if (true) {
    } else {
    }
    //switch no existe

    when (estadoCivil) {
        ('S') -> {
            println("acercarse")
        }
        ('C') -> {
            println("alejarse")
        }
        ('U') -> {
            println("no reconocido")
        }
    }

    val coqueteo = if (estadoCivil == 'S') "SI" else "NO"
}

//FUNCIONES
fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}")
}

//Segunda parte

abstract class NumerosJava {
    protected val numeroUno: Int;
    private val numeroDos: Int;

    constructor(
            uno: Int
            dos: Int
    ) { //bloque codigo constructor
        this.numeroUno = uno;
        this.numeroDos = dos;
        println("Inicializado")
    }
}

class Suma(
        uno: Int,
        dos: Int
) : Numeros(uno, dos) {
    init {
        this.numeroUno
        this.numeroDos
    }
    constructor(
            uno: Int?,
            dos: Int
    ):this( //llamar al constructor primario
            if(uno == null) 0 else uno,
            dos
    )
}

abstract class Numeros(
        uno: Int
) {
    var cedula: String = "";

    init {
        this.numeroUno = uno;
        this.cedula = dos;
    }
}

//Tercera parte

//Cuarta parte
//FILTER
// 1) Devolver una expresion TRUE/FALSE
// 2) Nuevo arreglo filtrado
val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

val respuestaAll: Boolean = arregloDinamico
        .all { valorActual: Int ->
            return@all (valorActual > 5)

        }

//REDUCE = VALOR ACUMULADO
val respuestaReduce: Int = arregloDinamico
        .reduce { acumulado: Int, valorActual: Int ->
            return@reduce (acumulado + valorActual)
        }
