import kotlin.math.exp

private class Day4Record(record: String) {
    var birthYear: Int? = null
    var issueYear: Int? = null
    var expirationYear: Int? = null
    var height: String? = null
    var hairColor: String? = null
    var eyeColor: String? = null
    var passportID: String? = null
    var countryID: String? = null

    init {
        val listOfAtoms = record.split(' ')

        for(atom in listOfAtoms.filter { atom -> atom.isNotEmpty()}) {
            val (fieldName, value) = atom.split(':')

            if (fieldName == "byr") {
                this.birthYear = value.toInt()
            }

            if (fieldName == "iyr") {
                this.issueYear = value.toInt()
            }

            if (fieldName == "eyr") {
                this.expirationYear = value.toInt()
            }

            if (fieldName == "hgt") {
                this.height = value
            }

            if (fieldName == "hcl") {
                this.hairColor = value
            }

            if (fieldName == "ecl") {
                this.eyeColor = value
            }

            if (fieldName == "pid") {
                this.passportID = value
            }

            if (fieldName == "cid") {
                this.countryID = value
            }
        }
    }

    fun hasRequiredFields(): Boolean {
        return !listOf(
            this.passportID,
            this.eyeColor,
            this.hairColor,
            this.expirationYear,
            this.issueYear,
            this.birthYear,
            this.height,
        ).any { field -> field == null }
    }

    fun hasRequiredFieldsPartTwo(): Boolean {
        // This incredibly unfun challenge revolves around stringent validation for the above fields. 🙄

        // We can use !! here because the above field does the null checks.

        // Birth Year must be at least 1920 and at most 2002
        if (birthYear.toString().count() != 4 || birthYear!! !in 1920..2002) {
            return false;
        }

        // Issue year must be at least 2010 and at most 2020
        if (issueYear.toString().count() != 4 || issueYear!! !in 2010..2020) {
            return false;
        }

        // Expiration year must be at least 2020 and at most 2030
        if (expirationYear.toString().count() != 4 || expirationYear!! !in 2020..2030) {
            return false;
        }

        // Height can be inches or centimeters; must be between 150cm, 193cm or 59in, 76in
        if (!height!!.contains("in") && !height!!.contains("cm")) {
            return false;
        }
        if (height!!.contains("in") && height!!.replace("in", "").toInt() !in 59..76) {
            return false;
        }
        if (height!!.contains("cm") && height!!.replace("cm", "").toInt() !in 150..193) {
            return false;
        }

        // Hair color must start with #, and have 6 hex characters after it.
        if (hairColor!![0] != '#') {
            return false;
        }
        hairColor = hairColor!!.substring(1, hairColor!!.length)

        for(char in this.hairColor!!) {
            if (char !in '0'..'9' && char !in 'a'..'f') {
                return false
            }
        }

        // Eye color has to be one of a few constants
        if (!listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth").any { color -> color == eyeColor }) {
            return false;
        }

        // Passport has to be 9 digits
        if (passportID!!.count() != 9 || passportID!!.any { item -> item !in '0'..'9'}) {
            return false;
        }

        // Everything else passed; we're good.
        return true;
    }
}

class Day4Database(linesOfFile: List<String>) {
    private val records: List<Day4Record>;

    init {
        // The individual records can be split onto multiple lines, so we group them up into "sentences"
        var recordBuffer = ""
        val records: MutableList<Day4Record> = mutableListOf()

        for(line in linesOfFile) {
            if (line == "" && recordBuffer != "") {
                // We are at the end of the record, so we need to flush the record buffer.
                records.add(Day4Record(recordBuffer.replace("  ", " ")))
                recordBuffer = ""
                continue
            }
            recordBuffer += "$line ";
        }

        records.add(Day4Record(recordBuffer))

        this.records = records;
    }

    fun validate(): Int {
        var goodRecords = 0;

        for (record in this.records) {
            if (record.hasRequiredFields()) {
                goodRecords++;
            }
        }

        return goodRecords;
    }

    fun validatePartTwo(): Int {
        var goodRecords = 0;

        for (record in this.records) {
            if (record.hasRequiredFields() && record.hasRequiredFieldsPartTwo()) {
                goodRecords++;
            }
        }

        return goodRecords;
    }
}
