package se.ifmo.collection.format.csv;

import se.ifmo.collection.models.StudyGroup;

import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface FormatWorker<E> {
    List<E> readFile(Path filePath);

    void writeFile(Collection<StudyGroup> values, Path filePath);

    List<E> readString(String str);

    String writeString(List<E> values);

    void removeById(long id, Path filePath);
}
