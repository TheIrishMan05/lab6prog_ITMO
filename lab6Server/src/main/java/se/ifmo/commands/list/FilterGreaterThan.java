package se.ifmo.commands.list;


import se.ifmo.collection.format.CollectionManager;
import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.util.stream.Collectors;

public class FilterGreaterThan extends Command {

    public FilterGreaterThan() {
        super("filter_greater_that_should_be_expelled",
                "<shouldBeExpelled> - print all elements" +
                        " where value of the field shouldBeExpelled is greater than written one.");
    }

    @Override
    public Response execute(Request request) {
        if (request.getText().isEmpty()){
            return new Response("Argument cannot be empty");
        }

        if (!request.getText().matches("\\d+")){
            return new Response("Argument should be a number");
        }

        Long filter = Long.parseLong(request.getText());

        return new Response(CollectionManager.getInstance().getResource().stream()
                .filter(studyGroup -> studyGroup.getShouldBeExpelled() > filter)
                .collect(Collectors.toSet()).toString());

    }
}
