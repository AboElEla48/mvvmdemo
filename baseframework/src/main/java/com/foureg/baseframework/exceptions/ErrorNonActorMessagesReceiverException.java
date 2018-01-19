package com.foureg.baseframework.exceptions;

/**
 * Created by aboelela on 19/01/18.
 * This exception will be thrown in case user tried to send message to non actor receiver
 */

public class ErrorNonActorMessagesReceiverException extends RuntimeException
{
    public ErrorNonActorMessagesReceiverException(String message) {
        super(message);
    }
}
