class Day2PasswordDeclaration(lineOfText: String) {
    // The format of one of these Password rules is '18-19 q: hprbdznbqlfnwzwpqckb'
    // The strategy is to remove all whitespace, and split it on ':'
    // This will yield two parts:
    //     [18-19q,hprbdznbqlfnwzwpqckb]
    // Then, the first half can be split on - and the last character of the second sub-split will become the letter.

    val lowerBounds: Int;
    val upperBounds: Int;
    val character: Char;
    val password: String;

    init {
        val noWhitespaceLine = lineOfText.replace(" ", "")
        val (rule, password) = noWhitespaceLine.split(':')
        val (lowerBound, upperBoundAndCharacter) = rule.split('-')
        val char = upperBoundAndCharacter.takeLast(1)[0]
        val restOfUpperBound = upperBoundAndCharacter.substring(0, upperBoundAndCharacter.length - 1)

        this.lowerBounds = lowerBound.toInt()
        this.upperBounds = restOfUpperBound.toInt()
        this.character = char
        this.password = password;
    }

    fun matchesPartOne(): Boolean {
        val characterCount = this.password.count { char -> char == this.character };
        return characterCount in lowerBounds..upperBounds;
    }

    fun matchesPartTwo(): Boolean {
        val characterAtFirstLocation = this.password[this.lowerBounds - 1];
        val characterAtLastLocation = this.password[this.upperBounds - 1];

        return (characterAtFirstLocation == this.character) xor (characterAtLastLocation == this.character);
    }
}
