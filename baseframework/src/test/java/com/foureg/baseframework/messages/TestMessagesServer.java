package com.foureg.baseframework.messages;

import java.util.List;

/**
 * Created by aboelela on 19/01/18.
 * Test messages server
 */

class TestMessagesServer extends MessagesServer
{
    List<MessagesActor> getActors() {
        return this.messagesActors;
    }
}
