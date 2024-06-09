package se.ifmo.commands.list;


import se.ifmo.collection.Resource;
import se.ifmo.collection.format.CollectionManager;
import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

public class Info extends Command {

    public Info() {
        super("info", "print information about collection");
    }

    @Override
    public Response execute(Request request) {
        Resource resource = CollectionManager.getInstance().getResource();

        return new Response(String.format("Collection - %n type: %s%n - initialized: %s%n - amount of elements: %d",
                resource.getClass().getSimpleName(), resource.getLocalDateTime(), resource.size()));


    }
}
