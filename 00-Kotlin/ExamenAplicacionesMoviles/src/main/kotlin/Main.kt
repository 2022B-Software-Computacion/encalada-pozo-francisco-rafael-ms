import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*

//Global variables
val scanner = Scanner(System.`in`) // Create a scanner to read input from the user
var input = "0" // Initialize an input variable

// Constants for text formatting
const val printGreen = "\u001B[3m\u001B[32m"
const val printBlue = "\u001b[34m"
const val printRed = "\u001b[31m"
const val printReset = "\u001B[0m"

// The readInput function prompts the user for input and returns the input as a string
fun readInput(): String {
    print("${printGreen}admin>${printReset} ")
    return scanner.next()
}

// The saveData function saves the user's data to a specified file
fun saveData(userData: DataFile, file: File) {
    file.parentFile.mkdir() // Create the parent directory if it doesn't exist
    if (!file.exists()) file.createNewFile() // Create the file if it doesn't exist
    ObjectOutputStream(file.outputStream()).use {
        it.writeObject(userData) // Write the user's data to the file
    }
}

// The loadData function loads a file from a specified directory
fun loadData(directory: String): File {
    return File(directory)
}

// The fileToDataFile function converts a file to a DataFile object
fun fileToDataFile(file: File?): DataFile? {
    if (file != null) {
        ObjectInputStream(file.inputStream()).use {
            return it.readObject() as? DataFile // Read the data from the file and return it as a DataFile object
        }
    }
    return null
}

//CRUD OPERATIONS
// The createFile function allows the user to create a new data file
fun createFile() {
    println("What do you want to create?")
    val dataFile: DataFile

    while (true) {
        showFileOptions()
        input = readInput()
        when (input) {
            "1" -> { // Code for creating a new genre file
                print("\tEnter the Genre's name: ")
                val genreName: String = scanner.next()

                print("\tEnter the Genre's appearance period: ")
                val startPeriod: String = scanner.next()

                print("\tEnter the Gender's appearance country: ")
                val startCountry: String = scanner.next()

                print("\tEnter the Gender's signature: ")
                val signature: String = scanner.next()

                print("\tEnter the Gender's BPM (Bits Per Minute): ")
                val bmpAverage: Double
                try {
                    bmpAverage = scanner.nextDouble()
                } catch (_: Exception) {
                    println("${printRed}Insert an Double value${printReset}")
                    scanner.next()
                    break
                }


                dataFile = Genre(genreName, startPeriod, startCountry, signature, bmpAverage)
                val genreFile = File("DataFiles\\$genreName", "$genreName.bin")
                println(dataFile)
                saveData(dataFile, genreFile)
                break
            }
            "2" -> { // Create a new Genre object
                print("\tEnter the Band's name: ")
                val bandName: String = scanner.next()

                print("\tEnter the Band's creation year: ")
                val creationYear: Int
                try {
                    creationYear = scanner.nextInt()
                } catch (_: Exception) {
                    println("${printRed}Insert an Int value${printReset}")
                    scanner.next()
                    break
                }


                println("\tBand's gender: ")
                File("DataFiles").listFiles { _, name -> !name.contains(".") }
                    ?.forEach {
                        println("\t\t- ${it.name}")
                    }
                print("\tEnter the gender: ")
                val genrePath: String = scanner.next()
                val file: File = loadData("DataFiles\\$genrePath\\$genrePath.bin")
                if (!file.isFile) {
                    println("${printRed}Insert a valid Genre${printReset}")
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
                when (scanner.next()) {
                    "1" -> typeOfBand = 'i'
                    "2" -> typeOfBand = 'v'
                    "3" -> typeOfBand = 'o'
                    "i" -> typeOfBand = 'i'
                    "v" -> typeOfBand = 'v'
                    "o" -> typeOfBand = 'o'
                    else -> println("\tWas selected \"with vocals\" by defect")
                }


                print(
                    "\tThe band is analogical or digital?" +
                            "\n\t\t1. Analogical" +
                            "\n\t\t2. Digital" +
                            "\n\tEnter an option: "
                )
                val analogicalInstrumentsOption: String = scanner.next()
                val analogicalInstruments = analogicalInstrumentsOption == "1"

                dataFile = Band(bandName, creationYear, genre, typeOfBand, analogicalInstruments)
                val bandFile = File("DataFiles\\${genre.genreName}", "$bandName.bin")
                println(dataFile)
                saveData(dataFile, bandFile)
                break
            }
            "3" -> break
            else -> {
                println("You had select an invalid option")
            }
        }
    }


}


//Read
fun readFile(dataFileName: String): File? {
    var file = loadData("DataFiles\\$dataFileName\\$dataFileName.bin")
    if (!file.isFile) {
        File("DataFiles").listFiles { _, name -> !name.contains(".") }
            ?.forEach {
                file = loadData("DataFiles\\${it.name}\\$dataFileName.bin")
                if (file.exists()) {
                    return file
                }
            }

    }
    return if (file.exists()) {
        file
    } else {
        null
    }
}

//Update
fun updateFile() {
    println("Insert the name of the Data File: ")
    input = readInput()
    val file: File? = readFile(input)
    val dataFile = fileToDataFile(file)
    println(dataFile)
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
            saveData(dataFile, File("DataFiles\\${dataFile.firstParameter}\\${dataFile.firstParameter}.bin"))
        }
        if (dataFile is Band) {
            deleteFile(file)
            dataFile.setParameters()
            saveData(dataFile, File("DataFiles\\${dataFile.genre.genreName}\\${dataFile.firstParameter}.bin"))
        }
    }
    println(dataFile)
}

