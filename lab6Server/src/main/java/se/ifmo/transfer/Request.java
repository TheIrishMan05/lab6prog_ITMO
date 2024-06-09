package se.ifmo.transfer;

import se.ifmo.collection.Resource;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String command = "";
    private String text = "";
    private Resource resource = new Resource();

    public Request() {}

    public Request(String command, Resource resource) {
        this.command = command;
        this.resource = resource;
    }

    public Request(String command, String text) {
        this.command = command;
        this.text = text;
    }

    public Request(String command, String text, Resource resource) {
        this.command = command;
        this.text = text;
        this.resource = resource;
    }

    public Request(String text) {
        this.text = text;
    }


    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(command, request.command) && Objects.equals(text, request.text) && Objects.equals(resource, request.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, text, resource);
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", text='" + text + '\'' +
                ", resource=" + resource +
                '}';
    }
}
