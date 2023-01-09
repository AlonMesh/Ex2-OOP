import java.io.File;
import java.io.IOException;

public class Main {

    public static void deleteAllFiles(int N) {
        for (int i = 0; i < N; i++) {
            File file = new File("name_"+(i+1) + ".txt");
            file.delete();
        }
    }

    public static void main(String[] args) throws Exception {

        int numProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Number of processors available to the JVM: " + numProcessors);
        System.out.println("So we can use " + numProcessors/2 + " to " + (numProcessors-1));

        CustomExecutor customExecutor = new CustomExecutor<>();
        
        int N = 10000;

        //deleteAllFiles(N);

//        String[] files = new String[N];
//        files = Ex2_1.createTextFiles(N, 31, 300);
//
//        long startTime = System.currentTimeMillis();
//        System.out.println(Ex2_1.getNumOfLines(files));
//        long endTime = System.currentTimeMillis();
//        System.out.println("Sequentially Elapsed time: " + (endTime - startTime) + " milliseconds");
//        deleteAllFiles(N);
//
//        Ex2_1 ex2 = new Ex2_1();
//
//        files = Ex2_1.createTextFiles(N, 32, 300);
//        startTime = System.currentTimeMillis();
//        System.out.println(ex2.getNumOfLinesThreads(files));
//        endTime = System.currentTimeMillis();
//        System.out.println("Threads Elapsed time: " + (endTime - startTime) + " milliseconds");
//        deleteAllFiles(N);
//
//        files = Ex2_1.createTextFiles(N, 33, 300);
//        startTime = System.currentTimeMillis();
//        System.out.println(ex2.getNumOfLinesThreadPool(files));
//        endTime = System.currentTimeMillis();
//        System.out.println("Thread pool Elapsed time: " + (endTime - startTime) + " milliseconds");
//        deleteAllFiles(N);
    }
}