import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {

    TaskManager taskManager = new InMemoryTaskManager();;

    /**
     * Проверка успешного создания задачи
     * (задача создана с корректным идентификатором)
     */
    @Test
    void whenCreateTaskWithAllParamsThenSuccess() {

        // Подготовка
        int expectedId = 1;
        String expectedName = "name";
        String expectedDescription = "description";

        // Исполнение
        int actualId = taskManager.createTask("name", "description");
        Task task = taskManager.getTaskById(actualId);

        // Проверка
        Assertions.assertEquals(expectedId, actualId);
        Assertions.assertEquals(expectedName, task.getName());
        Assertions.assertEquals(expectedDescription, task.getDescription());
    }

    /**
     * Проверка неуспешного создания задачи (с exception)
     * (задача создана с непереданным name)
     */
    @Test
    void whenCreateTaskWithNameIsNullThenException() {

        // Подготовка

        // Исполнение
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.createTask(null, "description"));


        // Проверка
        Assertions.assertEquals("Empty task name", ex.getMessage());
    }

    /**
     * Проверка неуспешного создания задачи (с exception)
     * (задача создана с пустым name)
     */
    @Test
    void whenCreateTaskWithNameIsEmptyThenException() {

        // Подготовка

        // Исполнение
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.createTask("", "description"));


        // Проверка
        Assertions.assertEquals("Empty task name", ex.getMessage());
    }

    /**
     * Проверка неуспешного создания задачи (с exception)
     * (задача создана с непереданным description)
     */
    @Test
    void whenCreateTaskWithDescriptionIsNullThenException() {

        // Подготовка

        // Исполнение
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.createTask("name", null));


        // Проверка
        Assertions.assertEquals("Empty task description", ex.getMessage());
    }

    /**
     * Проверка неуспешного создания задачи (с exception)
     * (задача создана с незаполненным description)
     */
    @Test
    void whenCreateTaskWithDescriptionIsEmptyThenException() {

        // Подготовка

        // Исполнение
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.createTask("name", ""));


        // Проверка
        Assertions.assertEquals("Empty task description", ex.getMessage());
    }

    /**
     * Проверка обновления задачи
     */
    @Test
    void whenUpdateTaskThenSuccess() {

        // Подготовка
        Task task1 = new Task("name1", "description1", 1);
        int checkingId = 1;

        // Исполнение
        taskManager.updateTask(task1);
        Task task = taskManager.getTaskById(checkingId);

        // Проверка
        Assertions.assertEquals(task1, task);
    }

    /**
     * Проверка успешного получения задачи по идентификатору
     */
    @Test
    void whenGetTaskByPresentIdThenSuccess() {

        // Подготовка
        int expectedId = taskManager.createTask("name", "description");

        // Исполнение
        int actualId = taskManager.getTaskById(expectedId).getId();

        // Проверка
        Assertions.assertEquals(expectedId, actualId);
    }

    /**
     * Проверка неуспешного получения задачи по идентификатору
     * (нет задач с заданным идентификатором)
     */
    @Test
    void whenGetTaskByAbsentIdThenReturnNull() {

        // Подготовка
        int absentId = 100;

        // Исполнение
        Task task = taskManager.getTaskById(absentId);

        // Проверка
        Assertions.assertNull(task);
    }

    /**
     * Проверка успешного получения задачи по индексу
     */
    @Test
    void whenGetTaskByPresentIndexThenSuccess() {

        // Подготовка
        taskManager.createTask("name1", "description1");
        taskManager.createTask("name2", "description2");
        int checkedIndex = 2;
        String expectedName = "name2";

        // Исполнение
        String actualName = taskManager.getTaskByIndex(checkedIndex).getName();

        // Проверка
        Assertions.assertEquals(expectedName, actualName);
    }

    /**
     * Проверка неуспешного получения задачи по индексу
     * (нет задачи с заданным индексом)
     */
    @Test
    void whenGetTaskByAbsentIndexThenReturnNull() {

        // Подготовка
        int absentIndex = 100;

        // Исполнение
        Task task = taskManager.getTaskByIndex(absentIndex);

        // Проверка
        Assertions.assertNull(task);
    }

    /**
     * Проверка неуспешного получения задачи по индексу
     * (индекс < 1)
     */
    @Test
    void whenGetTaskByIndexLessThan1ThenReturnNull() {

        // Подготовка
        int indexLessThan1 = -1;

        // Исполнение
        Task task = taskManager.getTaskByIndex(indexLessThan1);

        // Проверка
        Assertions.assertNull(task);
    }

    /**
     * Проверка получения всех задач
     */
    @Test
    void whenGetAllTasksThenSuccess() {

        // Подготовка
        taskManager.createTask("name1", "description1");
        taskManager.createTask("name2", "description2");
        taskManager.createTask("name3", "description3");
        int expectedCount = 3;

        // Исполнение
        int actualCount = taskManager.getAllTasks().size();

        // Проверка
        Assertions.assertEquals(expectedCount, actualCount);
    }

    /**
     * Проверка успешного удаления задачи по идентификатору
     */
    @Test
    void whenRemoveTaskByIdThenSuccess() {

        // Подготовка
        taskManager.createTask("name1", "description1");
        int toRemoveId = taskManager.createTask("name2", "description2");
        taskManager.createTask("name3", "description3");
        int expectedRemainCount = 2;

        // Исполнение
        taskManager.removeTaskById(toRemoveId);
        Task task = taskManager.getTaskById(toRemoveId);
        int actualRemainCount = taskManager.getTasksCount();

        // Проверка
        Assertions.assertNull(task);
        Assertions.assertEquals(expectedRemainCount, actualRemainCount);
    }

    /**
     * Проверка удаления всех задач
     */
    @Test
    void whenRemoveAllTasksThenSuccess() {

        // Подготовка
        taskManager.createTask("name1", "description1");
        taskManager.createTask("name2", "description2");
        taskManager.createTask("name3", "description3");
        int expectedRemainCount = 0;

        // Исполнение
        taskManager.removeAllTasks();
        int actualRemainCount = taskManager.getTasksCount();

        // Проверка
        Assertions.assertEquals(expectedRemainCount, actualRemainCount);
    }

    /**
     * Проверка получения количества задач
     */
    @Test
    void whenGetTaskCountThenSuccess() {

        // Подготовка
        taskManager.createTask("name1", "description1");
        taskManager.createTask("name2", "description2");
        int expectedCount = 2;

        // Исполнение
        int actualCount = taskManager.getTasksCount();

        // Проверка
        Assertions.assertEquals(expectedCount, actualCount);
    }


    /**
     * Проверка успешного создания подзадачи
     * (подзадача создана с корректным эпиком')
     */
    @Test
    void whenCreateSubtaskWithEpicThenSuccess() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        int expectedId = 2;
        String expectedName = "name";
        String expectedDescription = "description";

        // Исполнение
        int actualId = taskManager.createSubtask("name", "description", epic);
        Subtask subtask = taskManager.getSubtaskById(actualId);

        // Проверка
        Assertions.assertEquals(expectedId, actualId);
        Assertions.assertEquals(expectedName, subtask.getName());
        Assertions.assertEquals(expectedDescription, subtask.getDescription());
        Assertions.assertEquals(epic, subtask.getEpic());
    }

    /**
     * Проверка неуспешного создания подзадачи (с exception)
     * (подзадача не создается без передачи эпика)
     */
    @Test
    void whenCreateSubtaskWithEpicIsNullThenException() {

        // Подготовка

        // Исполнение
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> taskManager.createSubtask("name", "description", null));


        // Проверка
        Assertions.assertEquals("Empty subtask epic", ex.getMessage());
    }

    /**
     * Проверка обновления подзадачи
     */
    @Test
    void whenUpdateSubtaskThenSuccess() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        int subtaskId = taskManager.createSubtask("name", "description", epic);
        Subtask subtask1 = taskManager.getSubtaskById(subtaskId);
        TaskStatus expectedStatus = TaskStatus.IN_PROGRESS;
        subtask1.setStatus(expectedStatus);

        // Исполнение
        taskManager.updateSubtask(subtask1);
        Task subtask = taskManager.getSubtaskById(subtaskId);

        // Проверка
        Assertions.assertEquals(subtask1, subtask);
        Assertions.assertEquals(expectedStatus, subtask.getStatus());
    }

    /**
     * Проверка успешного получения подзадачи по индексу
     */
    @Test
    void whenGetSubtaskByPresentIndexThenSuccess() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        taskManager.createSubtask("name1", "description1", epic);
        taskManager.createSubtask("name2", "description2", epic);
        int checkingIndex = 2;
        String expectedName = "name2";

        // Исполнение
        String actualName = taskManager.getSubtaskByIndex(checkingIndex).getName();

        // Проверка
        Assertions.assertEquals(expectedName, actualName);
    }

    /**
     * Проверка неуспешного получения подзадачи по индексу
     * (нет подзадачи с заданным индексом)
     */
    @Test
    void whenGetSubtaskByAbsentIndexThenReturnNull() {

        // Подготовка
        int absentIndex = 100;

        // Исполнение
        Task task = taskManager.getSubtaskByIndex(absentIndex);

        // Проверка
        Assertions.assertNull(task);
    }

    /**
     * Проверка получения всех подзадач
     */
    @Test
    void whenGetAllSubtasksThenSuccess() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        taskManager.createSubtask("name1", "description1", epic);
        taskManager.createSubtask("name2", "description2", epic);
        int expectedCount = 2;

        // Исполнение
        int actualCount = taskManager.getAllSubtasks().size();

        // Проверка
        Assertions.assertEquals(expectedCount, actualCount);
    }

    /**
     * Проверка успешного удаления подзадачи по идентификатору
     */
    @Test
    void whenRemoveSubtaskByIdThenSuccess() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        taskManager.createSubtask("name1", "description1", epic);
        int subtaskId = taskManager.createSubtask("name2", "description2", epic);
        taskManager.createSubtask("name3", "description3", epic);
        int expectedRemainCount = 2;

        // Исполнение
        taskManager.removeSubtaskById(subtaskId);
        Subtask subtask = taskManager.getSubtaskById(subtaskId);
        int actualRemainCount = taskManager.getSubtasksCount();

        // Проверка
        Assertions.assertNull(subtask);
        Assertions.assertEquals(expectedRemainCount, actualRemainCount);
    }

    /**
     * Проверка удаления подзадачи по несуществующему идентификатору
     */
    @Test
    void whenRemoveAbsentSubtaskByIdThenDoNotRemove() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        taskManager.createSubtask("name1", "description1", epic);
        taskManager.createSubtask("name2", "description2", epic);
        taskManager.createSubtask("name3", "description3", epic);
        int absentId = 100;
        int expectedRemainCount = 3;

        // Исполнение
        taskManager.removeSubtaskById(absentId);
        Subtask subtask = taskManager.getSubtaskById(absentId);
        int actualRemainCount = taskManager.getSubtasksCount();

        // Проверка
        Assertions.assertNull(subtask);
        Assertions.assertEquals(expectedRemainCount, actualRemainCount);
    }

    /**
     * Проверка удаления всех подзадач
     * (все подзадачи и эпик в статусе NEW)
     */
    @Test
    void whenRemoveAllSubtasksWithOnlyNewStatusThenSuccess() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        taskManager.createSubtask("name1", "description1", epic);
        taskManager.createSubtask("name2", "description2", epic);
        taskManager.createSubtask("name3", "description3", epic);
        int expectedRemainCount = 0;
        TaskStatus expectedEpicStatus = TaskStatus.NEW;

        // Исполнение
        taskManager.removeAllSubtasks();
        int actualRemainCount = taskManager.getSubtasksCount();
        TaskStatus actualEpicStatus = taskManager.getEpicById(epic.getId()).getStatus();

        // Проверка
        Assertions.assertEquals(expectedRemainCount, actualRemainCount);
        Assertions.assertEquals(expectedEpicStatus, actualEpicStatus);
    }

    /**
     * Проверка удаления всех подзадач
     * (есть подзадача в статусе IN_PROGRESS)
     */
    @Test
    void whenRemoveAllSubtasksWithSomeSubtaskInProgressStatusThenSuccess() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        int id = taskManager.createSubtask("name1", "description1", epic);
        Subtask subtask = taskManager.getSubtaskById(id);
        subtask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtask);
        taskManager.createSubtask("name2", "description2", epic);
        taskManager.createSubtask("name3", "description3", epic);
        int expectedRemainCount = 0;
        TaskStatus expectedEpicStatus = TaskStatus.NEW;

        // Исполнение
        taskManager.removeAllSubtasks();
        int actualRemainCount = taskManager.getSubtasksCount();
        TaskStatus actualEpicStatus = taskManager.getEpicById(epic.getId()).getStatus();

        // Проверка
        Assertions.assertEquals(expectedRemainCount, actualRemainCount);
        Assertions.assertEquals(expectedEpicStatus, actualEpicStatus);
    }

    /**
     * Проверка получения количества подзадач
     */
    @Test
    void whenGetSubtasksCountThenSuccess() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        taskManager.createSubtask("name1", "description1", epic);
        taskManager.createSubtask("name2", "description2", epic);
        int expectedCount = 2;

        // Исполнение
        int actualCount = taskManager.getSubtasksCount();

        // Проверка
        Assertions.assertEquals(expectedCount, actualCount);
    }


    /**
     * Проверка успешного создания эпика
     * (эпик создан с корректным идентификатором)
     */
    @Test
    void whenCreateEpicThenSuccess() {

        // Подготовка
        int expectedId = 1;

        // Исполнение
        int actualId = taskManager.createEpic("epic 1", "epic 1 description").getId();

        // Проверка
        Assertions.assertEquals(expectedId, actualId);
    }

    /**
     * Проверка успешного получения эпика по индексу
     */
    @Test
    void whenGetEpicByPresentIndexThenSuccess() {

        // Подготовка
        taskManager.createEpic("epic 1", "epic 1 description");
        taskManager.createEpic("epic 2", "epic 2 description");
        int checkingIndex = 2;
        String expectedName = "epic 2";

        // Исполнение
        String actualName = taskManager.getEpicByIndex(checkingIndex).getName();

        // Проверка
        Assertions.assertEquals(expectedName, actualName);
    }

    /**
     * Проверка неуспешного получения эпика по индексу
     * (нет эпика с переданным индексом)
     */
    @Test
    void whenGetEpicByAbsentIndexThenReturnNull() {

        // Подготовка
        int absentIndex = 100;

        // Исполнение
        Task epic = taskManager.getEpicByIndex(absentIndex);

        // Проверка
        Assertions.assertNull(epic);
    }

    /**
     * Проверка удаления эпика без подзадач по идентификатору
     */
    @Test
    void whenRemoveEpicByIdWithoutSubtasksThenSuccess() {

        // Подготовка
        taskManager.createEpic("epic 1", "epic 1 description");
        taskManager.createEpic("epic 2", "epic 2 description");
        taskManager.createEpic("epic 3", "epic 3 description");
        int removingId = 2;
        int expectedCount = 2;

        // Исполнение
        taskManager.removeEpicById(removingId);
        Task epic = taskManager.getEpicById(removingId);
        int actualCount = taskManager.getEpicsCount();

        // Проверка
        Assertions.assertNull(epic);
        Assertions.assertEquals(expectedCount, actualCount);
    }

    /**
     * Проверка удаления эпика с подзадачами по идентификатору
     * (неуспешно)
     */
    @Test
    void whenRemoveEpicByIdWithSubtasksThenDoNotRemove() {

        // Подготовка
        taskManager.createEpic("epic 1", "epic 1 description");
        Epic epic = taskManager.createEpic("epic 2", "epic 2 description");
        int toRemoveEpicId = epic.getId();
        taskManager.createSubtask("name", "description", epic);
        taskManager.createEpic("epic 3", "epic 3 description");
        int expectedCount = 3;

        // Исполнение
        taskManager.removeEpicById(toRemoveEpicId);
        Task toRemoveEpic = taskManager.getEpicById(toRemoveEpicId);
        int actualCount = taskManager.getEpicsCount();

        // Проверка
        Assertions.assertNotNull(toRemoveEpic);
        Assertions.assertEquals(expectedCount, actualCount);
    }

    /**
     * Проверка удаления всех эпиков (без подзадач)
     */
    @Test
    void whenRemoveAllEpicsWithoutSubtasksThenSuccess() {

        // Подготовка
        taskManager.createEpic("epic 1", "epic 1 description");
        taskManager.createEpic("epic 2", "epic 2 description");
        taskManager.createEpic("epic 3", "epic 3 description");
        int expectedRemainCount = 0;

        // Исполнение
        taskManager.removeAllEpics();
        int actualRemainCount = taskManager.getEpicsCount();

        // Проверка
        Assertions.assertEquals(expectedRemainCount, actualRemainCount);
    }

    /**
     * Проверка удаления всех эпиков (есть подзадачи)
     */
    @Test
    void whenRemoveAllEpicsWithSomeSubtasksThenSuccess() {

        // Подготовка
        taskManager.createEpic("epic 1", "epic 1 description");
        Epic epic = taskManager.createEpic("epic 2", "epic 2 description");
        taskManager.createSubtask("name", "description", epic);
        taskManager.createEpic("epic 3", "epic 3 description");
        int expectedRemainCount = 1;

        // Исполнение
        taskManager.removeAllEpics();
        int actualRemainCount = taskManager.getEpicsCount();

        // Проверка
        Assertions.assertEquals(expectedRemainCount, actualRemainCount);
    }

    /**
     * Проверка получения количества эпиков
     */
    @Test
    void whenGetEpicsCountThenSuccess() {

        // Подготовка
        taskManager.createEpic("epic 1", "epic 1 description");
        taskManager.createEpic("epic 2", "epic 2 description");
        int expectedCount = 2;

        // Исполнение
        int actualCount = taskManager.getEpicsCount();

        // Проверка
        Assertions.assertEquals(expectedCount, actualCount);
    }

    /**
     * Проверка получения статуса эпика IN_PROGRESS после установки статуса подзадачи IN_PROGRESS
     */
    @Test
    void whenGetEpicStatusAfterSetSubtaskToInProgressThenSuccess() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        int id = taskManager.createSubtask("name1", "description1", epic);
        Subtask subtask = taskManager.getSubtaskById(id);
        subtask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtask);
        TaskStatus expectedEpicStatus = TaskStatus.IN_PROGRESS;

        // Исполнение
        TaskStatus actualEpicStatus = taskManager.getEpicById(epic.getId()).getStatus();

        // Проверка
        Assertions.assertEquals(expectedEpicStatus, actualEpicStatus);
    }

    /**
     * Проверка получения статуса эпика DONE после установки статуса подзадачи DONE
     */
    @Test
    void whenGetEpicStatusAfterSetSubtaskToDoneThenSuccess() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        int id = taskManager.createSubtask("name1", "description1", epic);
        Subtask subtask = taskManager.getSubtaskById(id);
        subtask.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask);
        TaskStatus expectedEpicStatus = TaskStatus.DONE;

        // Исполнение
        TaskStatus actualEpicStatus = taskManager.getEpicById(epic.getId()).getStatus();

        // Проверка
        Assertions.assertEquals(expectedEpicStatus, actualEpicStatus);
    }

    /**
     * Проверка получения статуса эпика NEW после удаления подзадачи DONE
     */
    @Test
    void whenGetEpicStatusAfterRemoveDoneSubtaskThenSuccess() {

        // Подготовка
        Epic epic = taskManager.createEpic("epic 1", "epic 1 description");
        int id = taskManager.createSubtask("name1", "description1", epic);
        Subtask subtask = taskManager.getSubtaskById(id);
        subtask.setStatus(TaskStatus.DONE);
        taskManager.updateSubtask(subtask);
        TaskStatus expectedEpicStatus = TaskStatus.NEW;

        // Исполнение
        taskManager.removeSubtaskById(id);
        TaskStatus actualEpicStatus = taskManager.getEpicById(epic.getId()).getStatus();

        // Проверка
        Assertions.assertEquals(expectedEpicStatus, actualEpicStatus);
    }

    /**
     * Проверка получения текущего (максимального) значения идентификатора счетчика (общий для любого типа задачи)
     */
    @Test
    void whenGetCounterThenSuccess() {

        // Подготовка
        taskManager.createTask("name1", "description1");
        taskManager.createTask("name2", "description2");
        taskManager.createEpic("epic 1", "epic 1 description");
        int expected = 3;

        // Исполнение
        int actual = taskManager.getCounter();

        // Проверка
        Assertions.assertEquals(expected, actual);
    }

    /**
     ** Проверка получения истории просмотреных задач
     */
    @Test
    void whenGetHistoryThenSuccess() {

        // Подготовка
        taskManager.createTask("name1", "description1");
        taskManager.createTask("name2", "description2");
        taskManager.createEpic("epic1", "epic description1");
        taskManager.getTaskById(1);
        taskManager.getTaskById(2);
        String expectedDescription = "description2";

        // Исполнение
        String actualDescription = taskManager.getHistory().get(1).getDescription();

        // Проверка
        Assertions.assertEquals(expectedDescription, actualDescription);
    }
}