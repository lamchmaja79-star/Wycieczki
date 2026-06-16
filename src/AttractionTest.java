import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AttractionTest {
    private Attraction attraction;
    List<Categories> categoryList;
    Location location;

    @BeforeEach
    void setUp() {
        categoryList = new ArrayList<>();
        categoryList.add(Categories.ROZRYWKA);
        location = new Location(10,11);

        attraction = new Attraction("Kino Kijów", categoryList, LocalTime.of(9,0),LocalTime.of(22,0), 2, location);
    }

    @Test
    void getName() {
        assertEquals("Kino Kijów", attraction.getName());
    }

    @Test
    void getCategoryList() {
        assertEquals(categoryList , attraction.getCategoryList());
        assertEquals(1, attraction.getCategoryList().size());
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
    public void getLocation() { assertEquals(location, attraction.getLocation()); }

    @Test
    void testToString() {
        String result = attraction.toString();
        assertEquals("[ROZRYWKA]: Kino Kijów, open: 09:00, closed: 22:00, czas trwania: 2 min", result);
    }

    @Test
    void testEquals() {
        //takie same
        Attraction attraction_copy = new Attraction("Kino Kijów", categoryList, LocalTime.of(9,0),LocalTime.of(22,0), 2, location);
        assertEquals(attraction, attraction_copy);
        //różne nazwy
        Attraction a1 = new Attraction("Kino", categoryList , LocalTime.of(9,0),LocalTime.of(22,0), 2,location);
        assertNotEquals(a1, attraction, "equals should return false for different names");
        //różne kategorie
        List<Categories> category2 =  new ArrayList<>();
        category2.add(Categories.HISTORIA);
        Attraction a2 = new Attraction("Kino Kijów", category2, LocalTime.of(9,0),LocalTime.of(22,0), 2,location);
        assertNotEquals(a2, attraction, "equals should return false for different categories");
        //różne czasy otwarcia
        Attraction a3 = new Attraction("Kino Kijów",categoryList, LocalTime.of(9,30),LocalTime.of(22,0), 2, location);
        assertNotEquals(a3, attraction, "equals should return false for different opening times");
        //różne czasy zamknięcia
        Attraction a4 = new Attraction("Kino Kijów", categoryList, LocalTime.of(9,0),LocalTime.of(20,0), 2, location);
        assertNotEquals(a4, attraction, "equals should return false for different closing times");
        //różny czas trwania
        Attraction a5 = new Attraction("Kino Kijów", categoryList, LocalTime.of(9,30),LocalTime.of(22,0), 200, location);
        assertNotEquals(a5, attraction, "equals should return false for different durations");
    }

    @Test
    void testHashCode() {
        Attraction attraction_copy = new Attraction("Kino Kijów", categoryList, LocalTime.of(9,0),LocalTime.of(22,0), 2, location);
        assertEquals(attraction.hashCode(), attraction_copy.hashCode());
        Attraction a1 = new Attraction("Muzeum Narodowe", categoryList, LocalTime.of(9, 0), LocalTime.of(20,0), 120, location);
        assertNotEquals(attraction.hashCode(), a1.hashCode());
    }

    @Test
    void isOpen(){
        assertTrue(attraction.isOpen(LocalTime.of(10, 0)));
        assertFalse(attraction.isOpen(LocalTime.of(21, 59)));
        assertFalse(attraction.isOpen(LocalTime.of(23, 0)));
    }
}