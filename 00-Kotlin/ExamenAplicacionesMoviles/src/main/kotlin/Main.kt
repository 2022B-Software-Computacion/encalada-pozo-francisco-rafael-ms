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
    open fun setParameter(index: Int,content: Any){
}}

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
    override fun setParameter(index: Int,content: Any){
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
    ObjectOutputStream(file.outputStream()).use {
        it.writeObject(userData)
    }
}

fun loadData(file: File): DataFile? {
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
                var genreName: String = scanner.next()
                var startPeriod: String = scanner.next()
                var startCountry: String = scanner.next()
                var signature: String = scanner.next()
                var bmpAverage: Double = scanner.nextDouble()
                dataFile = Genre(genreName, startPeriod, startCountry, signature, bmpAverage)
                var genreFile: File = File("DataFiles\\$genreName","$genreName.bin")
                saveData(dataFile, genreFile)
                println(dataFile)
                break
            }
            2 -> {
                var bandName: String = scanner.next()
                var creationYear: Int = scanner.nextInt()
                var genrePath: String = scanner.next()
                var file: File? = loadFiles(genrePath)
                if (file == null) {
                    println("Insert a valid Genre")
                    break
                }
                var genre: Genre = loadData(file) as Genre
                var typeOfBand: Char = scanner.next() as Char
                var analogicalInstruments: Boolean = scanner.nextBoolean()
                dataFile = Band(bandName, creationYear, genre, typeOfBand, analogicalInstruments)
                println(dataFile)
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
    //val rock = Genre("Rock", LocalDate.of(1980, 7, 7), "UE-UK", "4/4", Pair(80, 120))
    //val rockFile = File("DataFiles", "rockFile.bin")

    //saveData(rock, rockFile)
    //val savedFile = loadData(rockFile)

    iterateMainMenu()

}