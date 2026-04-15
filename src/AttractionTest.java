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
        assertEquals("ENTERTAINMENT: Kino Kijów, open: 09:00, closed: 22:00, czas trwania: 2 min", result);
    }

    @Test
    void testEquals() {
        //takie same
        Attraction attraction_copy = new Attraction("Kino Kijów", "entertainment", LocalTime.of(9,0),LocalTime.of(22,0), 2);
        assertEquals(attraction, attraction_copy);
        //różne nazwy
        Attraction a1 = new Attraction("Kino", "entertainment", LocalTime.of(9,0),LocalTime.of(22,0), 2);
        assertNotEquals(a1, attraction, "equals should return false for different names");
        //różne kategorie
        Attraction a2 = new Attraction("Kino Kijów", "history", LocalTime.of(9,0),LocalTime.of(22,0), 2);
        assertNotEquals(a2, attraction, "equals should return false for different categories");
        //różne czasy otwarcia
        Attraction a3 = new Attraction("Kino Kijów", "entertainment", LocalTime.of(9,30),LocalTime.of(22,0), 2);
        assertNotEquals(a3, attraction, "equals should return false for different opening times");
        //różne czasy zamknięcia
        Attraction a4 = new Attraction("Kino Kijów", "entertainment", LocalTime.of(9,0),LocalTime.of(20,0), 2);
        assertNotEquals(a4, attraction, "equals should return false for different closing times");
        //różny czas trwania
        Attraction a5 = new Attraction("Kino Kijów", "entertainment", LocalTime.of(9,30),LocalTime.of(22,0), 200);
        assertNotEquals(a5, attraction, "equals should return false for different durations");
    }

    @Test
    void testHashCode() {
        Attraction attraction_copy = new Attraction("Kino Kijów", "entertainment", LocalTime.of(9,0),LocalTime.of(22,0), 2);
        assertEquals(attraction.hashCode(), attraction_copy.hashCode());
        Attraction a1 = new Attraction("Muzeum Narodowe", "history", LocalTime.of(9, 0), LocalTime.of(20,0), 120);
        assertNotEquals(attraction.hashCode(), a1.hashCode());
    }
}