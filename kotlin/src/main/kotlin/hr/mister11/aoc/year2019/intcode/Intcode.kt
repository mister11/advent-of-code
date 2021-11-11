package hr.mister11.aoc.year2019.intcode

class Intcode(
    val intcodeValues: MutableList<Int>
) {

    private val inputs = mutableListOf<Int>()
    private val outputs = mutableListOf<Int>()
    var index = 0

    fun execute() {

        while (true) {
            val command = getCommand(index)
            command.execute(this)
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

    fun addInput(input: Int) = inputs.add(input)

    fun addOutput(output: Int) = outputs.add(output)

    fun getLatestOutput() = outputs.last()

    private fun getCommand(index: Int): Command {
        val (opCode, rawParamModes) = getOpCodeAndRawParamModes(intcodeValues[index].toString())
        val rawParamModeIndex = rawParamModes.length - 1
        return when (opCode) {
            OpCode.ADD -> AddCommand(
                argument1 = Argument(
                    value = intcodeValues[index + 1],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex) { '0' }.toString().toInt())
                ),
                argument2 = Argument(
                    value = intcodeValues[index + 2],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex - 1) { '0' }.toString().toInt())

                ),
                resultArgument = Argument(
                    value = index + 3,
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex - 2) { '0' }.toString().toInt()),
                )
            )
            OpCode.MULTIPLY -> MultiplyCommand(
                argument1 = Argument(
                    value = intcodeValues[index + 1],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex) { '0' }.toString().toInt())
                ),
                argument2 = Argument(
                    value = intcodeValues[index + 2],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex - 1) { '0' }.toString().toInt())

                ),
                resultArgument = Argument(
                    value = index + 3,
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex - 2) { '0' }.toString().toInt()),
                )
            )
            OpCode.INPUT -> InputCommand(
                value = inputs.removeFirst(),
                resultArgument = Argument(
                    value = index + 1,
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex) { '0' }.toString().toInt())
                )
            )
            OpCode.OUTPUT -> OutputCommand(
                resultArgument = Argument(
                    value = index + 1,
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex) { '0' }.toString().toInt())
                )
            )
            OpCode.JUMP_IF_TRUE -> JumpIfTrueCommand(
                argument1 = Argument(
                    value = intcodeValues[index + 1],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex) { '0' }.toString().toInt())
                ),
                argument2 = Argument(
                    value = intcodeValues[index + 2],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex - 1) { '0' }.toString().toInt())

                )
            )
            OpCode.JUMP_IF_FALSE -> JumpIfFalseCommand(
                argument1 = Argument(
                    value = intcodeValues[index + 1],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex) { '0' }.toString().toInt())
                ),
                argument2 = Argument(
                    value = intcodeValues[index + 2],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex - 1) { '0' }.toString().toInt())

                )
            )
            OpCode.LESS_THAN -> LessThanCommand(
                argument1 = Argument(
                    value = intcodeValues[index + 1],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex) { '0' }.toString().toInt())
                ),
                argument2 = Argument(
                    value = intcodeValues[index + 2],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex - 1) { '0' }.toString().toInt())

                ),
                resultArgument = Argument(
                    value = index + 3,
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex - 2) { '0' }.toString().toInt()),
                )
            )
            OpCode.EQUALS -> EqualsCommand(
                argument1 = Argument(
                    value = intcodeValues[index + 1],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex) { '0' }.toString().toInt())
                ),
                argument2 = Argument(
                    value = intcodeValues[index + 2],
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex - 1) { '0' }.toString().toInt())

                ),
                resultArgument = Argument(
                    value = index + 3,
                    parameterMode = ParameterMode.fromValue(rawParamModes.getOrElse(rawParamModeIndex - 2) { '0' }.toString().toInt()),
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
