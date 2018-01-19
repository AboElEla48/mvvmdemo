package com.foureg.baseframework.model;

import com.foureg.baseframework.messages.MessagesActor;
import com.foureg.baseframework.messages.MessagesServer;
import com.foureg.baseframework.messages.data.CustomMessage;

/**
 * Created by aboelela on 06/01/18.
 * This should be the parent model class for all models
 */

public class BaseDataModel implements MessagesActor
{
    public BaseDataModel(){
        MessagesServer.getInstance().registerActor(this);
    }

    @Override
    public void onReceiveMessage(int payload, CustomMessage customMessage) {

    }
}