//Delete
fun deleteFile(file: File?): Boolean {
    return file?.deleteRecursively() ?: false
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
    println("3. Cancel")
}


fun iterateMainMenu() {
    while (true) {
        showMenu()
        input = readInput()
        when (input) {
            //Create
            "1" -> createFile()

            //Read
            "2" -> {
                println("Insert the name of the Data File: ")
                input = readInput()
                val file = readFile(input)
                val dataFile = fileToDataFile(file)
                println(dataFile)
                if (file != null && file.nameWithoutExtension == file.parentFile.nameWithoutExtension) {
                    if (file.parentFile.isDirectory) {
                        println("\n${printBlue}Bands:")
                        for (name in file.parentFile.listFiles()!!) {
                            println("\t- " + name.nameWithoutExtension)
                        }
                        print(printReset)
                    }
                }
            }

            //Update
            "3" -> updateFile()

            //Delete
            "4" -> {
                println("Insert the name of the Data File: ")
                input = readInput()
                var file = loadData("DataFiles\\$input\\$input.bin")

                if (file.exists()) { //Delete only if the file exists
                    if (!file.isFile) { //Delete a band
                        File("DataFiles").listFiles { _, name -> !name.contains(".") }
                            ?.forEach {
                                file = loadData("DataFiles\\${it.name}\\$input.bin")
                            }
                        deleteFile(file)
                        continue
                    }

                    //Delete a genre
                    println(
                        "${printRed}Are you sure deleting ${file.nameWithoutExtension}? (y/n)" +
                                "\nThis will delete all files that have ${file.nameWithoutExtension} as their genre!" +
                                printReset
                    )

                    if (readInput() == "y") {
                        deleteFile(file.parentFile)
                    } else {
                        println("No file was deleted")
                    }

                } else {
                    println("There's no file with the name $input")
                }
            }

            //Exit
            "5" -> break

            //Default
            else -> println("You had select an invalid option")
        }
    }
}


fun main() {

    scanner.useDelimiter("\n")
    print("Execute filling code: ")
    if (scanner.next() == "y") {
        val rock = Genre("Rock", "1950", "EEUU & UK", "4/4", 90.0)
        val metal = Genre("Metal", "1980", "EEUU, Europe & Japan", "4/4 & subdivisions", 120.0)
        val blues = Genre("Blues", "1885", "Europe", "4/4, 6/8 & subdivisions", 85.0)
        val jazz = Genre("Jazz", "1920", "Europe & Japan", "4/4, 6/8 & subdivisions", 100.0)
        val classical = Genre("Classical", "1100", "Europe", "4/4, 3/4 normally", 76.5)
        saveData(rock, File("DataFiles\\Rock", "Rock.bin"))
        saveData(metal, File("DataFiles\\Metal", "Metal.bin"))
        saveData(blues, File("DataFiles\\Blues", "Blues.bin"))
        saveData(jazz, File("DataFiles\\Jazz", "Jazz.bin"))
        saveData(classical, File("DataFiles\\Classical", "Classical.bin"))
        saveData(Band("Nirvana", 1987, rock, 'v', true), File("DataFiles\\Rock", "Nirvana.bin"))
        saveData(Band("Rammstein", 1994, metal, 'v', false), File("DataFiles\\Metal", "Rammstein.bin"))
        saveData(Band("B.B. King", 1946, blues, 'v', true), File("DataFiles\\Blues", "B.B. King.bin"))
        saveData(Band("The Great Guitars", 1944, jazz, 'i', true), File("DataFiles\\Jazz", "The Great Guitars.bin"))
        saveData(
            Band("Benedict Saint Domingo Chorus", 2012, classical, 'o', true),
            File("DataFiles\\Classical", "Benedict Saint Domingo Chorus.bin")
        )

    }

    iterateMainMenu()

}