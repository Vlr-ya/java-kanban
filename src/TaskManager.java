import java.util.Collection;
import java.util.List;

/**
 * Управление задачами
 */
public interface TaskManager {

    /**
     * Создание задачи (в статусе NEW)
     * @param name название задачи
     * @param description описание задачи
     * @return идентификатор задачи
     */
    int createTask(String name, String description);

    /**
     * Обновление задачи
     * @param task объект задачи
     */
    void updateTask(Task task);

    /**
     * Получение задачи по идентификатору
     * @param id идентификатор задачи
     * @return объект задачи
     */
    Task getTaskById(int id);

    /**
     * Получение задачи по порядковому номеру
     * @param index порядковый номер задачи
     * @return объект задачи
     */
    Task getTaskByIndex(int index);

    /**
     * Получение списка всех задач
     * @return список всех задач
     */
    Collection<Task> getAllTasks();

    /**
     * Удаление задачи по идентификатору
     * @param id идентификатор задачи
     * @return объект задачи
     */
    Task removeTaskById(int id);

    /**
     * Удаление всех задач
     */
    void removeAllTasks();

    /**
     * Получение количества задач
     * @return количество задач
     */
    int getTasksCount();

    /**
     * Создание подзадачи (в статусе NEW)
     * @param name название подзадачи
     * @param description описание подзадачи
     * @param epic объект эпика, в который входит подзадача
     * @return идентификатор подзадачи (-1 - если не создана)
     */
    int createSubtask(String name, String description, Epic epic);

    /**
     * Обновление подзадачи (с обновлением статуса эпика)
     * @param subtask объект подзадачи
     */
    void updateSubtask(Subtask subtask);

    /**
     * Получение подзадачи по идентификатору
     * @param id идентификатор подзадачи
     * @return объект подзадачи
     */
    Subtask getSubtaskById(int id);

    /**
     * Получение подзадачи по порядковому номеру
     * @param index порядковый номер подзадачи
     * @return объект подзадачи
     */
    Subtask getSubtaskByIndex(int index);

    /**
     * Получение списка всех подзадач
     * @return список всех подзадач
     */
    Collection<Subtask> getAllSubtasks();

    /**
     * Получение списка подзадач для эпика
     * @param epicId идентификатор эпика
     * @return список подзадач
     */
    List<Subtask> getSubtasksByEpicId(int epicId);

    /**
     * Удаление подзадачи по идентификатору (с обновлением статуса эпика)
     * @param id идентификатор подзадачи
     * @return объект подзадачи
     */
    Subtask removeSubtaskById(int id);

    /**
     * Удаление всех подзадач (с обновлением статусов эпиков)
     */
    void removeAllSubtasks();

    /**
     * Получение количества подзадач
     * @return количество подзадач
     */
    int getSubtasksCount();


    /**
     * Создание эпика (в статусе NEW)
     * @param name название эпика
     * @param description описание эпика
     * @return объект эпика
     */
    Epic createEpic(String name, String description);

    /**
     * Получение эпика по идентификатору
     * @param id идентификатор эпика
     * @return объект эпика
     */
    Epic getEpicById(int id);

    /**
     * Получение эпика по порядковому номеру
     * @param index порядковый номер эпика
     * @return объект эпика
     */
    Epic getEpicByIndex(int index);

    /**
     * Получение списка всех эпиков
     * @return список всех эпиков
     */
    Collection<Epic> getAllEpics();

    /**
     * Удаление эпика по идентификатору
     * @param id идентификатор эпика
     * @return объект эпика (null - если эпик не найден или есть подзадачи)
     */
    Epic removeEpicById(int id);

    /**
     * Удаление всех эпиков (только при отсутствии подзадач)
     */
    void removeAllEpics();

    /**
     * Получение количества эпиков
     * @return количество эпиков
     */
    int getEpicsCount();

    /**
     * Получение текущего (максимального) значения идентификатора (счетчика) (общий для любого типа задачи)
     * @return значение идентификатора
     */
    int getCounter();

    /**
     ** Получение истории просмотреных задач
     * @return список просмотренных задач
     */
    List<Task> getHistory();
}