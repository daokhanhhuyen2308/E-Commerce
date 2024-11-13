package com.t3h.e_commerce.exception;

import com.t3h.e_commerce.dto.ApiHandleResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomExceptionHandler.class)
    public ResponseEntity<ApiHandleResponse<CustomError>> handleProductException(CustomExceptionHandler ex, HttpServletRequest request){

        ApiHandleResponse<CustomError> handleResponse = new ApiHandleResponse<>();
        handleResponse.setStatusCode(ex.getStatus().value());

        CustomError error = ex.getError();
        error.setTimestamp(new Date());
        error.setPath(request.getRequestURI());

        handleResponse.setError(error);

        return ResponseEntity.status(ex.getStatus()).body(handleResponse);

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiHandleResponse<CustomError>> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception,
                                                                           HttpServletRequest request){

        ApiHandleResponse<CustomError> apiHandleResponse = new ApiHandleResponse<>();
        apiHandleResponse.setStatusCode(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

        CustomError customError = new CustomError();
        customError.setCode("METHOD_NOT_ALLOWED");
        customError.setTimestamp(new Date());
        customError.setPath(request.getRequestURI());
        customError.setDetails("The " + exception.getMethod() + " method is not supported for route " + request.getRequestURI()
                                + ". Supported method: " + exception.getSupportedHttpMethods());
        customError.setMessage("The " +exception.getMethod() + " method is not supported for route " + request.getRequestURI()
                + ". Supported method: " + exception.getSupportedHttpMethods());

        apiHandleResponse.setError(customError);

        return new ResponseEntity<>(apiHandleResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiHandleResponse<CustomError>> handleGeneralException(Exception exception, HttpServletRequest request){

        ApiHandleResponse<CustomError> apiResponse = new ApiHandleResponse<>();
        apiResponse.setStatusCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        CustomError customError = new CustomError();
        customError.setCode("INTERNAL_SERVER_ERROR");
        customError.setDetails("An unexpected error occurred. Please try again later!");
        customError.setMessage(exception.getMessage());
        customError.setTimestamp(new Date());
        customError.setPath(request.getRequestURI());

       apiResponse.setError(customError);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiHandleResponse<CustomError>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                                                HttpServletRequest request){
        ApiHandleResponse<CustomError> apiHandleResponse = new ApiHandleResponse<>();
        apiHandleResponse.setStatusCode(HttpServletResponse.SC_BAD_REQUEST);

        CustomError customError = new CustomError();
        customError.setCode("VALIDATION_ERROR");
        customError.setTimestamp(new Date());
        customError.setPath(request.getRequestURI());
        customError.setDetails("Validation failed. Please check your information!");
        customError.setMessage(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage());

        apiHandleResponse.setError(customError);

        return ResponseEntity.badRequest().body(apiHandleResponse);
    }

}
