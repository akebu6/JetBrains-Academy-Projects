fun main() {
    var cells = listOf("___", "___", "___")
    var xWins = false
    var oWins = false
    var empty = 9
    var letter = 'X'

    do {
        printGame(cells)
        cells = getCell(cells.toMutableList(), letter)
        if (letter == 'X') {
            xWins = checkWin(cells, 'X')
            letter = 'O'
        } else {
            oWins = checkWin(cells, 'O')
            letter = 'X'
        }
        empty--
    } while (!xWins && !oWins && empty != 0)

    printGame(cells)

    when {
        xWins -> println("X wins")
        oWins -> println("O wins")
        else -> println("Draw")
    }
}

fun getCell(cells: MutableList<String>, char: Char): List<String> {
    var numbers: List<String>
    val range = 1..3

    while (true) {
        numbers = getString("Enter the coordinates: ").split(" ")
        if (numbers.size != 2 || !isNumber(numbers[0]) || !isNumber(numbers[1]))
            println("You should enter numbers!")
        else if (!range.contains(numbers[0].toInt()) || !range.contains(numbers[1].toInt()))
            println("Coordinates should be from 1 to 3!")
        else if (cells[numbers[0].toInt() - 1][numbers[1].toInt() - 1] != '_')
            println("This cell is occupied! Choose another one!")
        else break
    }

    val i1 = numbers[0].toInt() - 1
    val i2 = numbers[1].toInt() - 1

    cells[i1] = (if (i2 > 0) cells[i1].substring(0, i2) else "") + char +
            if (i2 < cells[i1].lastIndex) cells[i1].substring(i2 + 1) else ""

    return cells
}

fun printGame(cells: List<String>) {
    val dashes = "-".repeat(9)

    println(dashes)
    cells.forEach { line -> print("| "); line.chunked(1).forEach { print("$it ") }; println("|") }
    println(dashes)
}

fun checkWin(cells: List<String>, check: Char): Boolean {
    cells.forEach { line -> if (line.all { it == check }) return true }
    for (i in cells.indices) if (cells[0][i] == check && cells[1][i] == check && cells[2][i] == check) return true
    if (cells[0][0] == check && cells[1][1] == check && cells[2][2] == check) return true
    return cells[0][2] == check && cells[1][1] == check && cells[2][0] == check
}

fun getString(text: String): String {
    print(text)
    return readLine()!!
}

fun isNumber(number: String) = number.toIntOrNull() != null
