package cinema

import kotlin.math.floor

fun main() {

    println("Enter the number of rows:")
    val rows = readLine()!!.toInt()
    println("Enter the number of seats in each row:")
    val numberSeatsInEachRow = readLine()!!.toInt()
    val MyCinema = CinemaRoom(rows, numberSeatsInEachRow)
    MyCinema.mainMenu()

}

class CinemaRoom(rows: Int, numberSeatRoom: Int ) {
    private val rowsRoom = rows
    private val seatRoom = numberSeatRoom
    private val totalSeats = numberSeatRoom * rows
    private val seatsList = mutableListOf<Seats>()
    private val priceList = mutableListOf<Int>()
    private val currentIncome = mutableListOf<Int>()

    private val price = 10
    private val bigPrice = 8

    fun mainMenu() {
        createRoom()
        inputMenu()
    }

    private fun inputMenu() {
        do {
            println("\n" +
                "1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit"
            )
            when (readLine()!!) {
                "1" -> printCinema()
                "2" -> calcProfit()
                "3" -> statisticsCinema()
                "0" -> break
                else -> break
            }
        } while (true)
    }

    private fun statisticsCinema() {
        var curIt = 0
        var totalIt = 0
        val numberOfPurchasedTickets = seatsList.filter { it.status == false }
        numberOfPurchasedTickets.size
        println("Number of purchased tickets: ${numberOfPurchasedTickets.size}")
        println("Percentage: ${"%.2f".format((numberOfPurchasedTickets.size * 100.00 / totalSeats.toDouble()))}%")
        for ( i in numberOfPurchasedTickets) curIt += i.price
        println("Current income: $${curIt}")
        for (i in seatsList) totalIt += i.price
        println("Total income: $${totalIt}")
    }

    private fun createRoom() {
        val numberList = mutableListOf<String>()
        for (q in 1 .. rowsRoom)
        for (i in 1 .. seatRoom) {
            numberList.add("$q$i")
        }
        for (i in  0 until  totalSeats) {
            if (totalSeats <= 60)
            seatsList.add(Seats(numberList[i].toInt() / 10,numberList[i].toInt() % 10,
                                'S', price,true))
            else
                if ((numberList[i].toInt() / 10) <= (rowsRoom / 2))
                seatsList.add(Seats(numberList[i].toInt() / 10,numberList[i].toInt() % 10,
                    'S', price,true))
            else
                seatsList.add(Seats(numberList[i].toInt() / 10,numberList[i].toInt() % 10,
                        'S', bigPrice,true))
        }
    }

    private fun calcProfit() {
        println("Enter a row number:")
        val rows = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        val numberSeatsInEachRow = readLine()!!.toInt()
        if (rows > rowsRoom || numberSeatsInEachRow > seatRoom) {
            println("Wrong input!")
            calcProfit()
        } else
        if (!seatsList[((rows - 1) * seatRoom + numberSeatsInEachRow)-1].status) {
            println("That ticket has already been purchased!")
            calcProfit()
        } else {
            var buyTicket = 0
            buyTicket = if (rows == 1) numberSeatsInEachRow
            else
                (rows - 1) * seatRoom + numberSeatsInEachRow
            seatsList[buyTicket - 1].ticket = 'B'
            seatsList[buyTicket - 1].status = false
            print("Ticket price: ")
            println("$${seatsList[buyTicket - 1].price}")
        }
    }

    private fun printCinema() {
        printTop()
        printRow()
    }

    private fun printRow() {
        var l = 0
        println()
        for (j in 1..rowsRoom) {
            for (i in 0..seatRoom) {
                if (i == 0) {
                    print("$j ")
                    l -= 1
                }
                else
                    print("${seatsList[l].ticket} ")
                    l +=1
            }
            println()
        }
    }

    private fun printTop() {
        println("\nCinema:")
        for (i in 0 .. seatRoom) {
            if (i == 0) print("  ")
                else
                    print("$i ")
        }
    }
}

data class Seats(
    var row: Int,
    var numberSeats: Int,
    var ticket: Char,
    var price: Int,
    var status: Boolean
) {
    operator fun invoke(k: Int, i: Int, ch: Char, p: Int, b: Boolean) {
            row = k
            numberSeats = i
            ticket = ch
            price = p
            status = b
    }
}
