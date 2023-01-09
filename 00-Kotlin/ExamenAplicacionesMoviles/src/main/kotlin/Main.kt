import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.time.LocalDate
import java.util.Scanner

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
    var genreName: String?,
    var startPeriod: LocalDate?,
    var startCountry: String?,
    var signature: String?,
    var bpmRange: Pair<Int, Int>?,
) : DataFile(genreName, startPeriod, startCountry, signature, bpmRange) {
    override fun setParameter(index: Int,content: Any){
        this.genreName = content.toString()
    }
}



data class Band(
    var bandName: String?,
    var creationDate: LocalDate?,
    var genreName: String?,
    var startLocation: String?,
    var creatorsName: String?,
) : DataFile(bandName, genreName, creatorsName, startLocation, creatorsName)

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

//CRUD OPERATIONS
//Create
fun createFile() {
    println("What do you want to create?")
    var dataFile = iterateFileOptions()
    /*for (field in dataFile::class.java.declaredFields) {
        field.trySetAccessible()
        println("Insert the \"${field.name}\"")
    }*/
    for(index in 0..dataFile::class.java.declaredFields.size-2){
        println("Insert the \"${dataFile::class.java.declaredFields[index].name}\"")
        dataFile.setParameter(index, scanner.next())
    }
    println(dataFile)
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

fun iterateFileOptions(): DataFile {
    while (true) {
        showFileOptions()
        selectedOption = scanner.nextInt()
        when (selectedOption) {
            1 -> {
                return Genre("", null, null, null, null)
            }
            2 -> {
                return Band(null, null, null, null, null)
            }
        }
    }

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