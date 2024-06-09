package se.ifmo.commands.list;


import se.ifmo.collection.format.CollectionManager;
import se.ifmo.collection.models.Semester;
import se.ifmo.commands.Command;
import se.ifmo.transfer.Request;
import se.ifmo.transfer.Response;

public class CountLessThan extends Command {

    public CountLessThan() {
        super("count_less_than_semester_enum", "<semesterEnum> - print amount of elements" +
                " where number of semester is less than written one.");
    }

    @Override
    public Response execute(Request request) {
        if (request.getText() == null || request.getText().isEmpty()) {
            return new Response("Error! Command is typed without args");
        }

        if (!request.getText().matches("\\w+")) {
            return new Response("Error! Command arg is not a word");
        }

        Semester inputSemester = Semester.valueOf(request.getText().toUpperCase());

        return new Response(String.format("Amount of groups with number of semester less than %s: %s",
                inputSemester,
                CollectionManager
                        .getInstance()
                        .getResource()
                        .stream()
                        .filter(studyGroup -> studyGroup.getSemesterEnum().ordinal() < inputSemester.ordinal()).count()));
    }
}
