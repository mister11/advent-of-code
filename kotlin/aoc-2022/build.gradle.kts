import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.20"
}

group = "hr.mister11"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

val codeTemplate = """
    package hr.mister11.aoc2022
    
    fun main() {
        val input = readInput("<DAY>")
        
        println("Part 1: ${"$"}{Solution<DAY>.part1(input)}")
        println("Part 2: ${"$"}{Solution<DAY>.part2(input)}")
    }
   
    
    object Solution<DAY> {
        fun part1(input: String): String {
            TODO()
        }

        fun part2(input: String): String {
            TODO()
        }
    }
""".trimIndent()

val testTemplate = """
    package hr.mister11.aoc2022

    import kotlin.test.Test
    import kotlin.test.assertEquals

    class Solution<DAY>Test {

        @Test
        fun testPart1() {
            assertEquals("<PART1_RESULT>", Solution<DAY>.part1(readExample("<DAY>")))
        }

        @Test
        fun testPart2() {
            assertEquals("<PART2_RESULT>", Solution<DAY>.part2(readExample("<DAY>")))
        }
    }
""".trimIndent()

tasks.create("generate-day") {
    doLast {
        val day = properties["day"]
        val dayFormatted = day.toString().padStart(2, '0')

        val srcDir = "${project.projectDir}/src/main/kotlin"
        val testDir = "${project.projectDir}/src/test/kotlin"
        val resourcesDir = "${project.projectDir}/src/main/resources"

        val codeFile = "$srcDir/hr/mister11/aoc2022/$dayFormatted.kt"
        val testFile = "$testDir/hr/mister11/aoc2022/$dayFormatted.kt"
        val resourceFiles = listOf(
            "$resourcesDir/input/$dayFormatted.txt",
            "$resourcesDir/example/$dayFormatted.txt",
        )

        val code = codeTemplate.replace("<DAY>", dayFormatted)
        File(codeFile).writeText(code)


        val part1Result = properties["part1"].toString()
        val part2Result = properties["part2"].toString()
        val test = testTemplate
            .replace("<DAY>", dayFormatted)
            .replace("<PART1_RESULT>", part1Result)
            .replace("<PART2_RESULT>", part2Result)
        File(testFile).writeText(test)

        resourceFiles.forEach { File(it).createNewFile() }
    }
}
