package se.ifmo;

import se.ifmo.collection.format.CollectionManager;
import se.ifmo.commands.list.*;
import se.ifmo.console.Console;
import se.ifmo.console.ConsoleInterface;
import se.ifmo.handlers.Handler;
import se.ifmo.handlers.HandlerConfiguration;
import se.ifmo.server.Server;
import se.ifmo.server.ServerConfiguration;

import java.nio.file.Path;
import java.util.List;

public class ServerApplication {
    public static final Handler HANDLER = new Handler(new HandlerConfiguration(List.of(
        new Add(),
        new AddIfMax(),
        new Clear(),
        new CountLessThan(),
        new ExecuteScript(),
        new Exit(),
        new FilterGreaterThan(),
        new History(),
        new Info(),
        new PrintAscending(),
        new RemoveById(),
        new RemoveGreater(),
        new RemoveLower(),
        new Show(),
        new UpdateId()
    )));
    public static Path ROOT_FILE;

    public static Path getRootFile() {
        return ROOT_FILE;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Please, write filename as command line argument.");
            return;
        }

        ROOT_FILE = Path.of(args[0]);
        CollectionManager.getInstance().load(getRootFile());
        try (ConsoleInterface serverConsole = new Console();
             Server server = new Server(new ServerConfiguration(9834), HANDLER, serverConsole)){

            server.start();
        } catch (Exception e) {
            System.err.println("Error while running: " + e.getMessage());
        }
    }
}