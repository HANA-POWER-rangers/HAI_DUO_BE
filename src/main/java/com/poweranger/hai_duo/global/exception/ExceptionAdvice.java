package com.poweranger.hai_duo.global.exception;

import com.poweranger.hai_duo.global.response.ApiResponse;
import com.poweranger.hai_duo.global.response.code.ResponseStatus;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException e, WebRequest request) {
        String message = e.getConstraintViolations().stream()
                .map(cv -> cv.getMessage())
                .findFirst()
                .orElse("Validation failed");

        ErrorStatus status = resolveErrorStatusByMessage(message);
        return buildErrorResponse(e, status, request, null);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException e, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(err -> {
            String field = err.getField();
            String msg = Optional.ofNullable(err.getDefaultMessage()).orElse("");
            errors.merge(field, msg, (oldVal, newVal) -> oldVal + ", " + newVal);
        });

        return buildErrorResponse(e, ErrorStatus._BAD_REQUEST, request, errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception e, WebRequest request) {
        log.error("Unhandled Exception", e);
        return buildErrorResponse(e, ErrorStatus._INTERNAL_SERVER_ERROR, request, e.getMessage());
    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Object> handleCustomException(GeneralException e, HttpServletRequest request) {
        return buildErrorResponse(
                e,
                e.getErrorReasonHttpStatus(),
                new ServletWebRequest(request),
                null
        );
    }

    private ResponseEntity<Object> buildErrorResponse(Exception e, ErrorStatus status, WebRequest request, Object detail) {
        ApiResponse<Object> body = ApiResponse.onFailure(status.getCode(), status.getMessage(), detail);
        return super.handleExceptionInternal(e, body, HttpHeaders.EMPTY, status.getHttpStatus(), request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception e, ResponseStatus reason, WebRequest request, Object detail) {
        ApiResponse<Object> body = ApiResponse.onFailure(reason.getCode(), reason.getMessage(), detail);
        return super.handleExceptionInternal(e, body, HttpHeaders.EMPTY, reason.getHttpStatus(), request);
    }

    private ErrorStatus resolveErrorStatusByMessage(String message) {
        return Arrays.stream(ErrorStatus.values())
                .filter(status -> status.getMessage().equals(message))
                .findFirst()
                .orElse(ErrorStatus._INTERNAL_SERVER_ERROR);
    }
}
