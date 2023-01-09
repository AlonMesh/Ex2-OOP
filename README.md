# Ex2-OOP
## Ex2_1 - Part1

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
