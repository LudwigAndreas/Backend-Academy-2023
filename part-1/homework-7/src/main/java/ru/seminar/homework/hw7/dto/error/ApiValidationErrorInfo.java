package ru.seminar.homework.hw7.dto.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ApiValidationErrorInfo extends ApiSubErrorInfo {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationErrorInfo(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
