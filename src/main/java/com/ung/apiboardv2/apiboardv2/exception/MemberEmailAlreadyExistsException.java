package com.ung.apiboardv2.apiboardv2.exception;

public class MemberEmailAlreadyExistsException  extends RuntimeException{
    public MemberEmailAlreadyExistsException(String message) {
        super(message);
    }
}
