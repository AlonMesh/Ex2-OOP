import java.util.Comparator;
import java.util.concurrent.*;

public class CustomExecutor<T> extends ThreadPoolExecutor implements Executor {
    private int[] holder_threads_count;

    public CustomExecutor() {
        super(Runtime.getRuntime().availableProcessors()/2, Runtime.getRuntime().availableProcessors()-1, 300, TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(Runtime.getRuntime().availableProcessors()/2,Comparator.comparing(t -> ((PriorityFuture) t))));
        holder_threads_count = new int[10];

        for (int num : holder_threads_count) {
            num = 0;
        }

    }

    public PriorityFuture<T> submit(Task task) {
        // Each task has priorityValue.
        // The array holder_threads_count hold at place (i-1) the amount of task with priority i
        holder_threads_count[task.taskType.getPriorityValue()-1]++;

        PriorityFuture<T> priorityFuture = PriorityFuture.createPriorityFuture(task);

        execute(priorityFuture); // calls the underlying ThreadPoolExecutor's execute method which will add the task to the Queue

        return priorityFuture; // represents the result
        // used to check the status of the computation, retrieve its result, or cancel it
    }

    public PriorityFuture<T> submit(Callable<T> callable, TaskType taskType) {
        Task task = Task.createTask(callable, taskType);
        return submit(task);
    }

    public PriorityFuture<T> submit(Callable callable) {
        Task task = new Task(callable);
        return submit(task);
    }

    @Override
    protected void beforeExecute(Thread thread, Runnable run) {
        // As long as there is a task with priority 1 in the queue, the task that was sent is
        // sure will be priority 1. So only after all the tasks with priority 1 is done (=0),
        // it'll go to priority 2 and then for 3.
        for (int i = 0; i < holder_threads_count.length; i++) {
            if (holder_threads_count[i] > 0) {
                holder_threads_count[i]--;
                break;
            }
        }

        super.beforeExecute(thread, run);
    }
        public int getCurrentMax() {
            for (int i = 0; i < holder_threads_count.length; i++) {
                if (holder_threads_count[i] > 0) {
                    return (i+1);
                }
            }
            return 0;
        }

        public void gracefullyTerminate() {
            super.shutdown();
        }

}