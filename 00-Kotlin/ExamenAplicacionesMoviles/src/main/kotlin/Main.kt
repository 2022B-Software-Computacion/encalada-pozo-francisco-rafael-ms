import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

//Global variables
val scanner = Scanner(System.`in`)
var input = "0"
const val printGreen = "\u001B[3m\u001B[32m"
const val printBlue = "\u001b[34m"
const val printRed = "\u001b[31m"
const val printReset = "\u001B[0m"


fun saveData(userData: DataFile, file: File) {
    file.parentFile.mkdir()
    if (!file.exists()) file.createNewFile()
    ObjectOutputStream(file.outputStream()).use {
        it.writeObject(userData)
    }
}

fun readInput(): String {
    print("\n${printGreen}admin>${printReset} ")
    return scanner.next()
}

fun loadData(directory: String): File {
    return File(directory)
}

fun fileToDataFile(file: File): DataFile? {
    ObjectInputStream(file.inputStream()).use {
        return it.readObject() as? DataFile
    }
}

fun loadFiles(directory: String): File? {
    val dir = File(directory)
    val files = dir.listFiles()
    for (file in files!!) {
        if (file.isDirectory) {
            loadFiles(file.absolutePath)
            print(file)
        } else {
            return file
        }
    }
    return null
}

//CRUD OPERATIONS
//Create
fun createFile() {
    println("What do you want to create?")
    val dataFile: DataFile

    while (true) {
        showFileOptions()
        input = readInput()
        when (input) {
            "1" -> {
                print("\tEnter the Genre's name: ")
                val genreName: String = scanner.next()

                print("\tEnter the Genre's appearance period: ")
                val startPeriod: String = scanner.next()

                print("\tEnter the Gender's appearance country: ")
                val startCountry: String = scanner.next()

                print("\tEnter the Gender's signature: ")
                val signature: String = scanner.next()

                print("\tEnter the Gender's BPM (Bits Per Minute): ")
                val bmpAverage: Double = scanner.nextDouble()

                dataFile = Genre(genreName, startPeriod, startCountry, signature, bmpAverage)
                val genreFile = File("DataFiles\\$genreName", "$genreName.bin")
                saveData(dataFile, genreFile)
                break
            }
            "2" -> {
                print("\tEnter the Band's name: ")
                val bandName: String = scanner.next()

                print("\tEnter the Band's creation year: ")
                val creationYear: Int = scanner.nextInt()

                println("\tBand's gender: ")
                File("DataFiles").listFiles { _, name -> !name.contains(".") }
                    ?.forEach {
                        println("\t\t- ${it.name}")
                    }
                print("\tEnter the gender: ")
                val genrePath: String = scanner.next()
                val file: File = loadData("DataFiles\\$genrePath\\$genrePath.bin")
                if (!file.isFile) {
                    println("Insert a valid Genre")
                    break
                }
                val genre: Genre = fileToDataFile(file) as Genre

                print(
                    "\tBand's type:" +
                            "\n\t\t1. i(instrumental)" +
                            "\n\t\t2. v(with vocals)" +
                            "\n\t\t3. o(only vocals)" +
                            "\n\tEnter the Band's type: "
                )
                var typeOfBand = 'v'
                when (readInput()) {
                    "1" -> typeOfBand = 'i'
                    "2" -> typeOfBand = 'v'
                    "3" -> typeOfBand = 'o'
                    else -> println("\tWas selected \"with vocals\" by defect")
                }


                print(
                    "\tThe band is analogical or digital?" +
                            "\n\t\t1. Analogical" +
                            "\n\t\t2. Digital" +
                            "\n\tEnter an option: "
                )
                val analogicalInstrumentsOption: Int = scanner.nextInt()
                val analogicalInstruments = analogicalInstrumentsOption == 1

                dataFile = Band(bandName, creationYear, genre, typeOfBand, analogicalInstruments)
                val bandFile = File("DataFiles\\${genre.genreName}", "$bandName.bin")
                print(bandFile)
                saveData(dataFile, bandFile)
                break
            }
            else -> println("You had select an invalid option")
        }
    }


}


//Read
fun readFile(dataFileName: String): DataFile? {
    var dataFile: DataFile?
    while (true) {

        var file = loadData("DataFiles\\$dataFileName\\$dataFileName.bin")

        if (!file.isFile) {
            File("DataFiles").listFiles { _, name -> !name.contains(".") }
                ?.forEach {
                    file = loadData("DataFiles\\${it.name}\\$dataFileName.bin")
                    if (file.isFile) {
                        dataFile = fileToDataFile(file)
                        return dataFile
                    }
                }
            continue
        }

        dataFile = fileToDataFile(file)
        return dataFile

    }

}

//Update
fun updateFile() {
    print("Insert the name of the Data File: ")
    input = readInput()
    val dataFile = readFile(input)
    println("DataFile(firstParameter, secondParameter, thirdParameter, fourthParameter, fifthParameter)")

    println("\nfirstParameter: $printGreen${dataFile?.firstParameter}$printReset")

    print("secondParameter: ")
    dataFile?.secondParameter = scanner.next()

    print("thirdParameter: ")
    dataFile?.thirdParameter = scanner.next()

    print("fourthParameter: ")
    dataFile?.fourthParameter = scanner.next()

    print("fifthParameter: ")
    dataFile?.fifthParameter = scanner.next()

    if (dataFile != null) {
        if (dataFile is Genre) {
            dataFile.setParameters()
            saveData(dataFile,File("DataFiles\\${dataFile.firstParameter}\\${dataFile.firstParameter}.bin"))
        }
        if (dataFile is Band) {
            dataFile.setParameters()
            saveData(dataFile,File("DataFiles\\${dataFile.genre.genreName}\\${dataFile.firstParameter}.bin"))
        }
    }
    println(dataFile)
}

//Delete
fun deleteFile(file: File):Boolean {
    return file.deleteRecursively()
}

fun showMenu() {
    println("\n\t\tGENRE - BAND")
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
        input = readInput()
        when (input) {
            "1" -> createFile()
            "2" -> {
                print("Insert the name of the Data File: ")
                input = readInput()
                println(readFile(input))
            }
            "3" -> updateFile()
            "4" -> {
                print("Insert the name of the Data File: ")
                input = readInput()
                var file = loadData("DataFiles\\$input\\$input.bin")
                if (file.exists()){
                    if (!file.isFile) {
                        File("DataFiles").listFiles { _, name -> !name.contains(".") }
                            ?.forEach {
                                file = loadData("DataFiles\\${it.name}\\$input.bin")
                            }
                        deleteFile(file)
                        continue
                    }

                    println("${printRed}Are you sure deleting ${file.name}? (y/n)" +
                            "\nThis will delete all files that have ${file.name} as their genre!" +
                            printReset)

                    if (readInput()=="y"){
                        deleteFile(file.parentFile)
                    }else{
                        println("No file was deleted")
                    }

                }else {
                    println("There's no file with the name $input")
                }
            }
            "5" -> break
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