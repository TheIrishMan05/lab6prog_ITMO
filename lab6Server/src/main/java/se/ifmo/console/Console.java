package se.ifmo.console;

import java.io.*;

public class Console implements ConsoleInterface {
    private final BufferedReader reader;
    private final BufferedWriter writer;

    public Console() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.writer = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(System.out)));
    }

    @Override
    public String read() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }

    }

    @Override
    public boolean ready() {
        try {
            return reader.ready();
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void write(String message) {
        try {
            writer.append(message).append("\n").flush();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void writeWithoutNewLine(String message) {
        try {
            writer.append(message).flush();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void shoutDownSilently() throws Exception {
        this.writer.close();
        this.reader.close();
    }
}
