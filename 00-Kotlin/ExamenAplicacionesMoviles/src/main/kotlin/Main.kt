import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.time.LocalDate
import java.util.Scanner
import java.util.StringJoiner

//Global variables
val scanner = Scanner(System.`in`)
var selectedOption = 0

open class DataFile(
    var firstParameter: Any?,
    var secondParameter: Any?,
    var thirdParameter: Any?,
    var fourthParameter: Any?,
    var fifthParameter: Any?,
) : java.io.Serializable {
    open fun setParameter(index: Int, content: Any) {
    }
}

/*
data class Genre(
    override var firstParameter: Any?,  //Genre
    override var secondParameter: Any?, //startDate
    override var thirdParameter: Any?,  //startCountry
    override var fourthParameter: Any?, //signature
    override var fifthParameter: Any?,  //bpmRange
) : DataFile(firstParameter, secondParameter, thirdParameter, fourthParameter, fifthParameter)

data class Band(
    override var firstParameter: Any?,  //bandName
    override var secondParameter: Any?, //creationDate
    override var thirdParameter: Any?,  //genreName
    override var fourthParameter: Any?, //startLocation
    override var fifthParameter: Any?,  //creatorsName
) : DataFile(firstParameter, secondParameter, thirdParameter, fourthParameter, fifthParameter)
*/
data class Genre(
    var genreName: String,
    var startPeriod: String,
    var startCountry: String,
    var signature: String,
    var bpmAverage: Double,
) : DataFile(genreName, startPeriod, startCountry, signature, bpmAverage) {
    override fun setParameter(index: Int, content: Any) {
        this.genreName = content.toString()
    }
}


data class Band(
    var bandName: String,
    var creationYear: Int,
    var genre: Genre,
    var typeOfBand: Char,
    var analogicalInstruments: Boolean,
) : DataFile(bandName, genre, creationYear, typeOfBand, analogicalInstruments)

fun saveData(userData: DataFile, file: File) {
    file.parentFile.mkdir()
    if (!file.exists()) file.createNewFile()
    ObjectOutputStream(file.outputStream()).use {
        it.writeObject(userData)
    }
}

fun loadData(directory: String): File? {
    return File(directory)
}

fun fileToDataFile(file: File): DataFile? {
    ObjectInputStream(file.inputStream()).use {
        return it.readObject() as? DataFile
    }
}

fun loadFiles(directory: String): File? {
    val directory = File(directory)
    val files = directory.listFiles()
    for (file in files) {
        if (file.isDirectory) {
            loadFiles(file.absolutePath)
        } else {
            println(file.absolutePath)
            return file
        }
    }
    return null
}

//CRUD OPERATIONS
//Create
fun createFile() {
    println("What do you want to create?")
    var dataFile: DataFile

    while (true) {
        showFileOptions()
        selectedOption = scanner.nextInt()
        when (selectedOption) {
            1 -> {
                print("\tIngrese el nombre del genero: ")
                var genreName: String = scanner.next()

                print("\tIngrese el periodo de aparecimiento del genero: ")
                var startPeriod: String = scanner.next()

                print("\tIngrese el pais de aparecimiento del genero: ")
                var startCountry: String = scanner.next()

                print("\tIngrese las tempo del genero: ")
                var signature: String = scanner.next()

                print("\tIngrese los bpm del genero: ")
                var bmpAverage: Double = scanner.nextDouble()

                dataFile = Genre(genreName, startPeriod, startCountry, signature, bmpAverage)
                var genreFile = File("DataFiles\\$genreName", "$genreName.bin")
                saveData(dataFile, genreFile)
                break
            }
            2 -> {
                print("\tIngrese el nombre de la banda: ")
                var bandName: String = scanner.next()

                print("\tIngrese el año de creacion de la banda: ")
                var creationYear: Int = scanner.nextInt()

                print("\tGenero al que pertenece la banda: ")
                File("DataFiles").listFiles { _, name -> !name.contains(".") }
                    .forEach {
                        println("\t\t- ${it.name}")
                    }
                print("\tIngrese el genero: ")
                var genrePath: String = scanner.next()
                var file: File? = loadData("DataFiles\\$genrePath\\$genrePath.bin")
                if (file == null) {
                    println("Insert a valid Genre")
                    break
                }
                var genre: Genre = fileToDataFile(file) as Genre

                print("\tTipo de banda:" +
                        "\n\t\t1. i(instrumental)" +
                        "\n\t\t2. v(with vocals)" +
                        "\n\t\t3. o(only vocals)" +
                        "\n\tIngrese el tipo de banda: ")
                var typeOfBand = 'v'
                when(scanner.nextInt()){
                    1 -> typeOfBand = 'i'
                    3 -> typeOfBand = 'o'
                    else -> println("\tSe ha seleccionado \"only vocals\" por defecto")
                }


                print("\t¿La banda es analogica o es digital?" +
                        "\n\t\t1. Analogica" +
                        "\n\t\t2. Digital" +
                        "\n\tIngrese la opcion: ")
                var analogicalInstrumentsOption: Int = scanner.nextInt()
                var analogicalInstruments = analogicalInstrumentsOption==1

                dataFile = Band(bandName, creationYear, genre, typeOfBand, analogicalInstruments)
                var bandFile = File("DataFiles\\${genre.genreName}", "$bandName.bin")
                print(bandFile)
                saveData(dataFile, bandFile)
                break
            }
        }
    }


}


//Read
fun readFile() {

}

//Update
fun updateFile() {

}

//Delete
fun deleteFile() {

}

fun showMenu() {
    println("\t\tGENRE - BAND")
    println("1. Create a new File")
    println("2. Read a File")
    println("3. Update a File")
    println("4. Delete a File")
    println("5. Exit")
}

fun showFileOptions() {
    println("1. Genre")
    println("2. Band")
}


fun iterateMainMenu() {
    while (true) {
        showMenu()
        selectedOption = scanner.nextInt()
        when (selectedOption) {
            1 -> createFile()
            2 -> readFile()
            3 -> updateFile()
            4 -> deleteFile()
            5 -> break
            else -> println("You had select an invalid option")
        }
    }
}


fun main() {
    //val rock = Genre("Rock", "1980", "UE-UK", "4/4", 120.0)
    //val rockFile = File("DataFiles", "rockFile.bin")

    //saveData(rock, rockFile)
    //val savedFile = loadData(rockFile)

    iterateMainMenu()

}