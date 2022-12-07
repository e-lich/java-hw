package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class StudentDatabaseTest {

    @Test
    public void testForJMBAG() throws IOException {
        List<String> lines = Files.readAllLines(
                Paths.get("/Users/elich/Documents/FER/sem5/java/00-workspace/hw04-0036534407/src/main/resources/database.txt"),
                StandardCharsets.UTF_8
        );

        StudentDatabase db = new StudentDatabase(lines);
        StudentRecord record = db.forJMBAG("0000000010");

        assertTrue(Objects.equals(record.getFirstName(), "Luka") &&
                Objects.equals(record.getLastName(), "Dokleja") &&
                Objects.equals(record.getFinalGrade(), 3) &&
                Objects.equals(record.getJmbag(), "0000000010"));
    }

    @Test
    public void testFilter() throws IOException {
        List<String> lines = new ArrayList<>();

        lines.add("0000000001\tAkšamović\tMarin\t2");
        lines.add("0000000002\tBakamović\tPetra\t3");

        StudentDatabase db = new StudentDatabase(lines);

        List<StudentRecord> filtered_db = db.filter(record -> true);
        List<String> filtered_db_strings = new ArrayList<>();

        for (StudentRecord record: filtered_db) {
            String string = record.getJmbag() + "\t" +
                    record.getLastName() + "\t" +
                    record.getFirstName() + "\t" +
                    record.getFinalGrade();
            filtered_db_strings.add(string);
        }

        List<StudentRecord> filtered_db_false = db.filter(record -> false);

        assertTrue(lines.get(0).equals(filtered_db_strings.get(0)) &&
                lines.get(1).equals(filtered_db_strings.get(1)) &&
                filtered_db_false.size() == 0);
    }

    @Test
    public void testJmbagAlreadyExists() throws IOException {
        List<String> lines = new ArrayList<>();

        lines.add("0000000001\tAkšamović\tMarin\t2");
        lines.add("0000000001\tBakamović\tPetra\t3");

        assertThrows(IllegalArgumentException.class, () -> new StudentDatabase(lines));
    }

    @Test
    public void testInvalidGrade() throws IOException {
        List<String> lines = new ArrayList<>();

        lines.add("0000000001\tAkšamović\tMarin\t2");
        lines.add("0000000002\tBakamović\tPetra\t6");

        assertThrows(IllegalArgumentException.class, () -> new StudentDatabase(lines));
    }
}
