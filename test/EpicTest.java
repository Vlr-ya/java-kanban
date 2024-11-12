import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EpicTest {

    /**
     * Проверка успешного создания эпика
     * (эпик создан с корректным идентификатором)
     */
    @Test
    void whenCreateEpicWithAllParamsThenSuccess() {

        // Подготовка
        String expectedName = "epic";
        String expectedDescription = "description";
        TaskStatus expectedStatus = TaskStatus.NEW;
        int expectedId = 1;

        // Исполнение
        Epic epic = new Epic("epic", "description", 1);

        // Проверка
        Assertions.assertEquals(expectedName, epic.getName());
        Assertions.assertEquals(expectedDescription, epic.getDescription());
        Assertions.assertEquals(expectedStatus, epic.getStatus());
        Assertions.assertEquals(expectedId, epic.getId());
    }

    /**
     * Проверка сравнения созданных эпиков
     */
    @Test
    void whenCompareEpicsThenSuccess() {

        // Подготовка
        Epic epic1 = new Epic("epic1", "description1", 1);
        Epic epic2 = new Epic("epic2", "description2", 1);
        Epic epic3 = new Epic("epic3", "description3", 2);

        // Исполнение
        boolean equalsFrom1 = epic1.equals(epic2);
        boolean equalsFrom2 = epic2.equals(epic1);
        boolean equalsFrom3 = epic3.equals(epic2);

        // Проверка
        Assertions.assertTrue(equalsFrom1);
        Assertions.assertTrue(equalsFrom2);
        Assertions.assertFalse(equalsFrom3);
    }

    /**
     * Проверка вывода содержания эпика
     */
    @Test
    void whenGetEpicToStringThenSuccess() {

        // Подготовка
        Epic epic = new Epic("epic1", "description1", 1);
        String expectedToString = "Epic{name='epic1', description='description1', status='NEW', id=1}";

        // Исполнение
        String actualToString = epic.toString();

        // Проверка
        Assertions.assertEquals(expectedToString, actualToString);
    }
}