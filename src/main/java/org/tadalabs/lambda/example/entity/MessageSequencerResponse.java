package org.tadalabs.lambda.example.entity;

import org.json.JSONObject;

public class MessageSequencerResponse {
    private final Integer value;
    private final String session;
    private final String message;

    public String getMessage() {
        return message;
    }

    public Integer getValue() {
        return value;
    }

    public String getSession() {
        return session;
    }

    public MessageSequencerResponse(String session, Integer value) {
        this.session = session;
        this.value = value;
        this.message = null;
    }

    public MessageSequencerResponse(String message) {
        this.session = null;
        this.value = null;
        this.message = message;
    }

    public String toJSONString() {
        return new JSONObject(this).toString();
    }

}
