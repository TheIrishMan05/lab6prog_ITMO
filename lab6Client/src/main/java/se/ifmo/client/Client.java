package se.ifmo.client;

import se.ifmo.collection.ElementManager;
import se.ifmo.collection.Resource;
import se.ifmo.collection.models.StudyGroup;
import se.ifmo.io.ConsoleInterface;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.*;

public class Client implements AutoCloseable {
    private final static int BUFFER_SIZE = 10000;
    private final ClientConfiguration clientConfiguration;

    private final DatagramSocket socket;
    private final ConsoleInterface console;

    private boolean handle = true;

    private Deque<String> requests = new LinkedList<>();

    private Set<String> executeScriptHistory = new HashSet<>();

    private Map<String, Integer> requiredElementsForCommand = new HashMap<>();

    public Client(ClientConfiguration clientConfiguration, ConsoleInterface console) throws SocketException {
        this.console = console;
        this.socket = new DatagramSocket();
        this.clientConfiguration = clientConfiguration;
    }

    public void start() throws IOException, ClassNotFoundException {
        console.write("$$ Labwork 6 $$");
        console.write("If you want to quit: type 'exit'");
        console.write("If you want to get info about commands: type 'help'");
        console.write("If you want to clear console: type 'clear'");

        String input = "";
        main: while (handle && (!requests.isEmpty() || (input = console.writeAndRead("$ ")) != null && !input.equals("exit"))) {

            if (!requests.isEmpty()){
                input = requests.pollFirst();
            } else {
                executeScriptHistory.clear();
            }
            if (input == null) break;
            if (input.isEmpty() || input.isBlank()) continue;

            if ("clear".equals(input)) {
                console.clear();
                continue;
            }

            if ("exit".equals(input)) {
                handle = false;
                continue;
            }

            if (input.contains("execute_script")) {
                executeScriptHistory.add(input);
            }

            StringTokenizer tokenizer = new StringTokenizer(input, " ");
            Request request = new Request();

            String command = tokenizer.nextToken();
            int requiredElements = requiredElementsForCommand.getOrDefault(command, 0);

            while (tokenizer.hasMoreTokens()) request.setText(request.getText() + tokenizer.nextToken() + " ");
            request.setText(request.getText().trim());




            while (requiredElements-- > 0) {
                StudyGroup objectInput;
                if (requests.isEmpty()){
                    objectInput = ElementManager.collect(console);
                }
                else {
                    objectInput = ElementManager.collect(requests);
                }
                if (objectInput == null) continue main;
                request.getResource().add(objectInput);
            }


            request.setCommand(command);

            send(request);
            Response result = receive();

            if (result == null) {
                console.write("Command's output is empty.");
                continue;
            }

            if (!result.getInboundRequests().isEmpty()
                    && result.getInboundRequests().stream().noneMatch(executeScriptHistory::contains)){
                requests.addAll(result.getInboundRequests());
            }
            if (!result.getCommands().isEmpty()) requiredElementsForCommand = result.getCommands();

            console.write(result.getText());
            result.getResource().forEach((element) -> console.write("#%d %s", element.getId(), element));
        }

        console.write("Program is shutting down...");

    }

    private void send(Request request) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
        objectOutputStream.writeObject(request);
        objectOutputStream.flush();

        byte[] bytesSend = byteStream.toByteArray();

        DatagramPacket sendingPacket = new DatagramPacket(bytesSend, bytesSend.length, clientConfiguration.getHost(), clientConfiguration.getPort());
        socket.send(sendingPacket);

    }


    private Response receive() throws IOException, ClassNotFoundException {
        byte[] bufferReceive = new byte[BUFFER_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(bufferReceive, bufferReceive.length);
        socket.receive(receivePacket);

        ByteArrayInputStream byteStream = new ByteArrayInputStream(receivePacket.getData());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
        return (Response) objectInputStream.readObject();

    }

    @Override
    public void close() {
        if (!socket.isClosed()) socket.close();
    }
}
