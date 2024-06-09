package se.ifmo.commands.list;


import se.ifmo.collection.format.CollectionManager;
import se.ifmo.collection.models.StudyGroup;
import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.util.List;
import java.util.Optional;

public class RemoveLower extends Command {

    public RemoveLower() {
        super("remove_lower", "{element} - remove all elements from the collection" +
                " where name length is less than written element has.", 1);
    }

    @Override
    public Response execute(Request request) {
        if (request.getResource().isEmpty()){
            return new Response("Collection is empty");
        }

        CollectionManager collectionManager = CollectionManager.getInstance();

        StudyGroup inputElement = request.getResource().iterator().next();

        List<StudyGroup> groupsToRemove = collectionManager.getResource()
                .stream()
                .filter(studyGroup -> studyGroup.compareTo(inputElement) < 0)
                .toList();

        groupsToRemove.forEach(collectionManager.getResource()::remove);

        return new Response(String.format("All elements lower than %s have been removed", inputElement.getName()));
    }
}
