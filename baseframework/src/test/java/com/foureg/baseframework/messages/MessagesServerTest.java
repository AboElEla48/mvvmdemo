package com.foureg.baseframework.messages;

import com.foureg.baseframework.exceptions.ErrorNonActorMessagesReceiverException;
import com.foureg.baseframework.messages.data.CustomMessage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by aboelela on 19/01/18.
 * Test messages server
 */
public class MessagesServerTest
{
    @Before
    public void initServer() {

        receiverActor = new TestReceiverActor();
    }

    @Test
    public void test_actorRegistration() throws Exception {
        TestMessagesServer messagesServer = new TestMessagesServer();
        messagesServer.registerActor(receiverActor);

        Assert.assertTrue(messagesServer.getActors().contains(receiverActor));

        messagesServer.unregisterActor(receiverActor);

        Assert.assertTrue(!messagesServer.getActors().contains(receiverActor));
    }

    @Test
    public void test_sendMessageToActor() throws Exception {
        TestMessagesServer messagesServer = new TestMessagesServer();
        messagesServer.registerActor(receiverActor);

        messagesServer.sendMessage(TestReceiverActor.class, new CustomMessage(1, 10,
                MSG_TXT));

        Assert.assertTrue(receiverActor.payload == 10);
        Assert.assertTrue(receiverActor.customMessage.getData().equals(MSG_TXT));
    }

    @Test
    public void test_sendMessageToNonActor() throws Exception {
        TestMessagesServer messagesServer = new TestMessagesServer();

        boolean isException = false;

        try {
            messagesServer.sendMessage(TestReceiverNonActor.class, new CustomMessage(1, 10,
                    MSG_TXT));
        }catch (ErrorNonActorMessagesReceiverException ex) {
            isException = true;
        }

        Assert.assertTrue(isException);


    }

    private TestReceiverActor receiverActor;
    private final String MSG_TXT = "Custom Message text";
}