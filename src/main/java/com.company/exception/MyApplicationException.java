package com.company.exception;

/**
 * Application exception class.
 *
 * Created by tymchenkoivan on 08.07.2015.
 */
public class MyApplicationException extends Exception{

    public MyApplicationException() {
        super();
    }

    public MyApplicationException(String s) {
        super(s);
    }
}
