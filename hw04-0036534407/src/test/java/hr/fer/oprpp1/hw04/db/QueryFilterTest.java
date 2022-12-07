package hr.fer.oprpp1.hw04.db;

import hr.fer.oprpp1.hw04.db.parser.QueryParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QueryFilterTest {

    @Test
    public void testAccepts() {
        QueryParser parser = new QueryParser("jmbag=\"0000000003\"");
        QueryFilter filter = new QueryFilter(parser.getQuery());

        StudentRecord record1 = new StudentRecord("0000000003", "Kokolo", "Marta", 5);
        StudentRecord record2 = new StudentRecord("0000000004", "Kokolo", "Marta", 5);

        assertTrue(filter.accepts(record1));
        assertFalse(filter.accepts(record2));
    }
}
