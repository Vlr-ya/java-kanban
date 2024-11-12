import java.util.*;

/**
 * Управление задачами
 */
class InMemoryTaskManager implements TaskManager {

    private final Map<Integer, Task> taskMap = new HashMap<>();
    private final Map<Integer, Subtask> subtaskMap = new HashMap<>();
    private final Map<Integer, Epic> epicMap = new HashMap<>();

    private int counter = 0; // общий счетчик для всех типов задач, используется как идентификатор

    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public int createTask(String name, String description) {

        Task task = new Task(name, description, getNewCounter());
        updateTask(task);

        return task.getId();
    }

    @Override
    public void updateTask(Task task) {
        update(task, taskMap);
    }

    @Override
    public Task getTaskById(int id) {
        return getById(taskMap, id);
    }

    @Override
    public Task getTaskByIndex(int index) {
        return getByIndex(taskMap, index);
    }

    @Override
    public Collection<Task> getAllTasks() {
        return taskMap.values();
    }

    @Override
    public Task removeTaskById(int id) {
        return taskMap.remove(id);
    }

    @Override
    public void removeAllTasks() {
        taskMap.clear();
    }

    @Override
    public int getTasksCount() {
        return taskMap.size();
    }

    @Override
    public int createSubtask(String name, String description, Epic epic) {

        Subtask subtask = new Subtask(name, description, getNewCounter(), epic);
        updateSubtask(subtask);

        return subtask.getId();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        update(subtask, subtaskMap);
        updateEpicStatus(subtask);
    }

    @Override
    public Subtask getSubtaskById(int id) {
        return getById(subtaskMap, id);
    }

    @Override
    public Subtask getSubtaskByIndex(int index) {
        return getByIndex(subtaskMap, index);
    }

    @Override
    public Collection<Subtask> getAllSubtasks() {
        return subtaskMap.values();
    }

    @Override
    public List<Subtask> getSubtasksByEpicId(int epicId) {
        return getAllSubtasks().stream()
                .filter(s -> s.getEpic().getId() == epicId)
                .toList();
    }

    @Override
    public Subtask removeSubtaskById(int id) {
        Subtask subtask = subtaskMap.remove(id);
        if (subtask != null) {
            updateEpicStatus(subtask);
        }
        return subtask;
    }

    @Override
    public void removeAllSubtasks() {
        subtaskMap.clear();
        for (Epic epic : getAllEpics()) {
            if (epic.getStatus() != TaskStatus.NEW) {
                epic.setStatus(TaskStatus.NEW);
                update(epic, epicMap);
            }
        }
    }

    @Override
    public int getSubtasksCount() {
        return subtaskMap.size();
    }

    @Override
    public Epic createEpic(String name, String description) {

        Epic epic = new Epic(name, description, getNewCounter());
        update(epic, epicMap);

        return epic;
    }

    @Override
    public Epic getEpicById(int id) {
        return getById(epicMap, id);
    }

    @Override
    public Epic getEpicByIndex(int index) {
        return getByIndex(epicMap, index);
    }

    @Override
    public Collection<Epic> getAllEpics() {
        return epicMap.values();
    }

    @Override
    public Epic removeEpicById(int id) {

        if (getSubtasksByEpicId(id).isEmpty()) {
            return epicMap.remove(id);
        } else {
            return null;
        }
    }

    /**
     * Правильное использование удаления по условию
     */
    @Override
    public void removeAllEpics() {
        epicMap.entrySet().removeIf((entry) -> getSubtasksByEpicId(entry.getValue().getId()).isEmpty());
    }

    /**
     * Неправильное использование удаления по условию
     * Получаем исключение ConcurrentModificationException
     */
/*
    public void badRemoveAllEpics() {
        for (Epic epic : getAllEpics()) {
            if (getSubtasksByEpicId(epic.getId()).isEmpty()) {
                epicMap.remove(epic.getId());
            }
        }
    }
*/

    @Override
    public int getEpicsCount() {
        return epicMap.size();
    }

    @Override
    public int getCounter() {
        return counter;
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
        } else {
            List<Subtask> subtasks = getSubtasksByEpicId(id);
            if (subtasks.isEmpty()) {
                epicStatus = TaskStatus.NEW;
            } else {
                boolean statusNew = false;
                boolean statusInProgress = false;
                boolean statusDone = false;
                for (Subtask subtask : subtasks) {
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
     * Получение нового значения идентификатора (общий для любого типа задачи)
     * @return идентификатор задачи
     */
    private int getNewCounter() {
        return ++counter;
    }

    /**
     * Обновление задачи/подзадачи/эпика
     * @param task объект Task или наследник
     * @param map хранилище объекта
     * @param <V> тип объекта Task или наследника
     */
    private  <V extends Task> void update(V task, Map<Integer, V> map) {
        map.put(task.getId(), task);
    }

    /**
     * Получение задачи/подзадачи/эпика по идентификатору
     * @param map хранилище объекта
     * @param id идентификатор
     * @param <V> тип объекта Task или наследника
     * @return объект
     */
    private <V extends Task> V getById(Map<Integer, V> map, int id) {

        V task = map.get(id);
        addHistory(task);

        return task;
    }

    /**
     * Получение задачи/подзадачи/эпика по порядковому номеру
     * @param map хранилище объекта
     * @param index порядковый номер
     * @param <V> тип объекта Task или наследника
     * @return объект
     */
    private <V extends Task> V getByIndex(Map<Integer, V> map, int index) {

        if (index < 1) {
            return null;
        }

        V task = map.values().stream()
                .sorted(Comparator.comparingInt(Task::getId))
                .skip(index - 1)
                .findFirst()
                .orElse(null);
        addHistory(task);

        return task;
    }

    /**
     * Добавление задачи в список просмотренных
     * @param task объект задачи
     */
    private void addHistory(Task task) {
        historyManager.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }
}