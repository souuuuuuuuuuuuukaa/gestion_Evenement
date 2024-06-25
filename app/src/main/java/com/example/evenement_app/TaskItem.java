package com.example.evenement_app;

public class TaskItem {
    private String taskText;
    private boolean isChecked;

    public TaskItem(String taskText, boolean isChecked) {
        this.taskText = taskText;
        this.isChecked = isChecked;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
