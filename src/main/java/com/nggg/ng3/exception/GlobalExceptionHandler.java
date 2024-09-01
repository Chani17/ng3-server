// 이하린 : 전역에서 사용하는 Exception Handler

package com.nggg.ng3.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.nggg.ng3.exception.CustomExceptions.*;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RoomFullException.class)
    public ResponseEntity<Object> handleRoomFullException(RoomFullException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Object> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(GameAlreadyStartedException.class)
    public ResponseEntity<Object> handleGameAlreadyStartedException(GameAlreadyStartedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(RoomNotFoundException.class)
    public ResponseEntity<Object> handleRoomNotFoundException(RoomNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "알 수 없는 오류가 발생했습니다."));
    }
}
