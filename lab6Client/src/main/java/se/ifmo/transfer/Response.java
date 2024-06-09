package se.ifmo.transfer;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import se.ifmo.collection.Resource;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

@EqualsAndHashCode
@ToString
public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String text = "";
    private Resource resource = new Resource();

    private HashMap<String, Integer> commands = new HashMap<>();
    private Deque<String> inboundRequests = new ArrayDeque<>();


    public Response(String text) {
        this.text = text;
    }

    public Response(Resource resource) {
        this.resource = resource;
    }

    public Response(String text, Resource resource) {
        this.text = text;
        this.resource = resource;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public HashMap<String, Integer> getCommands() {
        return commands;
    }

    public void setCommands(HashMap<String, Integer> commands) {
        this.commands = commands;
    }

    public Deque<String> getInboundRequests() {
        return inboundRequests;
    }

    public void setInboundRequests(Deque<String> inboundRequests) {
        this.inboundRequests = inboundRequests;
    }

}
