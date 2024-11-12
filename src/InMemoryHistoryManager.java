import java.util.ArrayList;
import java.util.List;

/**
 * Управление историей просмотра задача
 */
class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> taskHistoryList = new ArrayList<>(); // список с историей просмотра задач
    private static final int TASK_HISTORY_MAX_SIZE = 10; // макс размер списка

    @Override
    public void add(Task task) {
        if (task != null) {
            if (taskHistoryList.size() == TASK_HISTORY_MAX_SIZE) {
                taskHistoryList.remove(0);
            }
            taskHistoryList.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return taskHistoryList;
    }
}
