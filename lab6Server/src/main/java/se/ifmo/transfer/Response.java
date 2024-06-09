package se.ifmo.transfer;

import se.ifmo.collection.Resource;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Response implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String text = "";
    private Resource resource = new Resource();

    private HashMap<String, Integer> commands = new HashMap<>();
    private ArrayDeque<String> inboundRequests = new ArrayDeque<>();

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

    public Response(ArrayDeque<String> inboundRequests, String text) {
        this.inboundRequests = inboundRequests;
        this.text = text;
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

    public void addInboundRequest(String request) {
        inboundRequests.add(request);
    }

    public void addInboundRequests(Deque<String> requests) {
        inboundRequests.addAll(requests);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return Objects.equals(text, response.text) && Objects.equals(resource, response.resource) && Objects.equals(commands, response.commands) && Objects.equals(inboundRequests, response.inboundRequests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, resource, commands, inboundRequests);
    }

    @Override
    public String toString() {
        return "Response{" +
                "text='" + text + '\'' +
                ", resource=" + resource +
                ", commands=" + commands +
                ", inboundRequests=" + inboundRequests +
                '}';
    }
}
