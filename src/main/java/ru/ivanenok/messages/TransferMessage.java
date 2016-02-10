package ru.ivanenok.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.UUID;

/**
 * Created by ivanenok on 2/10/16.
 */
public class TransferMessage {
    private String type;

    @JsonProperty("sequence_id")
    private String sequenceId;

    private Object data;

    public TransferMessage(String sequenceId, String type, Object data) {
        this.type = type;
        this.sequenceId = sequenceId;
        this.data = data;
    }

    public TransferMessage(String type, Object data) {
        this.type = type;
        this.sequenceId = UUID.randomUUID().toString();
        this.data = data;
    }

    protected TransferMessage() {

    }

    public String getType() {
        return type;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public Object getData() {
        return data;
    }

    @SuppressWarnings("unchecked")
    @JsonIgnore
    public Map<String, String> getDataObject() {
        return (Map<String, String>) data;
    }
}
