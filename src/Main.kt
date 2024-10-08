import java.io.File

/**
 * Entry point for the Task Buddy application.
 * This function handles the main application loop, allowing users to interact with tasks.
 */
fun main() {

    println("\nWelcome to Task Buddy!\n")

    // List to store tasks
    val tasks = mutableListOf<String>()

    // Main loop for the application
    while (true) {
        displayMenu()

        when (val choice = readln()) {
            "1" -> viewTasks(tasks)
            "2" -> addTask(tasks)
            "3" -> removeTask(tasks)
            "4" -> completeTask(tasks)
            "5" -> saveTasks(tasks)
            "6" -> loadTasks(tasks)
            "7" -> {
                println("\nThank you for using Task Buddy. Goodbye!")
                break
            }
            else -> println("\nInvalid input. Please try again.\n")
        }
    }
}

/**
 * Displays the main menu to the user, listing available options.
 */
fun displayMenu() {
    println("Menu:")
    println(" 1. View Tasks")
    println(" 2. Add Task")
    println(" 3. Remove Task")
    println(" 4. Complete Task")
    println(" 5. Save Tasks")
    println(" 6. Load Tasks")
    println(" 7. Quit")
    println("Please enter your choice: ")
}

/**
 * Displays the current list of tasks.
 *
 * @param tasks The list of tasks to be viewed.
 */
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

/**
 * Allows the user to add a new task to the list.
 *
 * @param tasks The mutable list to which the new task will be added.
 */
fun addTask(tasks: MutableList<String>) {
    println("\nEnter new task: ")
    val newTask = readlnOrNull()

    if (newTask.isNullOrBlank()) {
        println("Task can't be empty.\n")
    } else {
        // Adds a new task with default status (not completed)
        tasks.add("$newTask | |")
        println("\nThe new task was successfully added.\n")
    }
}

/**
 * Allows the user to remove a task from the list.
 * If there are no tasks, a message will be displayed and the function returns early.
 *
 * @param tasks The mutable list of tasks.
 */
fun removeTask(tasks: MutableList<String>) {
    if (tasks.isEmpty()) {
        println("\nThere are no tasks to remove. Please add or load tasks.\n")
        return
    }

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

/**
 * Marks a task as completed or uncompleted.
 * If there are no tasks, a message will be displayed and the function returns early.
 *
 * @param tasks The mutable list of tasks.
 */
fun completeTask(tasks: MutableList<String>) {
    if (tasks.isEmpty()) {
        println("\nThere are no tasks to complete. Please add or load tasks.\n")
        return
    }

    viewTasks(tasks)
    println("Enter the number of the task you'd like to mark as complete: ")
    val taskToComplete = readLine()?.toIntOrNull()

    if (taskToComplete != null && taskToComplete in 1..tasks.size) {
        val taskIndex = taskToComplete - 1
        val parts = tasks[taskIndex].split(" | ")
        if (parts.size == 2) {
            val taskText = parts[0]
            val newStatus = if (parts[1] == "X") " " else "X" // Toggles completion status
            tasks[taskIndex] = "$taskText |$newStatus|"
            println("\nCongratulations! You completed task number $taskToComplete.\n")
        } else {
            println("\nError: Task format is incorrect.\n")
        }
    } else {
        println("\nInvalid task number.\n")
    }
}

/**
 * Saves the current tasks to a file specified by the user.
 * If there are no tasks, a message will be displayed indicating that there is nothing to save.
 *
 * @param tasks The list of tasks to be saved.
 */
fun saveTasks(tasks: List<String>) {
    if (tasks.isEmpty()) {
        println("\nThere are no tasks to be saved. Please add or load tasks.\n")
    } else {
        println("\nEnter the name of the file you want the tasks to be saved to: ")
        val fileName = readln()

        try {
            File(fileName).writeText(tasks.joinToString("\n")) // Saves tasks to a file
            println("\nTasks were successfully saved in $fileName\n")
        } catch (e: Exception) {
            println("\nAn error occurred while saving tasks: ${e.message}\n")
        }
    }
}

/**
 * Loads tasks from a file specified by the user.
 *
 * @param tasks The mutable list of tasks to load into.
 * @return True if tasks were successfully loaded, otherwise false.
 */
fun loadTasks(tasks: MutableList<String>): Boolean {
    print("\nEnter the file name where your tasks are saved: ")
    val fileName = readLine() ?: return false

    return try {
        val loadedTasks = File(fileName).readLines() // Reads tasks from the file
        tasks.clear() // Clears existing tasks before loading new ones
        tasks.addAll(loadedTasks)
        println("\nTasks from $fileName successfully loaded.\n")
        true
    } catch (e: Exception) {
        println("\nThe file was not found or an error occurred: ${e.message}\n")
        false
    }
}