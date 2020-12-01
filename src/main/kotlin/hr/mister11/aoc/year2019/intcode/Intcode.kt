package hr.mister11.aoc.year2019.intcode

class Intcode(
    private val intcodeValues: MutableList<Int>
) {

    fun execute() {
        var index = 0
        while (true) {
            val opCode = OpCode.fromValue(intcodeValues[index])
            val command = getCommand(opCode, index)
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

    private fun getCommand(opCode: OpCode, index: Int): Command {
        return when (opCode) {
            OpCode.ADD -> AddCommand(
                arg1Index = intcodeValues[index + 1],
                arg2Index = intcodeValues[index + 2],
                resultIndex = intcodeValues[index + 3]
            )
            OpCode.MULTIPLY -> MultiplyCommand(
                arg1Index = intcodeValues[index + 1],
                arg2Index = intcodeValues[index + 2],
                resultIndex = intcodeValues[index + 3]
            )
            OpCode.HALT -> HaltCommand()
        }
    }

}
