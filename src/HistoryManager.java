import java.util.List;

/**
 * Управление историей просмотра задач
 */
public interface HistoryManager {

    /**
     * Добавление задачи в список просмотреных задач
     * @param task объкт задачи
     */
    void add(Task task);

    /**
     ** Получение истории просмотреных задач
     * @return список просмотренных задач
     */
    List<Task> getHistory();
}
