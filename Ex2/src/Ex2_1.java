import java.io.*;
import java.lang.Thread;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.Executors;
import java.util.concurrent.Callable;

public class Ex2_1 {

    // n = number of wanted files (array.size = n).
    // seed & bounds -> parameters for randomized.
    public static String[] createTextFiles(int n, int seed, int bound)
    {
        String[] files = new String[n];

        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            int amount_of_lines = rand.nextInt(bound); // x = a random value between [0, bound)
            //System.out.print(amount_of_lines+", "); // Print the amount of liens for each file.

            try
            {
                // Create a new File object with the file name "name_i.txt"
                FileWriter file = new FileWriter("name_" + (i+1) + ".txt");

                // Insert "Hello world" rows to the file
                for (int j = 0; j < amount_of_lines; j++)
                {
                    file.write("Hello World\n");
                }

                // file.close() actually "creates" a file from the FileWriter object.
                file.close();
                files[i] = ("name_" + (i+1));

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return files;
    }

    public static int getNumOfLines(String[] fileNames) throws IOException {
        int totalLines = 0;

        for (String line : fileNames)
        {

            // FileReader creates an object that holds a file by its path (not it's context)
            FileReader fileReader = new FileReader(line + ".txt");

            // BufferedReader creates an object that get the *contexts* of FileReader object.
            BufferedReader contextReader = new BufferedReader(fileReader);

            while (contextReader.readLine() != null)
            {
                totalLines++;
            }

        }
        return totalLines;
    }

    public int getNumOfLinesThreads(String[] fileNames) throws InterruptedException {
        int totalLines = 0;

        CountLines[] threads = new CountLines[fileNames.length];

        for (int i = 0; i < fileNames.length; i++)
        {
            threads[i] = new CountLines("name_" + (i+1) + ".txt");
            threads[i].run();
        }

        // Now let's wait till every thread is finish counting
        for (CountLines thread : threads)
        {
            thread.join();
        }

        // Now sum all lines
        for (CountLines thread : threads)
        {
            totalLines += thread.getLineAmount();
        }

        return totalLines;
    }

    public int getNumOfLinesThreadPool(String[] fileNames) throws Exception {
        int totalLines = 0;

        // Creating a pool by the build-in Executor class.
        ExecutorService executor = Executors.newFixedThreadPool(fileNames.length);
        ArrayList<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < fileNames.length; i++)
        {
            //Callable<Integer> task = new CountLinesPool("name_" + (i+1) + ".txt");
            CountLinesPool task = new CountLinesPool("name_" + (i+1) + ".txt");
            Future<Integer> future = executor.submit(task);
            futures.add(future);
        }

        for (Future<Integer> future : futures)
        {
            totalLines += future.get();
        }

        executor.shutdown();
        return totalLines;
    }
}

class CountLinesPool implements Callable
{
    String fileName; //1 2 3 4 5 6

    public CountLinesPool(String fileName) throws Exception {
        this.fileName = fileName;
    }

    @Override
    public Integer call() throws Exception {

        int lineAmount = 0;

        FileReader fileReader = new FileReader(this.fileName);
        BufferedReader contextReader = new BufferedReader(fileReader);

        while (contextReader.readLine() != null)
        {
            lineAmount++;
        }

        return lineAmount;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

class CountLines extends Thread
{
    String fileName;
    int lineAmount;

    // Constructor as we asked
    public CountLines(String fileName)
    {
        this.fileName = fileName;
        this.lineAmount = 0;
    }

    @Override
    public void run()
    {
        super.run(); // Let the thread start running (from class Thread)

        FileReader fileReader = null;
        try
        {
            fileReader = new FileReader(this.fileName);
            BufferedReader contextReader = new BufferedReader(fileReader);

            while (contextReader.readLine() != null)
            {
                this.lineAmount++;
            }

        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public int getLineAmount() {
        return lineAmount;
    }
}
