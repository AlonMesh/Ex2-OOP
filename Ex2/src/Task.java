import java.util.concurrent.Callable;

public class Task<T> implements Callable<T>, Comparable<T>  {
    TaskType taskType; // Each taskType has an integer value (=priority).
    final Callable<T> callable;

    // Start constructors.
    public Task(Callable<T> callable) {
        this.taskType = TaskType.OTHER; //^^^
        this.callable = callable;
    }

    public Task(Callable<T> callable, TaskType taskType) {
        this.taskType = taskType;
        this.callable = callable;
    }

    public Task(TaskType taskType, Callable<T> callable) {
        this.taskType = taskType;
        this.callable = callable;
    }
    // End constructors.

    public static Task createTask(Callable callable, TaskType taskType) {
        return new Task(callable, taskType);
    }

    public static Task createTask(Task task) {
        return new Task(task.callable, task.taskType);
    }

    @Override
    public T call() throws Exception {
       return callable.call();
    }

    public int compareTo(T other) {
        // The function "compareTo" must include T other.
        // T doesn't contain the field taskType, so we must convert it to type Task.
        Task<T> otherTask = (Task) other;

        // Now comparing is possible
        if (this.taskType.getPriorityValue() < otherTask.taskType.getPriorityValue()) {
            return -1;
        }

        else if (this.taskType.getPriorityValue() > otherTask.taskType.getPriorityValue()) {
            return 1;
        }
        return 0;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Callable<T> getCallable() {
        return callable;
    }
}


