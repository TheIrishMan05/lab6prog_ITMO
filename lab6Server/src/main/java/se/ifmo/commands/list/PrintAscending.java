package se.ifmo.commands.list;


import se.ifmo.collection.format.CollectionManager;
import se.ifmo.collection.models.StudyGroup;
import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.util.stream.Collectors;

public class PrintAscending extends Command {

    public PrintAscending() {
        super("print_ascending", "print all elements of collection in ascending order");
    }

    @Override
    public Response execute(Request request) {
        return new Response(CollectionManager.getInstance().getResource().stream()
                .map(StudyGroup::getName)
                .sorted()
                .map(name -> "\n" + name)
                .collect(Collectors.joining()));
    }
}
