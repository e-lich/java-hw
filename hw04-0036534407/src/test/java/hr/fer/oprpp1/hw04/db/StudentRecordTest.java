package hr.fer.oprpp1.hw04.db;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentRecordTest {

    @Test
    public void testGetters() {
        StudentRecord record = new StudentRecord("0000000001", "Bosnić", "Ivan", 5);
        assert record.getJmbag().equals("0000000001");
        assert record.getLastName().equals("Bosnić");
        assert record.getFirstName().equals("Ivan");
        assert record.getFinalGrade() == 5;
    }

    @Test
    public void testEquals() {
        StudentRecord record1 = new StudentRecord("0000000001", "Bosnić", "Ivan", 5);
        StudentRecord record2 = new StudentRecord("0000000001", "Bosnić", "Marta", 5);
        StudentRecord record3 = new StudentRecord("0000000002", "Bosnić", "Ivan", 5);
        assertEquals(record1, record2);
        assertNotEquals(record1, record3);
    }

    @Test
    public void testHashCode() {
        StudentRecord record1 = new StudentRecord("0000000001", "Bosnić", "Ivan", 5);
        StudentRecord record2 = new StudentRecord("0000000001", "Bosnić", "Marta", 5);
        StudentRecord record3 = new StudentRecord("0000000002", "Bosnić", "Ivan", 5);

        assertEquals(record1.hashCode(), record2.hashCode());
        assertNotEquals(record1.hashCode(), record3.hashCode());
    }
}
