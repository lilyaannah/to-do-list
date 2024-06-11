package kg.ab.todolist.commons.enums;

import jakarta.persistence.JoinColumn;

public enum StatusOfTask {
    @JoinColumn(name = "Завершено")
    COMPLETED,

    @JoinColumn(name = "Не завершено")
    NOT_COMPLETED;

}
