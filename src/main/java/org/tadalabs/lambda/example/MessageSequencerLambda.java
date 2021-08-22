package org.tadalabs.lambda.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.tadalabs.lambda.example.entity.MessageSequencerRequest;
import org.tadalabs.lambda.example.entity.MessageSequencerResponse;

public class MessageSequencerLambda implements RequestHandler<MessageSequencerRequest,
        MessageSequencerResponse> {

    private MessageSequenceController controller = new MessageSequenceController();

    public MessageSequencerLambda() {
    }

    public void setController(MessageSequenceController controller) {
        this.controller = controller;
    }

    private MessageSequencerResponse onReportRequest(
            MessageSequencerRequest request, Context context) {
        return new MessageSequencerResponse(controller.getSessionValue(),
                controller.getSequenceValue());
    }

    private MessageSequencerResponse onSequenceRequest(
            MessageSequencerRequest request, Context context) {
        Integer sequence = controller.getSequenceValue() + 1;
        controller.updateSequenceValue(sequence);
        return new MessageSequencerResponse(controller.getSessionValue(),
                sequence);
    }

    private MessageSequencerResponse onStartSessionRequest(
            MessageSequencerRequest request, Context context) {
        controller.startSession(request.getSession());
        return new MessageSequencerResponse(controller.getSessionValue(),
                null);
    }

    @Override
    public MessageSequencerResponse handleRequest(
            MessageSequencerRequest request, Context context) {
        controller.setClient(System.getenv("environment"));
        switch (request.getType()) {
            case START_SESSION:
                return onStartSessionRequest(request, context);
            case SEQUENCE:
                return onSequenceRequest(request, context);
            case REPORT:
                return onReportRequest(request, context);
            default:
                return new MessageSequencerResponse("invalid request type");
        }
    }
}