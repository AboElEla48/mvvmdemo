package com.foureg.baseframework.messages;

import com.foureg.baseframework.exceptions.ErrorNonActorMessagesReceiverException;
import com.foureg.baseframework.messages.data.CustomMessage;
import com.foureg.baseframework.types.Property;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * Created by aboelela on 19/01/18.
 * This is the server for all messages exchanged among all actors in system
 */

public class MessagesServer
{
    protected MessagesServer() {
        messagesActors = new ArrayList<>();
        pendingMessages = new HashMap<>();
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

        // When registering new actor, check if there are pending messages for it
        CustomMessage pendingMessage = pendingMessages.get(actor.getClass());
        if(pendingMessage != null) {
            actor.onReceiveMessage(pendingMessage.getPayLoad(), pendingMessage);
            pendingMessages.remove(actor.getClass());
        }
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
    public void sendMessage(final Class<?> receiver, final CustomMessage message)
            throws ErrorNonActorMessagesReceiverException {

        final Property<Boolean> isReceiverActorInstance = new Property<>();
        isReceiverActorInstance.set(false);

        // Assure the receiver is message actor
        Observable.fromIterable(Arrays.asList(receiver.getMethods()))
                .filter(new Predicate<Method>()
                {
                    @Override
                    public boolean test(Method method) throws Exception {
                        return method.getName().equals(MessagesActor.class.getMethods()[0].getName());
                    }
                })
                .blockingSubscribe(new Consumer<Method>()
                {
                    @Override
                    public void accept(Method method) throws Exception {
                        isReceiverActorInstance.set(true);
                    }
                });

        if (!isReceiverActorInstance.get()) {
            throw new ErrorNonActorMessagesReceiverException("MessagesServer::sendMessage, " +
                    "you are trying to send message to Non-Actor receiver. " +
                    "Please implement MessagesActor to " + receiver.getName());
        }

        final Property<Boolean> isMsgSent = new Property<>();
        isMsgSent.set(false);

        // search for object to receiver and send message to it
        Observable.fromIterable(messagesActors)
                .filter(new Predicate<MessagesActor>()
                {
                    @Override
                    public boolean test(MessagesActor actor) throws Exception {
                        return actor.getClass().getName().equals(receiver.getName());
                    }
                })
                .blockingSubscribe(new Consumer<MessagesActor>()
                {
                    @Override
                    public void accept(MessagesActor actor) throws Exception {
                        actor.onReceiveMessage(message.getPayLoad(), message);
                        isMsgSent.set(true);
                    }
                });


        // check if message sent or it will be pending message
        if (!isMsgSent.get()) {
            pendingMessages.put(receiver, message);
        }
    }

    private static MessagesServer messagesServer;

    // Hold all actors in system
    protected ArrayList<MessagesActor> messagesActors;

    // Hold map of pending messages
    private HashMap<Class<?>, CustomMessage> pendingMessages;
}
