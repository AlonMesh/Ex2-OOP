# Ex2-OOP
## Ex2_1 (Part1)

A Java class containing methods for creating text files, and counting the number of lines in these files using different approaches:
* Sequentially
* Using threads
* Using a thread pool

### Methods
#### createTextFiles(int n, int seed, int bound)
Creates n text files, with names "name_i.txt", where i is the index of the file (starting from 1). Each file will contain a number of "Hello World" lines, generated randomly using the seed and bound values as follows:
* A random number x is generated between 0 (inclusive) and bound (exclusive) using java.util.Random
* x "Hello World" lines are written to the file

#### getNumOfLines(String[] fileNames)
Counts the number of lines in the text files specified by the fileNames array, using a sequential approach.

#### getNumOfLinesThreads(String[] fileNames)
Counts the number of lines in the text files specified by the fileNames array, using a multi-threaded approach. A separate CountLines thread is created for each file, and the threads are started using the run() method. The main thread then waits for all of the CountLines threads to finish using the join() method. Finally, the number of lines in each file is summed to get the total number of lines.

#### getNumOfLinesThreadPool(String[] fileNames)
Counts the number of lines in the text files specified by the fileNames array, using a thread pool. A fixed size thread pool is created using java.util.concurrent.Executors, and a CountLinesPool task is submitted to the pool for each file. The main thread then waits for the tasks to complete and sums the number of lines in each file to get the total number of lines.

### Inner Classes
#### CountLines
A Java thread class used by getNumOfLinesThreads(String[] fileNames) to count the number of lines in a single file.

#### CountLinesPool
A java.util.concurrent.Callable implementation used by getNumOfLinesThreadPool(String[] fileNames) to count the number of lines in a single file. Can be submitted to an java.util.concurrent.Executor or java.util.concurrent.ExecutorService for execution.

## Ex2_2 (Part2) - Task Execution
### Summary

The CustomExecutor is designed to run tasks with different priorities. When a task is submitted to the executor, it is added to a PriorityBlockingQueue based on its priority. The PriorityBlockingQueue is a queue that sorts the tasks based on their priority, with the highest priority tasks at the front of the queue.

The CustomExecutor uses a pool of threads to run the tasks. When a thread becomes available, it takes the next highest priority task from the front of the queue and executes it. This ensures that tasks with higher priorities are completed before tasks with lower priorities.

The CustomExecutor also has a field holder_threads_count, which is an array of integers representing the number of threads currently running tasks with each TaskType. This can be used to track the number of tasks being run for each priority level and ensure that tasks are being distributed appropriately.

The CustomExecutor extends the ThreadPoolExecutor class, which provides several methods for managing the pool of threads and the tasks being run. Some of the major functions used by the CustomExecutor include:

* execute(Runnable command): adds the given Runnable task to the queue for execution by a thread in the pool
* submit(Callable task): adds the given Callable task to the queue and returns a Future object that can be used to track the task's progress and retrieve the result when it is complete
* shutdown(): begins the process of shutting down the executor, rejecting any new tasks that are submitted
* awaitTermination(long timeout, TimeUnit unit): blocks until all tasks have completed execution after a shutdown request, or the timeout occurs, whichever happens first.

The Task class implements the **Callable** interface and provides a **call()** method that is executed by a thread when the task is run. The call() method should contain the code for the task that needs to be performed.

The **compareTo** method of the Task class is used to compare the priority of two tasks. It compares the taskType values of the tasks, with the task having a higher priority being ranked higher. This is used by the PriorityBlockingQueue to sort the tasks based on their priority.

### Classes
#### Task
The Task class represents a unit of work that can be submitted to the CustomExecutor for execution. It implements the Callable interface, which allows it to be run by a thread and return a result. The Task class has the following fields and methods:

##### Fields
taskType: an enum of type TaskType representing the priority of the task
callable: a Callable object that contains the code for the task

##### Methods
Task(Callable<T> callable): constructs a new Task with OTHER as the taskType and the given callable
Task(Callable<T> callable, TaskType taskType): constructs a new Task with the given callable and taskType
Task(TaskType taskType, Callable<T> callable): constructs a new Task with the given taskType and callable
static Task createTask(Callable callable, TaskType taskType): creates a new Task with the given callable and taskType
static Task createTask(Task task): creates a new Task with the same callable and taskType as the given task
T call() throws Exception: executes the task's callable and returns the result
int compareTo(T other): compares the priority of this Task to the given other Task, returning a negative integer if this Task has a higher priority, a positive integer if it has a lower priority, or zero if the priorities are equal

  
