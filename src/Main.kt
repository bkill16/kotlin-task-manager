import java.io.File

fun main() {

    println("\nWelcome to Task Buddy!\n")

    val tasks = mutableListOf<String>()

    while (true) {
        displayMenu()

        when (val choice = readln()) {
            "1" -> viewTasks(tasks)
            "2" -> addTask(tasks)
            "3" -> removeTask(tasks)
            "4" -> saveTasks(tasks)
            "5" -> loadTasks(tasks)
            "6" -> {
                println("\nThank you for using Task Buddy. Goodbye!")
                break
            }
            else -> println("Invalid input. Please try again.\n")
        }
    }
}

fun displayMenu() {
    println("Menu:")
    println(" 1. View Tasks")
    println(" 2. Add Task")
    println(" 3. Remove Task")
    println(" 4. Save Tasks")
    println(" 5. Load Tasks")
    println(" 6. Quit")
    println("Please enter your choice: ")
}

fun viewTasks(tasks: List<String>) {
    if (tasks.isEmpty()) {
        println("\nThere are no tasks in your task list. Please add or load tasks.\n")
    } else {
        println("\nTask List\n")
        tasks.forEachIndexed { index, task ->
            println("${index + 1}. $task")
        }
        println()
    }
}

fun addTask(tasks: MutableList<String>) {
    println("\nEnter new task: ")
    val newTask = readlnOrNull()

    if (newTask.isNullOrBlank()) {
        println("Task can't be empty.\n")
    } else {
        tasks.add(newTask)
        println("\nThe new task was successfully added.\n")
    }
}

fun removeTask(tasks: MutableList<String>) {
    viewTasks(tasks)

    println("Enter the number of the task you'd like to remove: ")
    val taskToRemove = readLine()?.toIntOrNull()

    if (taskToRemove != null && taskToRemove in 1..tasks.size) {
        tasks.removeAt(taskToRemove - 1)
        println("\nTask number $taskToRemove has been removed from the task list.\n")
    } else {
        println("\nInvalid task number.\n")
    }
}

fun saveTasks(tasks: List<String>){
    if (tasks.isEmpty()) {
        println("\nThere are no tasks to be saved.\n")
    } else {
        println("\nEnter the name of the file you want the tasks to be saved to: ")
        val fileName = readln()

        try {
            File(fileName).writeText(tasks.joinToString("\n"))
            println("\nTasks were successfully saved in $fileName\n")
        } catch (e: Exception) {
            println("\nAn error occurred while saving tasks: ${e.message}\n")
        }
    }
}

fun loadTasks(tasks: MutableList<String>): Boolean {
    print("\nEnter the file name where your tasks are saved: ")
    val fileName = readLine() ?: return false

    return try {
        val loadedTasks = File(fileName).readLines()
        tasks.clear()
        tasks.addAll(loadedTasks)
        println("\nTasks from $fileName successfully loaded.\n")
        true
    } catch (e: Exception) {
        println("\nThe file was not found or an error occurred: ${e.message}\n")
        false
    }
}