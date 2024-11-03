import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Управление задачами
 */
public class TaskManager {

    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    private final Map<Integer, Epic> epicMap = new HashMap<>();

    private static int counter = 0; // общий счетчик для всех типов задач, используется как идентификатор

    /**
     * Создание задачи (в статусе NEW)
     * @param name название задачи
     * @param description описание задачи
     * @return идентификатор задачи
     */
    public int createTask(String name, String description) {

        int id = getCommonTaskId();
        Task task = new Task(name, description, id);
        updateTask(task);

        return id;
    }

    /**
     * Обновление задачи
     * @param task объект задачи
     */
    public void updateTask(Task task) {
        update(task, taskMap);
    }

    /**
     * Получение списка всех задач
     * @return список всех задач
     */
    public Collection<Task> getAllTasks() {
        return taskMap.values();
    }

    /**
     * Получение задачи по идентификатору
     * @param id идентификатор задачи
     * @return объект задачи
     */
    public Task getTaskById(int id) {
        return taskMap.get(id);
    }

    /**
     * Удаление задачи по идентификатору
     * @param id идентификатор задачи
     * @return объект задачи
     */
    public Task removeTaskById(int id) {
        return taskMap.remove(id);
    }

    /**
     * Удаление всех задач
     */
    public void removeAllTasks() {
        taskMap.clear();
    }

    /**
     * Создание подзадачи (в статусе NEW)
     * @param name название подзадачи
     * @param description описание подзадачи
     * @param epic объект эпика, в который входит подзадача
     * @return идентификатор подзадачи (-1 - если не создана)
     */
    public int createSubtask(String name, String description, Epic epic) {

        if (epic == null) {
            return  -1;
        } else {
            int id = getCommonTaskId();
            Subtask subtask = new Subtask(name, description, id, epic);
            updateSubtask(subtask);

            return id;
        }
    }

    /**
     * Обновление подзадачи (с обновлением статуса эпика)
     * @param subtask объект подзадачи
     */
    public void updateSubtask(Subtask subtask) {
        update(subtask, subtaskMap);
        updateEpicStatus(subtask);
    }

    /**
     * Получение списка всех подзадач
     * @return список всех подзадач
     */
    public Collection<Subtask> getAllSubtasks() {
        return subtaskMap.values();
    }

    /**
     * Получение списка подзадач для эпика
     * @param epicId идентификатор эпика
     * @return список подзадач
     */
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        return getAllSubtasks().stream()
                .filter(s -> s.getEpic().getId() == epicId)
                .toList();
    }

    /**
     * Получение подзадачи по идентификатору
     * @param id идентификатор подзадачи
     * @return объект подзадачи
     */
    public Subtask getSubtaskById(int id) {
        return subtaskMap.get(id);
    }

    /**
     * Удаление подзадачи по идентификатору (с обновлением статуса эпика)
     * @param id идентификатор подзадачи
     * @return объект подзадачи
     */
    public Subtask removeSubtaskById(int id) {
        Subtask subtask = subtaskMap.remove(id);
        if (subtask != null) {
            updateEpicStatus(subtask);
        }
        return subtask;
    }

    /**
     * Удаление всех подзадач (с обновлением статусов эпиков)
     */
    public void removeAllSubtasks() {
        subtaskMap.clear();
        for (Epic epic : getAllEpics()) {
            if (epic.getStatus() != TaskStatus.NEW) {
                epic.setStatus(TaskStatus.NEW);
                update(epic, epicMap);
            }
        }
    }

    /**
     * Создание эпика (в статусе NEW)
     * @param name название эпика
     * @param description описание эпика
     * @return объект эпика
     */
    public Epic createEpic(String name, String description) {

        Epic epic = new Epic(name, description, getCommonTaskId());
        update(epic, epicMap);

        return epic;
    }

    /**
     * Получение списка всех эпиков
     * @return список всех эпиков
     */
    public Collection<Epic> getAllEpics() {
        return epicMap.values();
    }

    /**
     * Получение эпика по идентификатору
     * @param id идентификатор эпика
     * @return объект эпика
     */
    public Epic getEpicById(int id) {
        return epicMap.get(id);
    }

    /**
     * Удаление эпика по идентификатору
     * @param id идентификатор эпика
     * @return объект эпика (null - если эпик не найден или есть подзадачи)
     */
    public Epic removeEpicById(int id) {

        if (getSubtasksByEpicId(id).isEmpty()) {
            return epicMap.remove(id);
        } else {
            return null;
        }
    }

    /**
     * Удаление всех эпиков (только при отсутствии подзадач)
     */
    public void removeAllEpics() {
        for (Epic epic : getAllEpics()) {
            if (getSubtasksByEpicId(epic.getId()).isEmpty()) {
                epicMap.remove(epic.getId());
            }
        }
    }

    /**
     * Расчет статуса эпика на основании статусов подзадач
     * @param id идентификатор эпика
     * @return статус эпика (null - если эпик не найден)
     */
    private TaskStatus getEpicStatus(int id) {

        TaskStatus epicStatus;
        Epic epic = getEpicById(id);

        if (epic == null) {
            epicStatus = null;
        } else if (getSubtasksByEpicId(id).isEmpty()) {
            epicStatus = TaskStatus.NEW;
        } else {
            boolean statusNew = false;
            boolean statusInProgress = false;
            boolean statusDone = false;
            for (Subtask subtask : getSubtasksByEpicId(id) ) {
                switch (subtask.getStatus()) {
                    case NEW:
                        statusNew = true;
                        break;
                    case IN_PROGRESS:
                        statusInProgress = true;
                        break;
                    case DONE:
                        statusDone = true;
                        break;
                }
            }
            if (statusNew && !statusInProgress && !statusDone) {
                epicStatus = TaskStatus.NEW;
            } else if (!statusNew && !statusInProgress && statusDone) {
                epicStatus = TaskStatus.DONE;
            } else {
                epicStatus = TaskStatus.IN_PROGRESS;
            }
        }

        return epicStatus;
    }

    /**
     * Обновление статуса эпика
     * @param subtask объект новой/измененной подзадачи
     */
    private void updateEpicStatus(Subtask subtask) {

        int epicId = subtask.getEpic().getId();
        Epic epic = getEpicById(epicId);
        TaskStatus epicStatusNew = getEpicStatus(epicId);

        if (epic.getStatus() != epicStatusNew) {
            epic.setStatus(epicStatusNew);
            update(epic, epicMap);
        }
    }

    /**
     * Получение значения идентификатора (общий для любого типа задачи)
     * @return идентификатор задачи
     */
    private int getCommonTaskId() {
        return ++counter;
    }

    /**
     * Обновление задачи/подзадачи/эпика
     * @param task объект Task или наследник
     * @param map хранилище объекта
     * @return объект
     * @param <V> тип объекта Task или наследника
     */
    private  <V extends Task> V update(V task, Map<Integer, V> map) {
        return map.put(task.getId(), task);
    }
}