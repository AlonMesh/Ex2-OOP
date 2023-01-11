import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/** PriorityFuture is a subclass of FutureTask with a priority field
 * It implements the Comparable interface, allowing tasks to be sorted by priority
 * The compareTo method is used to compare PriorityFuture instances and determine their relative priority
 * Tasks with a lower priority value will have a lower priority in the queue.
 */

public class PriorityFuture<T> extends FutureTask<T> implements Comparable {
    private final int priority; // The Future is about to get sorted, so changing priority is not available anymore.

    public PriorityFuture(Callable<T> callable, int priority) {
        super(callable);
        this.priority = priority;
    }

    public static PriorityFuture createPriorityFuture(Task task) {
        return new PriorityFuture(task.callable, task.taskType.getPriorityValue());
    }

    @Override
    public int compareTo(Object other) {
        return Integer.compare(this.priority, ((PriorityFuture) other).priority);
    }
}
