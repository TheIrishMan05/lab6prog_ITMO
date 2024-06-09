package se.ifmo.server;

import lombok.extern.log4j.Log4j2;
import se.ifmo.ServerApplication;
import se.ifmo.collection.format.CollectionManager;
import se.ifmo.console.ConsoleInterface;
import se.ifmo.handlers.Handler;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;



@Log4j2
public class Server implements AutoCloseable {

    private static final int BUFFER_SIZE = 10000;

    private final ServerConfiguration serverConfiguration;
    private final Handler handler;

    private final ConsoleInterface console;
    private DatagramSocket datagramSocket;

    private boolean isRunning = true;

    public Server(ServerConfiguration serverConfiguration, Handler handler, ConsoleInterface console) {
        this.serverConfiguration = serverConfiguration;
        this.handler = handler;
        this.console = console;
    }

    public void start() {
        log.info("Server is starting...");
        try {
            datagramSocket = new DatagramSocket(serverConfiguration.getPort());
            log.info("Server is listening on port {} \n", serverConfiguration.getPort());

            console.write("Type 'help' to see available commands.");

            listen();
        } catch (Exception e) {
            log.error("Error during server start: {}", e.getMessage());
        }
    }

    public void listen() {
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket incomingPacket = new DatagramPacket(buffer, buffer.length);

        while (isRunning) {
            if (console.ready()) console.write(processCommand(console.read()));

            try {
                datagramSocket.setSoTimeout(1000);
                datagramSocket.receive(incomingPacket);
            } catch (IOException e) {
                continue;
            }

            processPacket(incomingPacket);
        }
    }

    private void processPacket(DatagramPacket packet) {
        try {
            Request request = deserializeRequest(packet.getData());
            log.info("Received request: {} from {}", request.toString(), packet);

            Response response = handle(request);
            log.info("Responding with: {}", response);

            send(response, packet.getAddress(), packet.getPort());
        } catch (IOException | ClassNotFoundException e) {
            log.warn(e.getMessage());
        }
    }

    private String processCommand(String command) {
        return switch (command) {
            case "help" -> "[x] available commands: help, stop, save";
            case "save" -> {
                CollectionManager.getInstance().save();
                yield "[x] saved";
            }
            case "stop" -> {
                isRunning = false;
                yield "[x] server stopped";
            }
            default -> String.format("[x] unknown command: '%s'", command);
        };
    }

    private Response handle(Request request) {
        return handler.execute(request);
    }

    private void send(Response response, InetAddress clientAddress, int clientPort) {
        try {
            response.setCommands(ServerApplication.HANDLER.getHandlerConfiguration().requiredElements());

            byte[] responseData = serializeResponse(response);
            DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length, clientAddress, clientPort);
            datagramSocket.send(responsePacket);
        } catch (IOException e) {
            log.warn(e.getMessage());
        }
    }


    private byte[] serializeResponse(Response response) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
        outputStream.writeObject(response);
        outputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    private Request deserializeRequest(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        return (Request) objectInputStream.readObject();
    }

    @Override
    public void close() throws Exception {
        log.info("Server is closing...");

        if (datagramSocket != null && !datagramSocket.isClosed()) datagramSocket.close();
        datagramSocket = null;


        isRunning = false;
    }
}
