package io.siha.homework.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.siha.homework.enums.ReturnCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseT<T> {
    @JsonIgnore
    private ReturnCode status;
    private String returnCode;
    private String returnMessage;
    private T data;

    public static <U> ResponseT<U> of(final ReturnCode status) {
        return of(status, null);
    }

    public static <U> ResponseT<U> of(U data) {
        return of(ReturnCode.SUCCESS, data);
    }

    public static <U> ResponseT<U> of(final ReturnCode status, final U data) {
        return of(status, status.name(), null, data);
    }

    public static <U> ResponseT<U> of(final ReturnCode status, final String message, final U data) {
        return of(status, status.name(), message, data);
    }

    public static <U> ResponseT<U> of(final ReturnCode status, final String returnCode, final String returnMessage, final U data) {
        return new ResponseT<>(status, returnCode, returnMessage, data);
    }

    public boolean success() {
        return Optional.ofNullable(returnCode)
                .map(code -> code.equals(ReturnCode.SUCCESS.name()))
                .orElse(false);
    }
}
