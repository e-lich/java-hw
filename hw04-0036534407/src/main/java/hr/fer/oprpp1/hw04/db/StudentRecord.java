package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class that represents a single student record.
 */
public class StudentRecord {
    private final String jmbag;
    private final String lastName;
    private final String firstName;
    private final int finalGrade;

    /**
     * @return the jmbag
     */
    public String getJmbag() {
        return jmbag;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the finalGrade
     */
    public int getFinalGrade() {
        return finalGrade;
    }

    /**
     * @param jmbag the jmbag of new student record
     * @param lastName the last name of new student record
     * @param firstName the first name of new student record
     * @param finalGrade the final grade of new student record
     */
    public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
        this.jmbag = jmbag;
        this.lastName = lastName;
        this.firstName = firstName;
        this.finalGrade = finalGrade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRecord that = (StudentRecord) o;
        return jmbag.equals(that.jmbag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbag);
    }
}
