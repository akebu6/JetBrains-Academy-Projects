class GameBoard {
    private val startPositionWhite = 2
    private val startPositionBlack = 7
    private var firstPlayersName: Player? = null
    private var secondPlayerName: Player? = null
    private var actualTurnsPlayer: Player? = null
    private val chessBoard =
        MutableList(8) { MutableList(8) { Elements.EMPTY.view } }
    private val saveTurns = mutableListOf<String>()
    private var count = 0
    private lateinit var inputCoordinates: Coordinate

    fun start() {
        startPositionChessBoard()
        namesOfPlayers()
        printChessboard()
        inputUsersText()
    }

    private fun namesOfPlayers() {
        println("First Player's name:")
        firstPlayersName = Player(readLine()!!)
        println("Second Player's name:")
        secondPlayerName = Player(readLine()!!)
        actualTurnsPlayer = firstPlayersName
    }

    private fun switchPlayer() {
        actualTurnsPlayer = if (actualTurnsPlayer == firstPlayersName) secondPlayerName else firstPlayersName
    }

    private fun inputUsersText() {
        val regex =
            Regex(Elements.REGEX_SYMBOLS.view)
        repeat@ while (true) {
            println("${actualTurnsPlayer!!.name}'s turn:")
            val inputText = readLine()!!
            when {
                inputText == "exit" -> {
                    println("Bye!")
                    return
                }
                !inputText.matches(regex) -> {
                    println("Invalid input")
                    continue@repeat
                }

            }
            inputCoordinates = Coordinate.build(inputText)

            when {
                convertingCharsCoordinateToInt(inputCoordinates.firstLine) - convertingCharsCoordinateToInt(
                    inputCoordinates.secondLine) > 1 ||
                        convertingCharsCoordinateToInt(inputCoordinates.firstLine) - convertingCharsCoordinateToInt(
                    inputCoordinates.secondLine) < -1 -> {
                    println("Invalid input")
                    continue@repeat
                }
                (inputCoordinates.firstLine != inputCoordinates.secondLine) -> {
                    when {
                        count == 0 -> {
                            println("Invalid input")
                            continue@repeat
                        }
                        !takePawnOnPassant() && !checkedPawnsAttack() -> {
                            println("Invalid input")
                            continue@repeat
                        }
                    }
                }
                stalemate() -> {
                    return
                }
                winConditionToTakingAllPawns() -> {
                    return
                }
                winConditionLastLine() -> {
                    return
                }
                else -> changePawnsPosition()

            }
        }
    }

    // метод отвечающий за движение пешок согласно правилам игры (+2 или 1 вперед со старта, +1 дальше)
    private fun changePawnsPosition(): Boolean {

        when (actualTurnsPlayer) {
            firstPlayersName -> {
                if (chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] !=
                    Elements.WHITE.view
                ) {
                    println("No white pawn at ${inputCoordinates.firstLine}${inputCoordinates.firstPosition}")
                    return false
                } else if (
                    (inputCoordinates.firstPosition == startPositionWhite) && // проверяем, не находится ли чья-то пешка на пути между стартовым ходом
                    ((inputCoordinates.secondPosition - inputCoordinates.firstPosition) == 2)
                    && (chessBoard[inputCoordinates.firstPosition][convertingCharsCoordinateToInt(
                        inputCoordinates.firstLine)] != Elements.EMPTY.view)
                ) {
                    println("Invalid Input")
                    return false
                } else if (inputCoordinates.firstPosition == startPositionWhite &&
                    ((inputCoordinates.secondPosition - inputCoordinates.firstPosition) == 2 ||
                            (inputCoordinates.secondPosition - inputCoordinates.firstPosition) == 1)
                ) {
                    if (chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] ==
                        Elements.EMPTY.view
                    ) {
                        chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                            Elements.EMPTY.view
                        chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                            Elements.WHITE.view

                        turnsHistory()
                        printChessboard()
                        stalemate()
                        switchPlayer()

                        return true
                    } else
                        println("Invalid input")
                    return false

                } else if (actualTurnsPlayer == firstPlayersName && inputCoordinates.secondPosition - inputCoordinates.firstPosition != 1) {
                    println("Invalid input")
                    return false
                } else if (actualTurnsPlayer == firstPlayersName && inputCoordinates.firstPosition >= inputCoordinates.secondPosition) {
                    println("Invalid input")
                    return false
                } else {
                    if (chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] ==
                        Elements.EMPTY.view
                    ) {
                        chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                            Elements.EMPTY.view
                        chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                            Elements.WHITE.view
                        turnsHistory()
                        switchPlayer()
                        printChessboard()
                        stalemate()


                        return true
                    } else
                        println("Invalid input")
                    return false
                }
            }
            secondPlayerName -> {
                if (chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] != Elements.BLACK.view) {
                    println("No black pawn at ${inputCoordinates.firstLine}${inputCoordinates.firstPosition}")
                    return false
                } else if (
                    (inputCoordinates.firstPosition == startPositionBlack) && // проверяем, не находится ли чья-то пешка на пути между стартовым ходом
                    ((inputCoordinates.firstPosition - inputCoordinates.secondPosition) == 2)
                    && (chessBoard[inputCoordinates.secondPosition][convertingCharsCoordinateToInt(
                        inputCoordinates.firstLine)] != Elements.EMPTY.view)
                ) {
                    println("Invalid Input")
                    return false
                } else if (inputCoordinates.firstPosition == startPositionBlack &&
                    ((inputCoordinates.firstPosition - inputCoordinates.secondPosition) == 2 ||
                            (inputCoordinates.firstPosition - inputCoordinates.secondPosition) == 1)
                ) {
                    if (chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] ==
                        Elements.EMPTY.view
                    ) {
                        chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                            Elements.EMPTY.view
                        chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                            Elements.BLACK.view
                        turnsHistory()
                        switchPlayer()
                        printChessboard()
                        stalemate()
                        return true
                    } else
                        println("Invalid input")
                    return false

                } else if (actualTurnsPlayer == secondPlayerName && inputCoordinates.firstPosition - inputCoordinates.secondPosition != 1) {
                    println("Invalid input")
                    return false
                } else if (actualTurnsPlayer == secondPlayerName && inputCoordinates.secondPosition >= inputCoordinates.firstPosition) {
                    println("Invalid input")
                    return false
                } else
                    if (chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] ==
                        Elements.EMPTY.view
                    ) {
                        chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                            Elements.EMPTY.view
                        chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                            Elements.BLACK.view
                        turnsHistory()
                        switchPlayer()
                        printChessboard()
                        stalemate()
                        return true
                    } else
                        println("Invalid input")
                return false
            }
        }
        return false
    }

    // расставляет пешки на стартовые позиции
    private fun startPositionChessBoard() {
        for (i in 0..chessBoard.lastIndex) {
            Elements.WHITE.view.also { chessBoard[1][i] = it }
            Elements.BLACK.view.also { chessBoard[6][i] = it }
        }
    }

    // печатает доску
    private fun printChessboard() {
        println(Elements.LINE.view)
        for (i in 7 downTo 0) {
            print("${i + 1} |")
            for (j in 0..7) print(" ${chessBoard[i][j]} |")
            println("\n" + Elements.LINE.view)
        }
        println(Elements.LETTERS.view)
    }

    // проверяет возможность взятия пешки противника
    private fun checkedPawnsAttack(): Boolean {
        when (actualTurnsPlayer) {
            firstPlayersName -> {
                if (chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.secondLine)] ==
                    Elements.BLACK.view
                ) {
                    chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.secondLine)] =
                        Elements.EMPTY.view
                    chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.secondLine)] =
                        Elements.WHITE.view
                    chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                        Elements.EMPTY.view
                    turnsHistory()
                    printChessboard()
                    stalemate()
                    winConditionToTakingAllPawns()
                    switchPlayer()
                    return true
                }
            }
            secondPlayerName -> {
                if (chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.secondLine)] ==
                    Elements.WHITE.view
                ) {
                    chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.secondLine)] =
                        Elements.EMPTY.view
                    chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.secondLine)] =
                        Elements.BLACK.view
                    chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                        Elements.EMPTY.view
                    turnsHistory()
                    printChessboard()
                    stalemate()
                    winConditionToTakingAllPawns()
                    switchPlayer()
                    return true
                }
            }
        }
        return false
    }

    // конвертим позицию пешки по горизонтали в индексы, чтобы работать с 2д листом
    private fun convertingCharsCoordinateToInt(convert: Char): Int {
        val res = when (convert) {
            'a' -> 0
            'b' -> 1
            'c' -> 2
            'd' -> 3
            'e' -> 4
            'f' -> 5
            'g' -> 6
            'h' -> 7
            else -> 8
        }
        return res
    }

    // пишет предыдище ходы
    private fun turnsHistory() {
        count++
        val res = inputCoordinates.firstLine.toString() + inputCoordinates.firstPosition +
                inputCoordinates.secondLine + inputCoordinates.secondPosition
        saveTurns += res
    }

    // возвращает координаты предыдущего кода
    private fun lastTurn(): String = if (saveTurns.size < 2) saveTurns[0] else saveTurns[count - 1]

    // метод для взятия пешки на проходе - срабатывает если игрок делает первый ход со старт.позиции, а соперник находятся в позиции потенциально, бьющей пешку, через которую та перепрыгнула
    private fun takePawnOnPassant(): Boolean {
        val temp = lastTurn()
        val firstCoordinateLastTurn = temp[1].toString().toInt()
        val secondLineLastTurn = temp[2]
        val secondCoordinateLastTurn = temp[3].toString().toInt()

        when (actualTurnsPlayer) {
            firstPlayersName -> {
                if ((firstCoordinateLastTurn - secondCoordinateLastTurn == 2)
                    && (inputCoordinates.firstPosition == secondCoordinateLastTurn &&
                            (inputCoordinates.firstLine == secondLineLastTurn + 1 ||
                                    inputCoordinates.firstLine == secondLineLastTurn - 1))
                ) {

                    chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                        Elements.EMPTY.view
                    chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.secondLine)] =
                        Elements.WHITE.view
                    chessBoard[secondCoordinateLastTurn - 1][convertingCharsCoordinateToInt(secondLineLastTurn)] =
                        Elements.EMPTY.view
                    turnsHistory()
                    printChessboard()
                    stalemate()
                    winConditionToTakingAllPawns()
                    switchPlayer()
                    return true
                }
            }
            secondPlayerName -> {
                if ((secondCoordinateLastTurn - firstCoordinateLastTurn == 2)
                    && (inputCoordinates.firstPosition == secondCoordinateLastTurn && (inputCoordinates.firstLine == secondLineLastTurn + 1 ||
                            inputCoordinates.firstLine == secondLineLastTurn - 1))
                ) {
                    chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                        Elements.EMPTY.view
                    chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.secondLine)] =
                        Elements.BLACK.view
                    chessBoard[secondCoordinateLastTurn - 1][convertingCharsCoordinateToInt(secondLineLastTurn)] =
                        Elements.EMPTY.view
                    turnsHistory()
                    printChessboard()
                    stalemate()
                    winConditionToTakingAllPawns()
                    switchPlayer()
                    return true
                }
            }
        }
        return false
    }

    // метод проверяющий условие победы при взятии одним из игроков всех пешек противника
    private fun winConditionToTakingAllPawns(): Boolean {

        when (actualTurnsPlayer) {
            firstPlayersName -> {
                if (!chessBoard.flatten().contains(Elements.BLACK.view)) {
                    println("White Wins!")
                    println("Bye!")
                    return true
                }
            }
            secondPlayerName -> {
                if (!chessBoard.flatten().contains(Elements.WHITE.view)) {
                    println("Black Wins!")
                    println("Bye!")
                    return true
                }
            }

        }
        return false
    }

    // метод проверяющий выполнение условия победы, когда одна из пешек доходит до последней линии
    private fun winConditionLastLine(): Boolean {
        when (actualTurnsPlayer) {
            firstPlayersName -> {
                if (inputCoordinates.secondPosition == 8) {
                    chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.secondLine)] =
                        Elements.WHITE.view
                    chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                        Elements.EMPTY.view
                    printChessboard()
                    println("White Wins!")
                    println("Bye!")
                    return true
                }
            }
            secondPlayerName -> {
                if (inputCoordinates.secondPosition == 1) {
                    chessBoard[inputCoordinates.secondPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.secondLine)] =
                        Elements.BLACK.view
                    chessBoard[inputCoordinates.firstPosition - 1][convertingCharsCoordinateToInt(inputCoordinates.firstLine)] =
                        Elements.EMPTY.view
                    printChessboard()
                    println("Black Wins!")
                    println("Bye!")
                    return true
                }
            }
        }
        return false
    }

    // метод проверяющий пат - когда один из игроков в свой ход, имея на доске пешку, не может сделать ход
    private fun stalemate(): Boolean {
        if (winConditionToTakingAllPawns()) {
            return false
        }
        when (actualTurnsPlayer) {
            firstPlayersName -> {
                var countBlackTruePawns = 0

                for (i in 0..chessBoard.lastIndex) {

                    for (j in 0..chessBoard[i].lastIndex) {
                        if (chessBoard[i][j] == Elements.BLACK.view) {
                            val checkBlackTurns = when (j) {
                                in 1..6 -> {
                                    (chessBoard[i - 1][j] != Elements.EMPTY.view) &&
                                            (chessBoard[i - 1][j + 1] != Elements.WHITE.view || chessBoard[i - 1][j - 1] != Elements.WHITE.view)
                                }
                                7 -> {
                                    (chessBoard[i - 1][j] != Elements.EMPTY.view) &&
                                            (chessBoard[i - 1][j - 1] != Elements.WHITE.view)
                                }
                                0 -> {
                                    (chessBoard[i - 1][j] != Elements.EMPTY.view) &&
                                            (chessBoard[i - 1][j + 1] != Elements.WHITE.view)
                                }
                                else -> true
                            }

                            if (!checkBlackTurns) {
                                countBlackTruePawns++
                                break
                            } else continue
                        }

                    }
                }
                if (countBlackTruePawns == 0) {
                    println("Stalemate!")
                    println("Bye!")
                    return true
                }
            }
            secondPlayerName -> {
                var countWhiteTruePawns = 0

                for (i in 0..chessBoard.lastIndex) {
                    for (j in 0..chessBoard[i].lastIndex) {
                        if (chessBoard[i][j] == Elements.WHITE.view) {
                            val checkWhiteTurns = when (j) {
                                in 1..6 -> {
                                    (chessBoard[i + 1][j] != Elements.EMPTY.view) &&
                                            (chessBoard[i + 1][j + 1] != Elements.BLACK.view || chessBoard[i + 1][j - 1] != Elements.BLACK.view)
                                }
                                7 -> {
                                    (chessBoard[i + 1][j] != Elements.EMPTY.view) &&
                                            (chessBoard[i + 1][j - 1] != Elements.BLACK.view)
                                }
                                0 -> {
                                    (chessBoard[i + 1][j] != Elements.EMPTY.view) &&
                                            (chessBoard[i + 1][j + 1] != Elements.BLACK.view)
                                }
                                else -> true
                            }

                            if (!checkWhiteTurns) {
                                countWhiteTruePawns++
                                break
                            } else continue
                        }

                    }
                }
                if (countWhiteTruePawns == 0) {
                    println("Stalemate!")
                    println("Bye!")
                    return true
                }
            }
        }

        return false
    }
}
