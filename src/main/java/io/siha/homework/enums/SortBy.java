package io.siha.homework.enums;

public enum SortBy {
    ACCURACY("accuracy"),
    RECENCY("recency"),
    ;

    private final String value;

    SortBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
