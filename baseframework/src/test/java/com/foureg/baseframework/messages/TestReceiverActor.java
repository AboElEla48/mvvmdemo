package com.foureg.baseframework.messages;

import com.foureg.baseframework.messages.data.CustomMessage;

/**
 * Created by aboelela on 19/01/18.
 * Test receiver
 */

public class TestReceiverActor implements MessagesActor
{
    @Override
    public void onReceiveMessage(int payload, CustomMessage customMessage) {
        this.customMessage = customMessage;
        this.payload = payload;
    }

    CustomMessage customMessage;
    int payload;
}
