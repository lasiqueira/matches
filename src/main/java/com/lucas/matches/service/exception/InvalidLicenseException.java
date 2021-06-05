package com.lucas.matches.service.exception;

public class InvalidLicenseException extends RuntimeException{
    public InvalidLicenseException(String msg){
        super(msg);
    }
}
