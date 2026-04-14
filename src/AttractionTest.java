import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Attr;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AttractionTest {
    private Attraction attraction;
    @BeforeEach
    void setUp() {
        attraction = new Attraction("Kino Kijów", "entertainment", LocalTime.of(9,0),LocalTime.of(22,0), 2);
    }

    @Test
    void getName() {
        assertEquals("Kino Kijów", attraction.getName());
    }

    @Test
    void getCategory() {
        assertEquals("entertainment", attraction.getCategory());
    }

    @Test
    void getOpen() {
        assertEquals(LocalTime.of(9,0), attraction.getOpen());
    }

    @Test
    void getClosed() {
        assertEquals(LocalTime.of(22,0), attraction.getClosed());
    }

    @Test
    void getDurationMinutes() {
        assertEquals(2, attraction.getDurationMinutes());
    }

    @Test
    void testToString() {
        String result = attraction.toString();

        assertTrue(result.contains("Kino Kijów"));
        assertTrue(result.contains("ENTERTAINMENT"));
    }

}