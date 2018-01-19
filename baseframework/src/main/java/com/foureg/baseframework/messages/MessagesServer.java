package com.foureg.baseframework.messages;

import com.foureg.baseframework.exceptions.ErrorNonActorMessagesReceiverException;
import com.foureg.baseframework.messages.data.CustomMessage;
import com.foureg.baseframework.types.Property;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Observable;

/**
 * Created by aboelela on 19/01/18.
 * This is the server for all messages exchanged among all actors in system
 */

public class MessagesServer
{
    protected MessagesServer() {
        messagesActors = new ArrayList<>();
    }

    /**
     * Create single object to messages server
     *
     * @return : single object to server
     */
    public static MessagesServer getInstance() {
        if (messagesServer == null) {
            messagesServer = new MessagesServer();
        }

        return messagesServer;
    }

    /**
     * Register message actor to receive messages
     *
     * @param actor : the actor to receive messages
     */
    public void registerActor(MessagesActor actor) {
        messagesActors.add(actor);
    }

    /**
     * Remove message actor from messages server
     *
     * @param actor : the actor to remove
     */
    public void unregisterActor(MessagesActor actor) {
        messagesActors.remove(actor);
    }

    /**
     * send message to actor
     *
     * @param receiver : the receiver actor class to message
     * @param message  : the message object to send
     */
    public void sendMessage(Class<?> receiver, CustomMessage message)
            throws ErrorNonActorMessagesReceiverException{

        Property<Boolean> isReceiverActorInstance = new Property<>();
        isReceiverActorInstance.set(false);
        Observable.fromIterable(Arrays.asList(receiver.getInterfaces()))
                .filter(type -> type.getName().equals(MessagesActor.class.getName()))
                .blockingSubscribe(c -> isReceiverActorInstance.set(true));

        if(!isReceiverActorInstance.get()) {
            throw new ErrorNonActorMessagesReceiverException("MessagesServer::sendMessage, " +
                    "you are trying to send message to Non-Actor receiver. " +
                    "Please implement MessagesActor to " + receiver.getName());
        }

        Observable.fromIterable(messagesActors)
                .filter(actor -> actor.getClass().getName().equals(receiver.getName()))
                .blockingFirst()
                .onReceiveMessage(message.getPayLoad(), message);
    }

    private static MessagesServer messagesServer;

    // Hold all actors in system
    protected ArrayList<MessagesActor> messagesActors;
}
