package org.tadalabs.lambda.example.tests;

import org.junit.Test;
import org.junit.Assert;
import org.tadalabs.lambda.example.MessageSequenceController;
import org.tadalabs.lambda.example.MessageSequencerLambda;
import org.tadalabs.lambda.example.entity.MessageSequenceRequestType;
import org.tadalabs.lambda.example.entity.MessageSequencerRequest;
import org.tadalabs.lambda.example.entity.MessageSequencerResponse;

import static org.mockito.Mockito.*;

public class MessageSequencerLambdaTest {
    @Test
    public void Can_Report_Upon_Valid_Request() {
        MessageSequenceController mockController = mock(MessageSequenceController.class);
        when(mockController.getSessionValue()).thenReturn("test-session");
        when(mockController.getSequenceValue()).thenReturn(0);
        doNothing().when(mockController).setClient(System.getenv("environment"));
        MessageSequencerLambda sequencer = new MessageSequencerLambda();
        sequencer.setController(mockController);
        Assert.assertEquals(new MessageSequencerResponse("test-session", 0).toJSONString(),
                sequencer.handleRequest(new MessageSequencerRequest(MessageSequenceRequestType.REPORT,null),
                        new MockableLambdaContext()).toJSONString());
    }

    @Test
    public void Can_Sequence_Upon_Valid_Request() {
        MessageSequenceController mockController = mock(MessageSequenceController.class);
        when(mockController.getSessionValue()).thenReturn("test-session");
        when(mockController.getSequenceValue()).thenReturn(0);
        doNothing().when(mockController).setClient(System.getenv("environment"));
        MessageSequencerLambda sequencer = new MessageSequencerLambda();
        sequencer.setController(mockController);
        Assert.assertEquals(new MessageSequencerResponse("test-session", 1).toJSONString(),
                sequencer.handleRequest(new MessageSequencerRequest(MessageSequenceRequestType.SEQUENCE,null),
                        new MockableLambdaContext()).toJSONString());
    }

    @Test
    public void Can_StartSession_Upon_Valid_Request() {
        MessageSequenceController mockController = mock(MessageSequenceController.class);
        when(mockController.getSessionValue()).thenReturn("test-session");
        doNothing().when(mockController).startSession("test-session");
        MessageSequencerLambda sequencer = new MessageSequencerLambda();
        sequencer.setController(mockController);
        Assert.assertEquals(new MessageSequencerResponse("test-session", null).toJSONString(),
                sequencer.handleRequest(new MessageSequencerRequest(MessageSequenceRequestType.START_SESSION,"test-session"),
                        new MockableLambdaContext()).toJSONString());
    }

    @Test
    public void Can_HandleInvalidRequests_Upon_Invalid_Request() {
        MessageSequenceController mockController = mock(MessageSequenceController.class);
        when(mockController.getSessionValue()).thenReturn("test-session");
        doNothing().when(mockController).startSession("test-session");
        MessageSequencerLambda sequencer = new MessageSequencerLambda();
        sequencer.setController(mockController);
        Assert.assertEquals(new MessageSequencerResponse("invalid request type").toJSONString(),
                sequencer.handleRequest(new MessageSequencerRequest(MessageSequenceRequestType.INVALID,null),
                        new MockableLambdaContext()).toJSONString());
    }
}
