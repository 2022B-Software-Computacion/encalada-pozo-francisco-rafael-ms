data class Band(
    var bandName: String,
    var creationYear: Int,
    var genre: Genre,
    var typeOfBand: Char,
    var analogicalInstruments: Boolean,
)
    : DataFile(bandName, genre, creationYear, typeOfBand, analogicalInstruments)
{
    override fun toString(): String {
        return "${printBlue}Band Name: $firstParameter\n" +
                "Creation year: $creationYear\n" +
                "Genre: ${genre.genreName}\n" +
                "Type of band: $fourthParameter\n" +
                "Analogical Instruments: $fifthParameter" +
                printReset
    }

    fun setParameters(){
        bandName = firstParameter as String
        creationYear = (secondParameter as String).toInt()
        genre = fileToDataFile(readFile(thirdParameter as String)) as Genre
        typeOfBand = (fourthParameter as String)[0]
        analogicalInstruments = (fifthParameter as String).toBoolean()
    }

}