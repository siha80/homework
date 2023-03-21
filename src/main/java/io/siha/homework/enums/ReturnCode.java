package io.siha.homework.enums;

public enum ReturnCode {
    SUCCESS("SUCCESS"),
    WRONG_PARAMETERS("Parameter was wrong."),
    UNKNOWN("ERROR"),
    ;

    private final String message;

    ReturnCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
