package com.foureg.baseframework.messages.data;

/**
 * Created by aboelela on 19/01/18.
 * This data class represent the message to be exchanged between actors in messages system
 */

public class CustomMessage
{
    private int messageId;
    private Object data;
    private int payLoad;

    public CustomMessage() {}

    public CustomMessage(int messageId, int payload, Object data) {
        setMessageId(messageId);
        setData(data);
        setPayLoad(payload);
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }

    public Object getData() {
        return data;
    }

    public void setPayLoad(int payLoad) {
        this.payLoad = payLoad;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getPayLoad() {
        return payLoad;
    }
}
