package bot

import java.util.Scanner

val scanner = Scanner(System.`in`)

// main function to run all other functions
fun main() {
    greet("C2", "2022")
    remindName()
    guessAge()
    count()
    test()
    end()
}

// function to greet the user
fun greet(assistantName: String, birthYear: String) {
    println("Hello! My name is ${assistantName}.")
    println("I was created in ${birthYear}.")
    println("Please, remind me your name.")
}

// getting the user to remind the assistant
fun remindName() {
    val name = scanner.nextLine()
    println("What a great name you have, ${name}!")
}

// function to let the assistant guess the user's age
fun guessAge() {
    println("Let me guess your age.")
    println("Enter remainders of dividing your age by 3, 5 and 7.")
    val rem3 = scanner.nextInt()
    val rem5 = scanner.nextInt()
    val rem7 = scanner.nextInt()
    val age = (rem3 * 70 + rem5 * 21 + rem7 * 15) % 105
    println("Your age is ${age}; that's a good time to start programming!")
}

// function to show the user that the assistant can count
fun count() {
    println("Now I will prove to you that I can count to any number you want.")
    val num = scanner.nextInt()
    for (i in 0..num) {
        print(i)
        println("!")
    }
}

// test function to test the user's knowledge
fun test() {
    println("Let's test your programming knowledge.")
    println("Why do we use methods?") // question to ask the user
    // array to store the answers of teh question
    val myArray = arrayOf("1. To repeat a statement multiple times.",
                          "2. To decompose a program into several small subroutines.",
                          "3. To determine the execution time of a program.",
                          "4. To interrupt the execution of a program.")
    // loop to display all the items in the array to the user
    for (items in myArray) {
        println(items)
    }
    // reassigning the values of the array to integers so that they match the answers
    myArray[0] = "1"
    myArray[1] = "2"
    myArray[2] = "3"
    myArray[3] = "4"
    
  // getting user input
    var userInput = readLine()!!
  // loop to check if user input matches items in the array
    while (userInput != myArray[1]) {
        println("Please, try again.")
        userInput = readLine()!!
    }
}
// end function to display goodbye message
fun end() {
    println("Congratulations, have a nice day!") // Do not change this text
}
