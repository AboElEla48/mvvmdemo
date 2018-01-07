package com.foureg.baseframework.exceptions;

/**
 * Created by aboelela on 07/01/18.
 * This exception is thrown in case the framework failed to initializes
 */

public class ErrorInitializingFramework extends RuntimeException
{
    public ErrorInitializingFramework(String message) {
        super(message);
    }
}
