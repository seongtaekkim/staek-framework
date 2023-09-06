package com.staekframework.jdbc.exception;

public class WrongSizeException extends RuntimeException {

    private final int expect;
    private final int actual;
    public WrongSizeException(int expect, int actual) {
        super("Wrong size - expected: " + expect);
        this.expect = expect;
        this.actual = actual;
    }

    /**
     * trace skip
     * @return
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
