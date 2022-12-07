package hr.fer.oprpp1.scripting.elems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ElemsTests {

    @Test
    public void elementAsTextTest() {
        Element element = new Element();

        assertEquals(element.asText(), "");
    }
}
