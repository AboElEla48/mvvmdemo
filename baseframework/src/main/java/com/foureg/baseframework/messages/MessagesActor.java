package com.foureg.baseframework.messages;

import com.foureg.baseframework.messages.data.CustomMessage;

/**
 * Created by aboelela on 19/01/18.
 * This interface represents all actors in system
 */

public interface MessagesActor
{
    void onReceiveMessage(int payload, CustomMessage customMessage);
}
