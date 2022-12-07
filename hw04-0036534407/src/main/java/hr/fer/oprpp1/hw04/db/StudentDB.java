package hr.fer.oprpp1.hw04.db;

import hr.fer.oprpp1.hw04.db.parser.QueryParser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class that represents a database of students which can be queried.
 */
public class StudentDB {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(
                Paths.get("src/main/resources/database.txt"),
                StandardCharsets.UTF_8
        );

        StudentDatabase db = new StudentDatabase(lines);

        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();

        while (!line.equalsIgnoreCase("exit")) {
            if (line.startsWith("query ")) {
                String query = line.substring(6);
                int index = 0;
                StringBuilder dbQuery = new StringBuilder();
                String[] fields = null;

                while (index < query.length()) {
                    if (query.charAt(index) == 's') {
                        if (index + 6 < query.length() && query.startsWith("showing", index)) {
                            if (index + 8 < query.length()) {
                                fields = query.substring(index + 8).split(", ");
                            } else {
                                fields = new String[0];
                            }
                            break;
                        }
                    }
                    dbQuery.append(query.charAt(index++));
                }

                List<StudentRecord> records = query(db, dbQuery.toString());
                printRecords(records, fields);
                System.out.println("Records selected: " + records.size());
            } else {
                System.out.println("Invalid query");
                line = sc.nextLine();
                continue;
            }

            line = sc.nextLine();
        }

        System.out.println("Goodbye!");
    }

    /**
     * @param db database to query
     * @param query query to execute
     * @return list of records that match the query
     */
    public static List<StudentRecord> query(StudentDatabase db, String query) {
        List<StudentRecord> records;
        QueryParser parser = new QueryParser(query);

        if (parser.isDirectQuery()) {
            StudentRecord record = db.forJMBAG(parser.getQueriedJMBAG());

            records = new ArrayList<>();
            records.add(record);
        } else {
            records = db.filter(new QueryFilter(parser.getQuery()));
        }

        return records;
    }

    /**
     * Prints the given list of records in a formatted way.
     * @param records list of records to print
     */
    public static void printRecords (List<StudentRecord> records, String[] fields) {
        int maxLastNameLength = 0;
        int maxFirstNameLength = 0;
        int maxJmbagLength = 0;

        if (records.size() > 0) {
            for (StudentRecord record : records) {
                if (record.getLastName().length() > maxLastNameLength) {
                    maxLastNameLength = record.getLastName().length();
                }
                if (record.getFirstName().length() > maxFirstNameLength) {
                    maxFirstNameLength = record.getFirstName().length();
                }
                if (record.getJmbag().length() > maxJmbagLength) {
                    maxJmbagLength = record.getJmbag().length();
                }
            }

            if (fields == null) {
                String divider = "+" + "-".repeat(maxJmbagLength + 2)
                        + "+" + "-".repeat(maxLastNameLength + 2)
                        + "+" + "-".repeat(maxFirstNameLength + 2)
                        + "+" + "-".repeat(3) + "+";

                System.out.println(divider);

                for (StudentRecord record : records) {
                    System.out.println("| " + record.getJmbag()
                            + " ".repeat(maxJmbagLength - record.getJmbag().length() + 1)
                            + "| " + record.getLastName()
                            + " ".repeat(maxLastNameLength - record.getLastName().length() + 1)
                            + "| " + record.getFirstName()
                            + " ".repeat(maxFirstNameLength - record.getFirstName().length() + 1)
                            + "| " + record.getFinalGrade() + " |");
                }

                System.out.println(divider);
            } else if (fields.length > 0) {
                StringBuilder divider = new StringBuilder();
                boolean invalidField = false;
                for (String field : fields) {
                    switch (field) {
                        case "lastName":
                            divider.append("+").append("-".repeat(maxLastNameLength + 2));
                            break;
                        case "firstName":
                            divider.append("+").append("-".repeat(maxFirstNameLength + 2));
                            break;
                        case "jmbag":
                            divider.append("+").append("-".repeat(maxJmbagLength + 2));
                            break;
                        case "finalGrade":
                            divider.append("+").append("-".repeat(3));
                            break;
                        default:
                            invalidField = true;
                            break;
                    }
                }

                if (invalidField) {
                    System.err.println("Invalid field!");
                    return;
                }

                divider.append("+");

                System.out.println(divider);

                for (StudentRecord record : records) {
                    StringBuilder output = new StringBuilder();

                    for (String field : fields) {
                        if (field.equals("lastName")) {
                            output.append("| ")
                                    .append(record.getLastName())
                                    .append(" ".repeat(maxLastNameLength - record.getLastName().length() + 1));
                        }
                        if (field.equals("firstName")) {
                            output.append("| ")
                                    .append(record.getFirstName())
                                    .append(" ".repeat(maxFirstNameLength - record.getFirstName().length() + 1));
                        }
                        if (field.equals("jmbag")) {
                            output.append("| ")
                                    .append(record.getJmbag())
                                    .append(" ".repeat(maxJmbagLength - record.getJmbag().length() + 1));
                        }
                        if (field.equals("finalGrade")) {
                            output.append("| ")
                                    .append(record.getFinalGrade())
                                    .append(" ");
                        }
                    }
                    output.append(" |");
                    System.out.println(output);
                }
                System.out.println(divider);
            }
        }
    }
}
