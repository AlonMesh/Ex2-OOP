import org.junit.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.concurrent.*;

import static java.lang.Thread.sleep;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);
    /**
     * check if the queue add by priority,
     * set core and max to be 1, because we need
     * that task get in the workqueue.
     *
     * Print - print the priorities og the queue's tasks
     * by the order in the queue
     */
    @Test
    public void our_Test(){
        CustomExecutor customExecutor1 = new CustomExecutor();
        logger.info(()-> "Current maximum priority = " +
                customExecutor1.getCurrentMax());
        for(int i=0; i<5; i++)
        {
            customExecutor1.submit(()-> {
                System.out.println("3OTHER!!!");
                sleep(1000);
                return 1000 * Math.pow(1.02, 30);
            }, TaskType.OTHER);

            logger.info(()-> "Current maximum priority = " +
                    customExecutor1.getCurrentMax());

            customExecutor1.submit(()-> {
                System.out.println("1COMPUTATIONAL!!!");
                return 1000 * Math.pow(1.02, 29);
            }, TaskType.COMPUTATIONAL);

            logger.info(()-> "Current maximum priority = " +
                    customExecutor1.getCurrentMax());

            customExecutor1.submit(()-> {
                System.out.println("2IO!!!");
                return 1000 * Math.pow(1.02, 37);
            }, TaskType.IO);

            logger.info(()-> "Current maximum priority = " +
                    customExecutor1.getCurrentMax());

            customExecutor1.submit(()-> {
                System.out.println("3OTHER!!!");
                return 1000 * Math.pow(1.02, 30);
            }, TaskType.OTHER);

            logger.info(()-> "Current maximum priority = " +
                    customExecutor1.getCurrentMax());

            customExecutor1.submit(()-> {
                System.out.println("3OTHER!!!");
                return 1000 * Math.pow(1.02, 30);
            }, TaskType.OTHER);

            logger.info(()-> "Current maximum priority = " +
                    customExecutor1.getCurrentMax());

            customExecutor1.submit(()-> {
                System.out.println("2IO!!!");
                return 1000 * Math.pow(1.02, 35);
            }, TaskType.IO);

            logger.info(()-> "Current maximum priority = " +
                    customExecutor1.getCurrentMax());
            Callable<String> c = () -> {
                StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
                System.out.println("just Callable");
                return sb.reverse().toString();
            };
            customExecutor1.submit(c);

            logger.info(()-> "Current maximum priority = " +
                    customExecutor1.getCurrentMax());
            Task<?> task = Task.createTask(() -> {
                int sum = 0;
                System.out.println("as task");
                for (int j = 1; j <= 10; j++) {
                    sum += j;
                }
                return sum;
            }, TaskType.COMPUTATIONAL);
            customExecutor1.submit(task);

            logger.info(()-> "5Current maximum priority = " +
                    customExecutor1.getCurrentMax());
        }

        logger.info(()-> "Current maximum priority = " +
                customExecutor1.getCurrentMax());

        customExecutor1.gracefullyTerminate();
    }
}
