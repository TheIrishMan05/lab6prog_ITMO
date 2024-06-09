package se.ifmo.commands.list;


import se.ifmo.collection.format.CollectionManager;
import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

public class Show extends Command {

    public Show() {
        super("show", "print all elements of the collection");
    }

    @Override
    public Response execute(Request request) {
        return new Response("All elements of the collection:", CollectionManager.getInstance().getResource());
    }
}
