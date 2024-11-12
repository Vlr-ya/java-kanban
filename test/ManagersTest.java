import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ManagersTest {

    private final TaskManager TASK_MANAGER = Managers.getDefault();
    private final HistoryManager HISTORY_MANAGER = Managers.getDefaultHistory();

    /**
     * Проверка Managers.getDefault
     * Добавление и получение задач
     */
    @Test
    void whenCreateAndGetTasksThenSuccess() {

        // Подготовка
        int expectedSize = 2;
        String expectedDescription = "description2";

        // Исполнение
        TASK_MANAGER.createTask("name1", "description1");
        int taskId = TASK_MANAGER.createTask("name2", "description2");
        int actualSize = TASK_MANAGER.getAllTasks().size();
        String actualDescription = TASK_MANAGER.getTaskById(taskId).getDescription();

        // Проверка
        Assertions.assertEquals(expectedSize, actualSize);
        Assertions.assertEquals(expectedDescription, actualDescription);
    }

    /**
     * Проверка Managers.getDefaultHistory
     * Добавление в историю и получение истории просмотреных задач
     */
    @Test
    void whenAddAndGetHistoryThenSuccess() {

        // Подготовка
        Task task1 = new Task("name1", "description1", 1);
        Task task2 = new Task("name2", "description2", 2);
        int expectedSize = 2;
        String expectedDescription = "description1";

        // Исполнение
        HISTORY_MANAGER.add(task1);
        HISTORY_MANAGER.add(task2);
        int actualSize = HISTORY_MANAGER.getHistory().size();
        String actualDescription = HISTORY_MANAGER.getHistory().get(0).getDescription();

        // Проверка
        Assertions.assertEquals(expectedSize, actualSize);
        Assertions.assertEquals(expectedDescription, actualDescription);
    }
}