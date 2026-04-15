import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripPlannerTest {
    private TripPlanner tripPlanner;
    private Attraction a1;
    private Attraction a2;
    private Attraction a3;
    @BeforeEach
    void setUp() {
        tripPlanner = new TripPlanner();
        a1 = new Attraction("Muzeum Narodowe", "history", LocalTime.of(9, 0), LocalTime.of(20,0), 120);
        a2 = new Attraction("Wawel", "history", LocalTime.of(10, 0), LocalTime.of(22,0), 45);
        a3 = new Attraction("Kino", "entertainment", LocalTime.of(9, 0), LocalTime.of(20,0), 100);
    }
    @Test
    void constructor() {
        assertNotNull(tripPlanner.getAllAttractions(), "Konstruktor powinien tworzyć listę");
        assertTrue(tripPlanner.getAllAttractions().isEmpty(), "Konstruktor powinien tworzyć pustą listę");
    }

    @Test
    void addAttraction_shouldNotAddDuplicate() {
        tripPlanner.addAttraction(a1);
        assertEquals(1, tripPlanner.getAllAttractions().size());
        assertTrue(tripPlanner.getAllAttractions().contains(a1));
        tripPlanner.addAttraction(a1);
        assertEquals(1, tripPlanner.getAllAttractions().size());
    }

    @Test
    void getAllAttractions() {
        tripPlanner.addAttraction(a1);
        tripPlanner.addAttraction(a2);
        assertEquals(2, tripPlanner.getAllAttractions().size());
        assertTrue(tripPlanner.getAllAttractions().contains(a1));
        assertTrue(tripPlanner.getAllAttractions().contains(a2));
    }

    @Test
    void filterByCategory_foundMatch(){
        tripPlanner.addAttraction(a1);
        tripPlanner.addAttraction(a2);
        tripPlanner.addAttraction(a3);
        List<Attraction> result = tripPlanner.filterByCategory("history");
        assertEquals(2, result.size());
        assertTrue(result.contains(a1));
        assertTrue(result.contains(a2));
        assertFalse(result.contains(a3));
    }

    @Test
    void filterByCategory_notFoundMatch(){
        tripPlanner.addAttraction(a1);
        tripPlanner.addAttraction(a2);
        List<Attraction> result = tripPlanner.filterByCategory("entertainment");
        assertTrue(result.isEmpty());
    }
}
