package com.app.bnc.exception;
 
public class DataNotAllowedException extends RuntimeException{
    private static final long serialVersionUID = 1L;

	public DataNotAllowedException(String message) {
        super(message);
    }
}