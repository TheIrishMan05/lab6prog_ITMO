package se.ifmo.collection;

import se.ifmo.collection.models.StudyGroup;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.TreeSet;

public class Resource extends TreeSet<StudyGroup> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDateTime localDateTime = LocalDateTime.now();

    public Resource(StudyGroup... studyGroups) {
        for (StudyGroup studyGroup : studyGroups) {
            add(studyGroup);
        }
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
