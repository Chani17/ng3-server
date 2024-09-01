// 이하린 : 방 검증 관련 사용자 정의 예외 모음

package com.nggg.ng3.exception;

public class CustomExceptions {

    public static class RoomFullException extends RuntimeException {
        public RoomFullException(String message) {
            super(message);
        }
    }

    public static class InvalidPasswordException extends RuntimeException {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }

    public static class GameAlreadyStartedException extends RuntimeException {
        public GameAlreadyStartedException(String message) {
            super(message);
        }
    }

    public static class RoomNotFoundException extends RuntimeException {
        public RoomNotFoundException(String message) {
            super(message);
        }
    }
}