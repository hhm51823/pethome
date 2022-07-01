package cn.raths.basic.exception;

public class BusinessException extends RuntimeException{

    public BusinessException(){}

    public BusinessException(String message) {
        super(message);
    }
}
