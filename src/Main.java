public class Main {

    private static Task task;
    private static Subtask subtask;
    private static Epic epic;

    private static final TaskManager TASK_MANAGER = Managers.getDefault();

    public static void main(String[] args) {

        int count;

        TASK_MANAGER.createTask("Task 1", "Task 1 description");
        TASK_MANAGER.createTask("Task 2", "Task 2 description");

        epic = TASK_MANAGER.createEpic("Epic 1", "Epic 1 description");
        TASK_MANAGER.createSubtask("Subtask 1", "Subtask 1 description", epic);
        TASK_MANAGER.createSubtask("Subtask 2", "Subtask 2 description", epic);

        epic = TASK_MANAGER.createEpic("Epic 2", "Epic 2 description");
        TASK_MANAGER.createSubtask("Subtask 3", "Subtask 3 description", epic);

        TASK_MANAGER.createEpic("Epic 3", "Epic 3 description");

        printState(TASK_MANAGER);

        task = TASK_MANAGER.getTaskById(1);
        task.setStatus(TaskStatus.IN_PROGRESS);
        TASK_MANAGER.updateTask(task);

        subtask = TASK_MANAGER.getSubtaskById(4);
        subtask.setStatus(TaskStatus.IN_PROGRESS);
        TASK_MANAGER.updateSubtask(subtask);

        printState(TASK_MANAGER);

        TASK_MANAGER.removeTaskById(100); // отсутствует
        TASK_MANAGER.removeTaskById(1);

        TASK_MANAGER.removeEpicById(100); // отсутствует
        TASK_MANAGER.removeEpicById(3); // есть подзадачи
        TASK_MANAGER.removeEpicById(8);

        printState(TASK_MANAGER);

        count = TASK_MANAGER.getTasksCount();
        TASK_MANAGER.getTaskByIndex(-1); // отсутствует
        TASK_MANAGER.getTaskByIndex(1);
        TASK_MANAGER.getTaskByIndex(100); // отсутствует
        TASK_MANAGER.getTaskByIndex(count);

        count = TASK_MANAGER.getSubtasksCount();
        TASK_MANAGER.getSubtaskByIndex(-1); // отсутствует
        TASK_MANAGER.getSubtaskByIndex(1);
        TASK_MANAGER.getSubtaskByIndex(100); // отсутствует
        TASK_MANAGER.getSubtaskByIndex(count);

        count = TASK_MANAGER.getEpicsCount();
        TASK_MANAGER.getEpicByIndex(-1); // отсутствует
        TASK_MANAGER.getEpicByIndex(1);
        TASK_MANAGER.getEpicByIndex(100); // отсутствует
        TASK_MANAGER.getEpicByIndex(count);

        printAllTasks(TASK_MANAGER);
    }

    private static void printState(TaskManager manager) {

        System.out.println("\nTasks:");
        manager.getAllTasks().forEach(System.out::println);

        System.out.println("Epics:");
        manager.getAllEpics().forEach(System.out::println);

        System.out.println("Subtasks:");
        manager.getAllSubtasks().forEach(System.out::println);
    }

    private static void printAllTasks(TaskManager manager) {

        System.out.println("\nTasks:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }

        System.out.println("Epics:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);
            for (Task subtask : manager.getSubtasksByEpicId(epic.getId())) {
                System.out.println("--> " + subtask);
            }
        }

        System.out.println("Subtasks:");
        for (Task subtask : manager.getAllSubtasks()) {
            System.out.println(subtask);
        }

        System.out.println("History:");
        int i = 0;
        for (Task task : manager.getHistory()) {
            System.out.println(++i + " " + task);
        }
    }
}