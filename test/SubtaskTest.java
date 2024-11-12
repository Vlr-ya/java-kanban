import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubtaskTest {

    /**
     * Проверка успешного создания подзадачи
     * (подзадача создана с корректным идентификатором)
     */
    @Test
    void whenCreateSubtaskWithAllParamsThenSuccess() {

        // Подготовка
        Epic epic = new Epic("epic", "description", 1);
        String expectedName = "name";
        String expectedDescription = "description";
        TaskStatus expectedStatus = TaskStatus.NEW;
        int expectedId = 2;

        // Исполнение
        Subtask subtask = new Subtask("name", "description", 2, epic);

        // Проверка
        Assertions.assertEquals(expectedName, subtask.getName());
        Assertions.assertEquals(expectedDescription, subtask.getDescription());
        Assertions.assertEquals(expectedStatus, subtask.getStatus());
        Assertions.assertEquals(expectedId, subtask.getId());
        Assertions.assertEquals(epic, subtask.getEpic());
    }

    /**
     * Проверка сравнения созданных подзадач
     */
    @Test
    void whenCompareSubtasksThenSuccess() {

        // Подготовка
        Epic epic = new Epic("epic", "description", 1);
        Subtask subtask1 = new Subtask("name1", "description1", 2, epic);
        Subtask subtask2 = new Subtask("name2", "description2", 2, epic);
        Subtask subtask3 = new Subtask("name3", "description3", 3, epic);

        // Исполнение
        boolean equalsFrom1 = subtask1.equals(subtask2);
        boolean equalsFrom2 = subtask2.equals(subtask1);
        boolean equalsFrom3 = subtask3.equals(subtask2);

        // Проверка
        Assertions.assertTrue(equalsFrom1);
        Assertions.assertTrue(equalsFrom2);
        Assertions.assertFalse(equalsFrom3);
    }

    /**
     * Проверка вывода содержания задачи
     */
    @Test
    void whenGetSubtaskToStringThenSuccess() {

        // Подготовка
        Epic epic = new Epic("epic", "description", 1);
        Subtask subtask = new Subtask("name1", "description1", 2, epic);
        String expectedToString = "Subtask{name='name1', description='description1', status='NEW', id=2, epicId=1}";

        // Исполнение
        String actualToString = subtask.toString();

        // Проверка
        Assertions.assertEquals(expectedToString, actualToString);
    }
}