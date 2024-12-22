package uib.swarchitecture.quepasa.infrastructure.web.controllers.utils;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private final T data;
    private final String errorMessage;

    public ApiResponse(T data) {
        this.data = data;
        this.errorMessage = null;
    }

    public ApiResponse(String errorMessage) {
        this.data = null;
        this.errorMessage = errorMessage;
    }
}