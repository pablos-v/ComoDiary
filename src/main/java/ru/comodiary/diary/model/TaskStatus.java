package ru.comodiary.diary.model;

/**
 * Статусы задач
 *
 * @COMPLETED - Выполнена
 * @NOT_COMPLETED - Не выполнена
 * @EXPIRED - Просрочена
 */
public enum TaskStatus {

    COMPLETED {
        @Override
        public String toString() {
            return "DONE";
        }
    },
    NOT_COMPLETED {
        @Override
        public String toString() {
            return "SET";
        }
    },
    EXPIRED {
        @Override
        public String toString() {
            return "EXPIRED";
        }
    }
}
