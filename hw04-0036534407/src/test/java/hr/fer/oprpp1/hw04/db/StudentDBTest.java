package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class StudentDBTest {

    @Test
    public void testDirectQuery() throws IOException {
        List<String> lines = Files.readAllLines(
                Paths.get("src/main/resources/database.txt"),
                StandardCharsets.UTF_8
        );

        StudentDatabase db = new StudentDatabase(lines);

        List<StudentRecord> records = StudentDB.query(db, "jmbag=\"0000000003\"");

        assertTrue(records.size() == 1 &&
                records.get(0).getJmbag().equals("0000000003") &&
                records.get(0).getFirstName().equals("Andrea") &&
                records.get(0).getLastName().equals("Bosnić") &&
                records.get(0).getFinalGrade() == 4);

    }

    @Test
    public void testComplexQuery() throws IOException {
        List<String> lines = Files.readAllLines(
                Paths.get("src/main/resources/database.txt"),
                StandardCharsets.UTF_8
        );

        StudentDatabase db = new StudentDatabase(lines);

        List<StudentRecord> records = StudentDB.query(db, "firstName>\"J\" " +
                "and lastName LIKE \"Br*\" ");

        assertTrue(records.size() == 1 &&
                records.get(0).getJmbag().equals("0000000005") &&
                records.get(0).getFirstName().equals("Jusufadis") &&
                records.get(0).getLastName().equals("Brezović") &&
                records.get(0).getFinalGrade() == 2);
    }

    @Test
    public void testPrintRecords() {
        List<StudentRecord> records = new ArrayList<>();

        records.add(new StudentRecord("0000000006", "Finderle", "Ruben", 5));

        String expected = "+------------+----------+-------+---+\n" +
                "| 0000000006 | Finderle | Ruben | 5 |\n" +
                "+------------+----------+-------+---+\n";

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        StudentDB.printRecords(records, new String[]{"jmbag", "lastName", "firstName", "finalGrade"});

        assertEquals(expected, outputStream.toString());
    }

    @Test
    public void testMainDB() throws IOException {
        String expected1 = "Using index for record retrieval.\n" +
                "+------------+-----------+-------+---+\n" +
                "| 0000000001 | Akšamović | Marin | 2 |\n" +
                "+------------+-----------+-------+---+\n" +
                "Records selected: 1\n";

        String expected2 = "+------------+------------+----------+---+\n" +
                "| 0000000021 | Jakobušić  | Antonija | 2 |\n" +
                "| 0000000003 | Bosnić     | Andrea   | 4 |\n" +
                "| 0000000050 | Sikirica   | Alen     | 3 |\n" +
                "| 0000000051 | Skočir     | Andro    | 4 |\n" +
                "| 0000000036 | Markić     | Ante     | 5 |\n" +
                "| 0000000023 | Kalvarešin | Ana      | 4 |\n" +
                "+------------+------------+----------+---+\n" +
                "Records selected: 6\n";

        String expected3 = "Invalid query\n";

        String expected4 = "Goodbye!\n";

        String userInput = String.format("query jmbag = \"0000000001\"%s" +
                        "query firstName LIKE \"A*\"%s" +
                        "update%s" +
                        "exit%s",
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator(),
                System.lineSeparator());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);

        StudentDB.main(new String[0]);

        assertEquals(expected1 + expected2 + expected3 + expected4, outputStream.toString());
    }
}
