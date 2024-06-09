package se.ifmo.collection.format;

import lombok.extern.log4j.Log4j2;
import se.ifmo.collection.Resource;
import se.ifmo.collection.format.csv.CsvFormatWorker;
import se.ifmo.collection.format.csv.FormatWorker;
import se.ifmo.collection.models.StudyGroup;

import java.nio.file.Path;

@Log4j2
public class FileManager {
    private static FormatWorker<StudyGroup> formatWorker = new CsvFormatWorker();

    public static Resource loadCollection(Path path) {
        Resource resource = new Resource();
        resource.addAll(formatWorker.readFile(path));
        return resource;
    }

    public static void saveCollection(Path path, Resource resource) {
        try {
            formatWorker.writeFile(resource, path);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


}

