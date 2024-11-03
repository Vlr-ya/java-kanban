public class Main {

    private static Task task;
    private static Subtask subtask;
    private static Epic epic;
    private static final TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {

        taskManager.createTask("Task 1", "Task 1 description");
        taskManager.createTask("Task 2", "Task 2 description");

        epic = taskManager.createEpic("Epic 1", "Epic 1 description");
        taskManager.createSubtask("Subtask 1", "Subtask 1 description", epic);
        taskManager.createSubtask("Subtask 2", "Subtask 2 description", epic);

        epic = taskManager.createEpic("Epic 2", "Epic 2 description");
        taskManager.createSubtask("Subtask 3", "Subtask 3 description", epic);

        taskManager.createEpic("Epic 3", "Epic 3 description");

        printState();

        task = taskManager.getTaskById(1);
        task.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateTask(task);

        subtask = taskManager.getSubtaskById(4);
        subtask.setStatus(TaskStatus.IN_PROGRESS);
        taskManager.updateSubtask(subtask);

        printState();

        taskManager.removeTaskById(100); // отсутствует
        taskManager.removeTaskById(1);

        taskManager.removeEpicById(100); // отсутствует
        taskManager.removeEpicById(3); // есть подзадачи
        taskManager.removeEpicById(8);

        printState();
    }

    private static void printState() {

        System.out.println("\nTasks:");
        taskManager.getAllTasks().forEach(System.out::println);

        System.out.println("Epics:");
        taskManager.getAllEpics().forEach(System.out::println);

        System.out.println("Subtasks:");
        taskManager.getAllSubtasks().forEach(System.out::println);
    }
}