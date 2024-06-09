package se.ifmo.commands.list;


import se.ifmo.collection.format.CollectionManager;
import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

public class Clear extends Command {

    public Clear() {
        super("clear_collection", "clear the collection");
    }

    @Override
    public Response execute(Request request) {
        CollectionManager.getInstance().getResource().clear();
        return new Response("Collection has been cleared");
    }

}
