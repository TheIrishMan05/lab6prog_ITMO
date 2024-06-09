package se.ifmo.commands.list;


import se.ifmo.collection.Resource;
import se.ifmo.collection.format.CollectionManager;
import se.ifmo.collection.models.StudyGroup;
import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

import java.util.Optional;

public class RemoveById extends Command {

    public RemoveById() {
        super("remove_by_id", "<ID> - remove element by written ID");
    }

    @Override
    public Response execute(Request request) {
        if (request.getText() == null || request.getText().isEmpty()) {
            return new Response("Error! Command is typed without args");
        }

        if (!request.getText().matches("\\d+")) {
            return new Response("Error! Command arg is not a number");
        }

        long idToFind = Long.parseLong(request.getText());
        Resource collection = CollectionManager.getInstance().getResource();
        StudyGroup formerStudyGroup = getById(collection, idToFind);

        if (formerStudyGroup == null) {
            return new Response(String.format("Group with ID %d does not exist", idToFind));
        } else {
            collection.remove(formerStudyGroup);
            return new Response(String.format("Element with ID %d has been removed", idToFind));
        }
    }

    private StudyGroup getById(Resource collection, Long id) {
        for (StudyGroup studyGroup : collection) {
            if (studyGroup.getId() == id) return studyGroup;
        }
        return null;
    }
}
