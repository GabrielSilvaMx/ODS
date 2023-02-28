package org.bedu.ods.exception;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<ErrorMessage> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {

        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex,
                request.getDescription(false));
        log.warn( "ConstraintViolationException " + message.getMessage());

        return new ResponseEntity<>(message, HttpStatus.UNPROCESSABLE_ENTITY); // https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/422
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        log.warn("ResourceNotFoundException " + message.getMessage());
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        log.warn("Exception " + message.getMessage());
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiCallError<String>> handleAccessDeniedException(
            HttpServletRequest request, AccessDeniedException ex) {

        log.warn("AccessDeniedException " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiCallError<>("Acceso denegado: ", List.of(ex.getMessage() )));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiCallError<String>> handleAuthenticationException(
            HttpServletRequest request, AuthenticationException ex) {
        log.warn("AuthenticationException " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiCallError<>("Acceso denegado", List.of(ex.getMessage())));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiCallError<String>> handleExpiredJwtException (
            HttpServletRequest request, ExpiredJwtException ex) {
        log.warn("ExpiredJwtException " + ex.getMessage());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ApiCallError<>("JWT Token expirado", List.of(ex.getMessage())));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ApiCallError<T> {

        private String message;
        private List<T> details;
    }

}