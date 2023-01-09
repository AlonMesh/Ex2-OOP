import java.util.concurrent.Callable;

public class TaskCallableAdapter<T> implements Callable<T> {
    private final Task<T> task;

    public TaskCallableAdapter(Task<T> task) {
        this.task = task;
    }

    @Override
    public T call() throws Exception {
        return task.call();
    }

    public Task<T> getTask() {
        return task;
    }
}
