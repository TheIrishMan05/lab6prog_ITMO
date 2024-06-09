package se.ifmo.collection.format.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import se.ifmo.collection.models.Coordinates;
import se.ifmo.collection.models.Location;
import se.ifmo.collection.models.Person;
import se.ifmo.collection.models.StudyGroup;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class CsvFormatWorker implements FormatWorker<StudyGroup> {
    @Override
    public List<StudyGroup> readFile(Path filePath) {
        if (Files.notExists(filePath)) {
            System.out.println("File " + filePath + " not exists.");
            return Collections.emptyList();
        }

        if (!Files.isReadable(filePath)) {
            System.out.println("File " + filePath + " is not readable.");
            return Collections.emptyList();
        }

        try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(filePath, StandardOpenOption.READ))) {
            return new CsvToBeanBuilder<>(new InputStreamReader(in))
                    .withType(StudyGroup.class)
                    .withFilter(strings -> {
                        for (String line : strings)
                            if (line != null && !line.isEmpty())
                                return true;
                        return false;
                    })
                    .withExceptionHandler(e -> {
                        System.out.println("! error of adding element: #" + e.getLineNumber() + ": " + Arrays.toString(e.getLine()) + ": " + e.getMessage());
                        return null;
                    })
                    .build().parse().stream().map(q -> {
                        try {
                            StudyGroup unverifiedGroup = (StudyGroup) q;

                            StudyGroup result = new StudyGroup();

                            result.setId(unverifiedGroup.getId());
                            result.setName(unverifiedGroup.getName());

                            Coordinates coordinates = new Coordinates();

                            coordinates.setX(unverifiedGroup.getCoordinates().getX());
                            coordinates.setY(unverifiedGroup.getCoordinates().getY());

                            result.setCoordinates(coordinates);

                            result.setCreationDate(unverifiedGroup.getCreationDate());

                            result.setStudentsCount(unverifiedGroup.getStudentsCount());

                            result.setExpelledStudents(unverifiedGroup.getExpelledStudents());

                            result.setShouldBeExpelled(unverifiedGroup.getShouldBeExpelled());

                            result.setSemesterEnum(unverifiedGroup.getSemesterEnum());

                            Person person = new Person();

                            person.setBirthday(unverifiedGroup.getGroupAdmin().getBirthday());
                            person.setEyeColor(unverifiedGroup.getGroupAdmin().getEyeColor());
                            person.setName(unverifiedGroup.getGroupAdmin().getName());

                            Location location = new Location();
                            location.setName(unverifiedGroup.getGroupAdmin().getLocation().getName());
                            location.setX(unverifiedGroup.getGroupAdmin().getLocation().getX());
                            location.setY(unverifiedGroup.getGroupAdmin().getLocation().getY());

                            person.setLocation(location);

                            result.setGroupAdmin(person);
                            return result;
                        } catch (Throwable ex) {
                            System.out.println("Attempt to add element has failed: " + ex.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull).collect(Collectors.toList());
        } catch (Exception e) {
            if (e.getMessage().contains("CSV"))
                System.out.println("Input file is empty! Empty collection will be initialized");
            else System.out.println("Error reading file: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public void writeFile(Collection<StudyGroup> values, Path filePath) {
        if (Files.notExists(filePath)) {
            System.out.println("File " + filePath + " doesn't exists.");
            return;
        }

        if (!Files.isWritable(filePath)) {
            System.out.println("File " + filePath + " is not writable.");
            return;
        }

        try (Writer fileWriter = new FileWriter(filePath.toFile(), false)) {
            StatefulBeanToCsv<StudyGroup> beanToCsv = new StatefulBeanToCsvBuilder<StudyGroup>(fileWriter)
                    .withSeparator(',')
                    .build();
            beanToCsv.write(new ArrayList<>(values));
            System.out.println("Collection has been saved successfully to " + filePath.getFileName());
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<StudyGroup> readString(String str) {
        try {
            return new CsvToBeanBuilder<>(new StringReader(str))
                    .withType(StudyGroup.class)
                    .build().parse().stream().map(q -> (StudyGroup) q).collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public String writeString(List<StudyGroup> values) {
        try (Writer writer = new StringWriter()) {
            StatefulBeanToCsv<StudyGroup> beanToCsv = new StatefulBeanToCsvBuilder<StudyGroup>(writer)
                    .withSeparator(',')
                    .build();
            beanToCsv.write(values.stream().toList());
            return writer.toString();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    @Override
    public void removeById(long id, Path filePath) {
        try (BufferedReader fileReader = new BufferedReader(new FileReader(filePath.toFile()));
             BufferedWriter fileWriter = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            while (fileReader.ready()) {
                final String line = fileReader.readLine();
                if (line.startsWith("\"" + id + "\"")) continue;
                fileWriter.write(line);
                fileWriter.write("\n");
            }
            fileWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
