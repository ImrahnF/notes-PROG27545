package sheridan.omrahn.todolistapplication.domain;

public class Task {

    private String description;
    private boolean completed;

    public Task(String description) {
        this.description = description;
        this.completed = false;
    }

    // Getters and setters
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    // Mark as complete/incomplete
    public void markAsCompleted() {
        this.completed = true;
    }

    public void unmarkAsCompleted() {
        this.completed = false;
    }

}
