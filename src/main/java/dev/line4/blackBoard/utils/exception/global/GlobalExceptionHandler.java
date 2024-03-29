package dev.line4.blackBoard.utils.exception.global;

import dev.line4.blackBoard.utils.exception.custom.BlackboardEntityDuplicateException;
import dev.line4.blackBoard.utils.exception.custom.EntityNotFoundException;
import dev.line4.blackBoard.utils.response.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidationException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<?>> handleConstraintViolationException(ConstraintViolationException e) {
        String errorMessage = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), errorMessage));
    }


    // Custom Exception
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.createFailWithoutData(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(BlackboardEntityDuplicateException.class)
    public ResponseEntity<ApiResponse<?>> handleBlackboardEntityDuplicateException(BlackboardEntityDuplicateException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.createFailWithoutData(HttpStatus.BAD_REQUEST.value(), e. getMessage()));
    }

}
