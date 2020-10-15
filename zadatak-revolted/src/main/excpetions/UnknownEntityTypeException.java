package main.excpetions;

public class UnknownEntityTypeException extends RuntimeException {

    private static final long serialVersionUID = 2440276837943861392L;

    public UnknownEntityTypeException(String message) {
        super(message);
    }
}