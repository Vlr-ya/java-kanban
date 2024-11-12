import java.util.Objects;

/**
 * Задачи
 */
public class Task {

    private String name;
    private String description;
    private TaskStatus status;
    private final int id;

    public Task (String name, String description, int id) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Empty task name");
        } else if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Empty task description");
        } else if (id < 1) {
            throw new IllegalArgumentException("Incorrect task id: " + id);
        }

        this.name = name;
        this.description = description;
        this.id = id;
        this.status = TaskStatus.NEW;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    protected void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return getClass().getName() + "{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                '}';
    }
}