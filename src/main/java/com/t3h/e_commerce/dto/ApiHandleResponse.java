package com.t3h.e_commerce.dto;


public class ApiHandleResponse<T>{
    private int statusCode;
    private T error;

    public ApiHandleResponse(int statusCode, T error) {
        this.statusCode = statusCode;
        this.error = error;
    }

    public ApiHandleResponse(){

    }
    public boolean isSuccess() {
        return false;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setError(T error) {
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public T getError() {
        return error;
    }
}
