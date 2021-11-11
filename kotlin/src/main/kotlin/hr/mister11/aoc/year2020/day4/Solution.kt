package hr.mister11.aoc.year2020.day4

import hr.mister11.aoc.Resources

private val HAIR_COLOR_REGEX = "#[0-9a-f]{6}".toRegex()
private val VALID_EYE_COLORS = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

fun main() {
    val solution = Solution(Resources.readFileRaw(2020, 4))
    println("Part 1: ${solution.part1()}")
    println("Part 2: ${solution.part2()}")
}

class Solution(
    credsRaw: String
) {

    private val credsList = parseCredsRaw(credsRaw)

    fun part1() = credsList.count { it.isValid() }
    fun part2() = credsList.count { it.isReallyValid() }

    private fun parseCredsRaw(credentialsRaw: String): List<NorthPoleCreds> {
        return credentialsRaw.split("\\n\\n".toRegex())
            .map { credentialsItemRaw ->
                credentialsItemRaw.split("\\s+".toRegex())
                    .map { credentialPairTokens ->
                        val credentialsKeyValue = credentialPairTokens.split(":")
                        Pair(credentialsKeyValue[0], credentialsKeyValue[1])
                    }
                    .toMap()
                    .toNorthPoleCreds()
            }
    }
}

private fun Map<String, String>.toNorthPoleCreds(): NorthPoleCreds {
    return NorthPoleCreds(
        birthYear = this["byr"],
        issueYear = this["iyr"],
        expirationYear = this["eyr"],
        height = this["hgt"],
        hairColor = this["hcl"],
        eyeColor = this["ecl"],
        passportId = this["pid"],
        countryId = this["cid"]
    )
}

data class NorthPoleCreds(
    val birthYear: String?,
    val issueYear: String?,
    val expirationYear: String?,
    val height: String?,
    val hairColor: String?,
    val eyeColor: String?,
    val passportId: String?,
    val countryId: String?
) {

    fun isValid(): Boolean {
        return birthYear != null && issueYear != null && expirationYear != null && height != null &&
            hairColor != null && eyeColor != null && passportId != null
    }

    fun isReallyValid(): Boolean {
        return isValid() && isBirthYearValid() && isIssueYearValid() && isExpirationYearValid() && isHeightValid() &&
            isHairColorValid() && isEyeColorValid() && isPassportIdValid()
    }

    private fun isBirthYearValid(): Boolean {
        if (birthYear == null || birthYear.length != 4) {
            return false
        }
        return birthYear.toInt() in 1920..2002
    }

    private fun isIssueYearValid(): Boolean {
        if (issueYear == null || issueYear.length != 4) {
            return false
        }
        return issueYear.toInt() in 2010..2020
    }

    private fun isExpirationYearValid(): Boolean {
        if (expirationYear == null || expirationYear.length != 4) {
            return false
        }
        return expirationYear.toInt() in 2020..2030
    }

    private fun isHeightValid(): Boolean {
        if (height == null) {
            return false
        }
        val length = height.length
        val lastTwo = height.substring(length - 2, length)
        if (lastTwo != "cm" && lastTwo != "in") {
            return false
        }
        return if (lastTwo == "cm") {
            height.substring(0, length - 2).toInt() in 150..193
        } else {
            height.substring(0, length - 2).toInt() in 59..76
        }
    }

    private fun isHairColorValid(): Boolean {
        if (hairColor == null) {
            return false
        }
        return hairColor.matches(HAIR_COLOR_REGEX)
    }

    private fun isEyeColorValid(): Boolean {
        return VALID_EYE_COLORS.contains(eyeColor)
    }

    private fun isPassportIdValid(): Boolean {
        return passportId.orEmpty().length == 9
    }
}