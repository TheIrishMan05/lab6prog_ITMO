package se.ifmo.commands.list;


import se.ifmo.collection.Resource;
import se.ifmo.collection.format.CollectionManager;
import se.ifmo.collection.models.StudyGroup;
import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.util.List;
import java.util.Optional;

public class RemoveGreater extends Command {

    public RemoveGreater() {
        super("remove_greater", "{element} - remove all elements where name length is greater " +
                "than written element has.", 1);
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
                .filter(studyGroup -> studyGroup.compareTo(inputElement) >= 0)
                .toList();

        groupsToRemove.forEach(collectionManager.getResource()::remove);

        return new Response(String.format("All elements greater than %s have been removed", inputElement.getName()));
    }

}
