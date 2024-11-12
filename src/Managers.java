/**
 * Утилитарный класс по управлению менеджерами задач и историей просмотра задач
 */
class Managers {

    /**
     * Получение объекта "Менеджер задач"
     * @return объект типа "Менеджер задач"
     */
    static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    /**
     * Получение объекта "Менеджер истории просмотра задач"
     * @return объект типа "Менеджер истории просмотра задач"
     */
    static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
