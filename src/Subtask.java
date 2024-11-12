/**
 * Подзадачи
 */
public class Subtask extends Task {

    private Epic epic;

    public Subtask(String name, String description, int id, Epic epic) {
        super(name, description, id);
        if (epic == null) {
            throw new IllegalArgumentException("Empty subtask epic");
        }
        this.epic = epic;
    }

    public Epic getEpic() {
        return epic;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + getName() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", id=" + getId() +
                ", epicId=" + getEpic().getId() +
                '}';
    }
}