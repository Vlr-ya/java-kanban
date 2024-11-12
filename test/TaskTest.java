import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TaskTest {

    /**
     * Проверка успешного создания задачи
     * (задача создана с корректным идентификатором)
     */
    @Test
    void whenCreateTaskWithAllParamsThenSuccess() {

        // Подготовка
        String expectedName = "name";
        String expectedDescription = "description";
        TaskStatus expectedStatus = TaskStatus.NEW;
        int expectedId = 1;

        // Исполнение
        Task task = new Task("name", "description", 1);

        // Проверка
        Assertions.assertEquals(expectedName, task.getName());
        Assertions.assertEquals(expectedDescription, task.getDescription());
        Assertions.assertEquals(expectedStatus, task.getStatus());
        Assertions.assertEquals(expectedId, task.getId());
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
                () -> new Task(null, "description",1));


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
                () -> new Task("", "description",1));


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
                () -> new Task("name", null,1));


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
                () -> new Task("name", "",1));


        // Проверка
        Assertions.assertEquals("Empty task description", ex.getMessage());
    }

    /**
     * Проверка неуспешного создания задачи (с exception)
     * (задача создана с некорректным идентификатором)
     */
    @Test
    void whenCreateTaskWithIdLessThan1ThenException() {

        // Подготовка

        // Исполнение
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Task("name", "description",-1));


        // Проверка
        Assertions.assertEquals("Incorrect task id: -1", ex.getMessage());
    }

    /**
     * Проверка сравнения созданных задач
     */
    @Test
    void whenCompareTasksThenSuccess() {

        // Подготовка
        Task task1 = new Task("name1", "description1", 1);
        Task task2 = new Task("name2", "description2", 1);
        Task task3 = new Task("name3", "description3", 2);

        // Исполнение
        boolean equalsFrom1 = task1.equals(task2);
        boolean equalsFrom2 = task2.equals(task1);
        boolean equalsFrom3 = task3.equals(task2);

        // Проверка
        Assertions.assertTrue(equalsFrom1);
        Assertions.assertTrue(equalsFrom2);
        Assertions.assertFalse(equalsFrom3);
    }

    /**
     * Проверка вывода содержания задачи
     */
    @Test
    void whenGetTaskToStringThenSuccess() {

        // Подготовка
        Task task = new Task("name1", "description1", 1);
        String expectedToString = "Task{name='name1', description='description1', status='NEW', id=1}";

        // Исполнение
        String actualToString = task.toString();

        // Проверка
        Assertions.assertEquals(expectedToString, actualToString);
    }
}