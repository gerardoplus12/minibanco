package com.app.bnc.exception;
 
public class DataDuplicadeException extends RuntimeException{
    private static final long serialVersionUID = 1L;

	public DataDuplicadeException(String message) {
        super(message);
    }
}