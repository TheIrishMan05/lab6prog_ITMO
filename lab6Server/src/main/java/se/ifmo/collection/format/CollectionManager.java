package se.ifmo.collection.format;


import se.ifmo.ServerApplication;
import se.ifmo.collection.Resource;

import java.nio.file.Path;

public class CollectionManager {
    private static CollectionManager instance = null;
    private Resource resource = null;

    private CollectionManager() {
    }

    public static CollectionManager getInstance() {
        if (instance == null) instance = new CollectionManager();
        return instance;
    }

    public Resource getResource() {
        if (resource == null) load(ServerApplication.ROOT_FILE);
        return resource;
    }

    public void save() {
        save(ServerApplication.ROOT_FILE);
    }

    public void save(Path path) {
        FileManager.saveCollection(path, resource);
    }

    public Resource load(Path path) {
        return load(path, false);
    }

    public Resource load(Path path, boolean append) {
        try {
            Resource loaded = FileManager.loadCollection(path);

            if (append) loaded.forEach(loaded::add);
            resource = loaded;

            return resource;
        } catch (Exception e) {
            return new Resource();
        }
    }
}
