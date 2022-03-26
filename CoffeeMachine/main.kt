package machine

import java.math.BigDecimal

class CoffeeMachine {
    companion object {
        // Initial values of supplies
        private const val WATER_INIT = 400
        private const val MILK_INIT = 540
        private const val BEANS_INIT = 120
        private const val DISPOSABLE_CUPS_INIT = 9
        private const val CASH_INIT = 550
    }

    private var water = WATER_INIT
    private var milk = MILK_INIT
    private var beans = BEANS_INIT
    private var cash = BigDecimal(CASH_INIT)
    private var disposableCups = DISPOSABLE_CUPS_INIT
    private var coffeePowder = 0

    enum class Coffee(val label: String, val water: Int, val milk: Int, val beans: Int, val cost: BigDecimal) {
        ESPRESSO ("espresso", 250, 0, 16, BigDecimal(4)),
        LATTE ("latte", 350, 75, 20, BigDecimal(7)),
        CAPPUCCINO ("cappuccino", 200, 100, 12, BigDecimal(6)),
        ;
    }

    enum class Command(val cmd: String) {
        BUY ("buy"),
        FILL ("fill"),
        TAKE ("take"),
        REMAINING ("remaining"),
        EXIT ("exit"),
        ;
    }

    enum class SuppliesState(val text: String) {
        ENOUGH ("I have enough resources, making you a coffee!"),
        NOT_ENOUGH_WATER ("Sorry, not enough water!"),
        NOT_ENOUGH_MILK ("Sorry, not enough milk!"),
        NOT_ENOUGH_BEANS ("Sorry, not enough coffee beans!"),
        NOT_ENOUGH_CUPS ("Sorry, not enough disposable cups!"),
        ;
    }

    private fun printState() {
        println("The coffee machine has:")
        println("$water ml of water")
        println("$milk ml of milk")
        println("$beans g of coffee beans")
        println("$disposableCups disposable cups")
        println("\$$cash of money")
    }

    /** Starts interaction with coffee machine */
    fun run() {
        while (true) {
            val commands = Command.values().joinToString(", ", "(", ")", transform = { it.cmd })
            print("Write action $commands: ")
            val command = readln()
            println()

            when (command) {
                Command.BUY.cmd -> buy()
                Command.FILL.cmd -> fill()
                Command.TAKE.cmd -> take()
                Command.REMAINING.cmd -> printState()
                Command.EXIT.cmd -> break
            }

            println()
        }
    }

    // region Buy
    private fun buy() {
        val options = Coffee.values().joinToString(", ", transform = { "${it.ordinal + 1} - ${it.label}" })
        print("What do you want to buy? $options, back - to main menu: ")

        val option = readln()
        if (option == "back") return
        val coffee = Coffee.values()[option.toInt() - 1]

        val state = checkSupplies(coffee)
        println(state.text)

        if (state == SuppliesState.ENOUGH) {
            makeCoffee(coffee)
        }
    }

    private fun checkSupplies(coffee: Coffee, num: Int = 1): SuppliesState {
        if (water < coffee.water) return SuppliesState.NOT_ENOUGH_WATER
        if (milk < coffee.milk) return SuppliesState.NOT_ENOUGH_MILK
        if (beans < coffee.beans) return SuppliesState.NOT_ENOUGH_BEANS
        if (disposableCups < 1) return SuppliesState.NOT_ENOUGH_CUPS

        return SuppliesState.ENOUGH
    }

    private fun makeCoffee(coffee: Coffee, cups: Int = 1) {
        for (i in 1..cups) {
            makeCup(coffee)
        }
    }

    private fun makeCup(coffee: Coffee) {
        processPayment(coffee)
        grindBeans(coffee)
        boilWater(coffee)
        mixingWaterAndPowder(coffee)
        pourCoffee()
        pourMilk(coffee)
    }

    private fun processPayment(coffee: Coffee) {
        cash += coffee.cost
    }

    private fun grindBeans(coffee: Coffee) {
        beans -= coffee.beans
        coffeePowder += coffee.beans
    }

    private fun boilWater(coffee: Coffee) {
        water -= coffee.water
    }

    private fun mixingWaterAndPowder(coffee: Coffee) {
        coffeePowder -= coffee.beans
    }

    private fun pourCoffee() {
        disposableCups--
    }

    private fun pourMilk(coffee: Coffee) {
        milk -= coffee.milk
    }
    // endregion

    private fun fill() {
        print("Write how many ml of water do you want to add: ")
        water += readln().toInt()
        print("Write how many ml of milk do you want to add: ")
        milk += readln().toInt()
        print("Write how many grams of coffee beans do you want to add: ")
        beans += readln().toInt()
        print("Write how many disposable cups of coffee do you want to add: ")
        disposableCups += readln().toInt()
    }

    private fun take() {
        println("I gave you \$$cash")
        cash = BigDecimal.ZERO
    }
}

fun main() {
    val coffeeMachine = CoffeeMachine()
    coffeeMachine.run()
}
