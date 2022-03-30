import java.util.*

class Parking() {
    private var spotList = mutableListOf<ParkingSpot>()
    fun mainMenu() {
        do {
            val numberCarAndColor = readLine()!!.split(" ".toRegex()).toMutableList()
            when (numberCarAndColor[0]) {
                "reg_by_color" -> {
                    if (numberCarAndColor.size == 2) {
                        val numberCarAndColorIndexOne = numberCarAndColor[1]
                    reg_by_color(numberCarAndColorIndexOne)
                    } else continue
                }
                "spot_by_color" -> {
                    if (numberCarAndColor.size == 2) {
                        val numberCarAndColorIndexOne = numberCarAndColor[1]
                        spot_by_color(numberCarAndColorIndexOne)
                    } else continue
                }
                "spot_by_reg" -> {
                    if (numberCarAndColor.size == 2) {
                        val numberCarAndColorIndexOne = numberCarAndColor[1]
                    spot_by_reg(numberCarAndColorIndexOne)
                    } else continue
                }
                "park" -> park(numberCarAndColor)
                "leave" -> {
                    if (numberCarAndColor.size == 2) {
                        val numberCarAndColorIndexOne = numberCarAndColor[1]
                        leave(numberCarAndColorIndexOne)
                    } else continue
                }
                "create" -> {
                    if (numberCarAndColor.size == 2) {
                    val numberCarAndColorIndexOne = numberCarAndColor[1]
                    createParking(numberCarAndColorIndexOne)
                    } else continue
                }
                "status" -> statusParking()
                else -> break
            }
        } while (true)
        System.exit(-1)
    }
    private fun spot_by_reg(numberCarAndColorIndexOne: String) {
        if (spotList.size == 0) {
            println("Sorry, a parking lot has not been created.")
            mainMenu()
        }
        val statusListFilter = spotList.filter { it.numberCarInToSpot.lowercase(Locale.getDefault()) ==
                numberCarAndColorIndexOne.lowercase(Locale.getDefault()) }
        val strOut = mutableListOf<String>()
        if (statusListFilter.isEmpty()) {
            print("No cars with registration number $numberCarAndColorIndexOne were found.")
        } else
        {
            for (i in statusListFilter) {
                strOut.add(i.numberSpot.toString())
            }
        }
        println(strOut.joinToString(", "))
        mainMenu()
    }

    private fun spot_by_color(numberCarAndColorIndexOne: String) {
        if (spotList.size == 0) {
            println("Sorry, a parking lot has not been created.")
            mainMenu()
        }
        val statusListFilter = spotList.filter { it.colorCar.lowercase(Locale.getDefault()) ==
                numberCarAndColorIndexOne.lowercase(Locale.getDefault()) }
        val strOut = mutableListOf<String>()
        if (statusListFilter.isEmpty()) {
            print("No cars with color $numberCarAndColorIndexOne were found.")
        } else
        {
            for (i in statusListFilter) {
                strOut.add(i.numberSpot.toString())
            }
        }
        println(strOut.joinToString(", "))
        mainMenu()
    }

    private fun reg_by_color(numberCarAndColorIndexOne: String) {
        if (spotList.size == 0) {
            println("Sorry, a parking lot has not been created.")
            mainMenu()
        }
        val statusListFilter = spotList.filter { it.colorCar.lowercase(Locale.getDefault()) ==
                numberCarAndColorIndexOne.lowercase(Locale.getDefault()) }
            val strOut = mutableListOf<String>()
            if (statusListFilter.isEmpty()) {
                print("No cars with color $numberCarAndColorIndexOne were found.")
            } else
                {
                    for (i in statusListFilter) {
                        strOut.add(i.numberCarInToSpot)
            }
        }
        println(strOut.joinToString(", "))
        mainMenu()
    }

    private fun statusParking() {
        if (spotList.size == 0) {
            println("Sorry, a parking lot has not been created.")
            mainMenu()
        }

        val statusListFilter = spotList.filter { it.status == true }
        if (statusListFilter.isEmpty()) {
            println("Parking lot is empty.")
        } else {
            for (i in statusListFilter) {
                println("${i.numberSpot} ${i.numberCarInToSpot} ${i.colorCar}")
            }
        }
        mainMenu()
    }

    private fun createParking(numberCarAndColorIndexOne: String) {
        spotList.removeAll { it.numberSpot >=1 }
        val parkingSize = numberCarAndColorIndexOne.toInt()
        for (i in 0 until parkingSize){
           spotList.add(ParkingSpot(i+1," "," ",false))
        }
        println("Created a parking lot with $parkingSize spots.")
        mainMenu()
    }

    private fun park(numberCarAndColor: MutableList<String> ) {
        val numberCar = numberCarAndColor[1]
        val colorCar = numberCarAndColor[2]
        if (spotList.size == 0) {
            println("Sorry, a parking lot has not been created.")
            mainMenu()
        } else {
            val spotListFilter = spotList.filter { it.status == false }
            if (!spotListFilter.isEmpty()) {
                val index = spotListFilter.first().numberSpot
                    spotList[index-1](numberCar, colorCar, true)
                    println("$colorCar car parked in spot $index.")
            } else {
                    println("Sorry, the parking lot is full.")
                    }
                }
                mainMenu()
    }

    private fun leave(numberCarAndColorIndexOne: String) {
            if (spotList.size == 0) {
                println("Sorry, a parking lot has not been created.")
                mainMenu()
            }
            if (spotList.size >= 1) {
                val cleanIndex = numberCarAndColorIndexOne.toInt() - 1
                spotList[cleanIndex](" ", " ", false)
                println("Spot $numberCarAndColorIndexOne is free.")
                mainMenu()
            } else mainMenu()

    }
}
