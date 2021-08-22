package org.tadalabs.lambda.example;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.AttributeUpdate;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;

public class MessageSequenceController {

    private DynamoDB client;
    private static final String TABLE_NAME = "message-processing";
    private static final String KEY_NAME = "id";
    private static final String KEY_VALUE = "message-sequence-state";
    private static final String SESSION_KEY = "session-value";
    private static final String SEQUENCE_KEY = "sequence-value";
    private static final String LOCAL_ENDPOINT = "http://localhost:8000";
    private static final String LOCAL_REGION = "local";

    public void setClient(String environment) {
        if (null == environment || !environment.equals("production")) {
            client = new DynamoDB(AmazonDynamoDBClientBuilder
                    .standard()
                    .withEndpointConfiguration(
                            new AwsClientBuilder.EndpointConfiguration(
                                    LOCAL_ENDPOINT,
                                    LOCAL_REGION))
                    .build());
        } else {
            client = new DynamoDB(AmazonDynamoDBClientBuilder
                    .standard().withRegion(Regions.US_EAST_1)
                    .build());
        }
    }

    private Item getItem() {
        return client.getTable(TABLE_NAME)
                .getItem(KEY_NAME, KEY_VALUE);
    }

    private void updateItem(AttributeUpdate attribute) {
        client.getTable(TABLE_NAME)
                .updateItem(KEY_NAME, KEY_VALUE, attribute);
    }

    public String getSessionValue() {
        return getItem().getString(SESSION_KEY);
    }

    public Integer getSequenceValue() {
        return getItem().getInt(SEQUENCE_KEY);
    }

    void updateSequenceValue(Integer value) {
        updateItem(new AttributeUpdate(SEQUENCE_KEY).put(value));
    }

    public void startSession(String session) {
        client.getTable(TABLE_NAME)
                .updateItem(new UpdateItemSpec().withPrimaryKey(KEY_NAME, KEY_VALUE)
                        .withAttributeUpdate(new AttributeUpdate(SESSION_KEY).put(session),
                                new AttributeUpdate(SEQUENCE_KEY).put(0)));
    }
}
