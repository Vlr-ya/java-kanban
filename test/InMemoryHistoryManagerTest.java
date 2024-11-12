import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InMemoryHistoryManagerTest {

    HistoryManager historyManager = new InMemoryHistoryManager();

    /**
     * Проверка добавления в историю и получения истории просмотреных задач
     */
    @Test
    void whenAddAndGetHistoryThenSuccess() {

        // Подготовка
        Task task1 = new Task("name1", "description1", 1);
        Task task2 = new Task("name2", "description2", 2);
        int expectedSize = 2;
        String expectedDescription = "description1";

        // Исполнение
        historyManager.add(task1);
        historyManager.add(task2);
        int actualSize = historyManager.getHistory().size();
        String actualDescription = historyManager.getHistory().get(0).getDescription();

        // Проверка
        Assertions.assertEquals(expectedSize, actualSize);
        Assertions.assertEquals(expectedDescription, actualDescription);
    }

    /**
     * Проверка добавления в историю более 10 задач и получения истории просмотреных задач
     */
    @Test
    void whenAddGreaterThan10AndGetHistoryThenSuccess() {

        // Подготовка
        Task task1 = new Task("name1", "description1", 1);
        Task task2 = new Task("name2", "description2", 2);
        Task task3 = new Task("name3", "description3", 3);
        int expectedSize = 10;
        String expectedDescriptionFirst = "description2";
        String expectedDescriptionLast = "description3";

        // Исполнение
        historyManager.add(task1); // 1
        historyManager.add(task2); // 2
        historyManager.add(task1); // 3
        historyManager.add(task2); // 4
        historyManager.add(task1); // 5
        historyManager.add(task2); // 6
        historyManager.add(task1); // 7
        historyManager.add(task2); // 8
        historyManager.add(task1); // 9
        historyManager.add(task2); // 10
        historyManager.add(task3); // 11
        int actualSize = historyManager.getHistory().size();
        String actualDescriptionFirst = historyManager.getHistory().get(0).getDescription();
        String actualDescriptionLast = historyManager.getHistory().get(9).getDescription();

        // Проверка
        Assertions.assertEquals(expectedSize, actualSize);
        Assertions.assertEquals(expectedDescriptionFirst, actualDescriptionFirst);
        Assertions.assertEquals(expectedDescriptionLast, actualDescriptionLast);
    }

    /**
     * Проверка добавления в историю null объекта и получения пустой истории просмотреных задач
     */
    @Test
    void whenAddNullAndGetHistoryThenReturnEmpty() {

        // Подготовка

        // Исполнение
        historyManager.add(null);
        boolean isEmpty = historyManager.getHistory().isEmpty();

        // Проверка
        Assertions.assertTrue(isEmpty);
    }
}