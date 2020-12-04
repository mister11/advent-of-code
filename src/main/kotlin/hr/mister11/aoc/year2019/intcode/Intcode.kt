package hr.mister11.aoc.year2019.intcode

class Intcode(
    private val intcodeValues: MutableList<Int>
) {

    fun execute() {
        var index = 0
        while (true) {
            val command = getCommand(index)
            command.execute(intcodeValues)
            index += command.numOfArguments() + 1
        }
    }

    fun restore1202ProgramAlarm() {
        setInputs(12, 2)
    }

    fun setInputs(a: Int, b: Int) {
        intcodeValues[1] = a
        intcodeValues[2] = b
    }

    fun getValueAt(position: Int) = intcodeValues[position]

    private fun getCommand(index: Int): Command {
        val (opCode, rawParamModes) = getOpCodeAndRawParamModes(intcodeValues[index].toString())
        return when (opCode) {
            OpCode.ADD -> AddCommand(
                argument1 = Argument(
                    value = intcodeValues[index + 1],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(2) { '0' }.toString().toInt())
                ),
                argument2 = Argument(
                    value = intcodeValues[index + 2],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(1) { '0' }.toString().toInt())

                ),
                resultArgument = Argument(
                    value = intcodeValues[index + 3],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(0) { '0' }.toString().toInt()),
                )
            )
            OpCode.MULTIPLY -> MultiplyCommand(
                argument1 = Argument(
                    value = intcodeValues[index + 1],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(2) { '0' }.toString().toInt())
                ),
                argument2 = Argument(
                    value = intcodeValues[index + 2],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(1) { '0' }.toString().toInt())

                ),
                resultArgument = Argument(
                    value = intcodeValues[index + 3],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(0) { '0' }.toString().toInt()),
                )
            )
            OpCode.HALT -> HaltCommand()
        }
    }

    private fun getOpCodeAndRawParamModes(opCodeRaw: String): Pair<OpCode, String> {
        val opCodeRawLength = opCodeRaw.length
        return if (opCodeRawLength == 1 || opCodeRawLength == 2) {
            Pair(OpCode.fromValue(opCodeRaw.toInt()), "")
        } else {
            Pair(
                OpCode.fromValue(opCodeRaw.substring(opCodeRawLength - 2, opCodeRawLength).toInt()),
                opCodeRaw.substring(0, opCodeRawLength - 2)
            )
        }
    }

}