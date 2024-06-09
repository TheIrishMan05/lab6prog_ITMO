package se.ifmo.server;

import lombok.Getter;

@Getter
public class ServerConfiguration {

    private int port;

    public ServerConfiguration(int port) {
        this.port = port;
    }

}
