data class Genre(
    var genreName: String,
    var startPeriod: String,
    var startCountry: String,
    var signature: String,
    var bpmAverage: Double,
)
    : DataFile(genreName, startPeriod, startCountry, signature, bpmAverage)
{
    override fun toString(): String {
        return "${printBlue}Genre Name: $firstParameter\n" +
                "Start period: $secondParameter\n" +
                "Start country: $thirdParameter\n" +
                "Signature: $fourthParameter\n" +
                "BMP average: $fifthParameter" +
                printReset
    }

    fun setParameters(){
        genreName = firstParameter as String
        startPeriod = secondParameter as String
        startCountry = thirdParameter as String
        signature = fourthParameter as String
        bpmAverage = (fifthParameter as String).toDouble()
    }
}