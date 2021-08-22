package org.tadalabs.lambda.example.entity;

public class MessageSequencerRequest {

    private MessageSequenceRequestType type;
    private String session;

    public MessageSequenceRequestType getType() {
        return type;
    }
    public void setType(MessageSequenceRequestType type) {
        this.type = type;
    }

    public String getSession() {
        return session;
    }
    public void setSession(String session) {
        this.session = session;
    }

    public MessageSequencerRequest() { }

    public MessageSequencerRequest(MessageSequenceRequestType type, String session) {
        this.type = type;
        this.session = session;
    }
}