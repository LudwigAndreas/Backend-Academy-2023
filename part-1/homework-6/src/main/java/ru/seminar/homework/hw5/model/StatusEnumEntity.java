package ru.seminar.homework.hw5.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEnumEntity {
    NEW("NEW") {
        @Override
        public StatusEnumEntity nextStatus() {
            return StatusEnumEntity.WAITING;
        }

        @Override
        public StatusEnumEntity prevStatus() {
            return StatusEnumEntity.NEW;
        }
    },

    WAITING("WAITING") {
        @Override
        public StatusEnumEntity nextStatus() {
            return StatusEnumEntity.PROCESSED;
        }

        @Override
        public StatusEnumEntity prevStatus() {
            return StatusEnumEntity.NEW;
        }
    },

    PROCESSED("PROCESSED") {
        @Override
        public StatusEnumEntity nextStatus() {
            return StatusEnumEntity.CLOSE;
        }

        @Override
        public StatusEnumEntity prevStatus() {
            return StatusEnumEntity.WAITING;
        }
    },

    CLOSE("CLOSE") {
        @Override
        public StatusEnumEntity nextStatus() {
            return StatusEnumEntity.CLOSE;
        }

        @Override
        public StatusEnumEntity prevStatus() {
            return StatusEnumEntity.PROCESSED;
        }
    },

    CANCEL("CANCEL") {
        @Override
        public StatusEnumEntity nextStatus() {
            return StatusEnumEntity.CANCEL;
        }

        @Override
        public StatusEnumEntity prevStatus() {
            return StatusEnumEntity.CANCEL;
        }
    };

    private String status;

    StatusEnumEntity(String value) {
        this.status = value;
    }

    public abstract StatusEnumEntity nextStatus();

    public abstract StatusEnumEntity prevStatus();

    @JsonValue
    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.valueOf(status);
    }

    @JsonCreator
    public static StatusEnumEntity fromValue(String value) {
        for (StatusEnumEntity b : StatusEnumEntity.values()) {
            if (b.status.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
