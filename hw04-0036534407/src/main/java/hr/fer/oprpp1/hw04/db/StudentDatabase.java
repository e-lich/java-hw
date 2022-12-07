package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that represents a database of student records.
 */
public class StudentDatabase {
    private HashMap<String, StudentRecord> database = new HashMap<>();

    /**
     * Constructor that creates a new database from a list of student records.
     * @param lines list of lines from the database file
     * @throws IllegalArgumentException if a student record is invalid
     */
    public StudentDatabase(List<String> lines) {
        for (String line: lines) {
            String[] record = line.split("\t");

            if (database.containsKey(record[0])) {
                System.out.println("Given jmbag already exists in the database.");
                throw new IllegalArgumentException();
            }

            int grade = Integer.parseInt(record[3]);
            if (grade < 1 || grade > 5) {
                System.out.println("Given final grade is not a number between 1 and 5.");
                throw new IllegalArgumentException();
            }

            database.put(record[0], new StudentRecord(record[0], record[1], record[2], grade));
        }
    }

    /**
     * @param jmbag jmbag of the student
     * @return student record with the given jmbag
     */
    public StudentRecord forJMBAG(String jmbag) {
        System.out.println("Using index for record retrieval.");
        return database.get(jmbag);
    }

    /**
     * @param filter filter that is used to filter the database
     * @return list of student records that satisfy the filter
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> acceptedRecords = new ArrayList<>();

        for (StudentRecord record: database.values()) {
            if (filter.accepts(record)) {
                acceptedRecords.add(record);
            }
        }

        return acceptedRecords;
    }
}
