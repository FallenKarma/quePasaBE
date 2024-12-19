package uib.swarchitecture.quepasa.infrastructure.controllers.utils;

import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private T data;
    private String errorMessage;

    public ApiResponse(T data) {
        this.data = data;
        this.errorMessage = null;
    }

    public ApiResponse(String errorMessage) {
        this.data = null;
        this.errorMessage = errorMessage;
    }
}