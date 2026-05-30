import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripPlannerTest {
    private TripPlanner tripPlanner;
    private Attraction a1;
    private Attraction a2;
    private Attraction a3;
    List<Categories> category1;
    List<Categories> category2;
    @BeforeEach
    void setUp() {
        tripPlanner = new TripPlanner();
        category1 = new ArrayList<>();
        category2 = new ArrayList<>();
        category1.add(Categories.HISTORIA);
        category2.add(Categories.ROZRYWKA);
        a1 = new Attraction("Muzeum Narodowe", category1, LocalTime.of(9, 0), LocalTime.of(20,0), 120, new Location(1,1));
        a2 = new Attraction("Wawel", category1, LocalTime.of(10, 0), LocalTime.of(22,0), 45,  new Location(1,2));
        a3 = new Attraction("Kino", category2, LocalTime.of(9, 0), LocalTime.of(20,0), 100,  new Location(3,4));
    }
    @Test
    void constructor() {
        assertNotNull(tripPlanner.getAllAttractions(), "Konstruktor powinien tworzyć listę");
        assertTrue(tripPlanner.getAllAttractions().isEmpty(), "Konstruktor powinien tworzyć pustą listę");
    }

    @Test
    void addAttraction() {
        tripPlanner.addAttraction(a1);
        assertEquals(1, tripPlanner.getAllAttractions().size());
        assertTrue(tripPlanner.getAllAttractions().contains(a1));
        tripPlanner.addAttraction(a1);
        assertEquals(1, tripPlanner.getAllAttractions().size());
        tripPlanner.addAttraction(a2);
        assertEquals(2, tripPlanner.getAllAttractions().size());
        assertTrue(tripPlanner.getAllAttractions().contains(a1) && tripPlanner.getAllAttractions().contains(a2));
    }

    @Test
    void getAllAttractions() {
        tripPlanner.addAttraction(a1);
        tripPlanner.addAttraction(a2);
        tripPlanner.addAttraction(a3);
        assertEquals(3, tripPlanner.getAllAttractions().size());
        assertTrue(tripPlanner.getAllAttractions().contains(a1));
        assertTrue(tripPlanner.getAllAttractions().contains(a2));
        assertTrue(tripPlanner.getAllAttractions().contains(a3));
    }

    @Test
    void filterByCategory_foundMatch(){
        tripPlanner.addAttraction(a1);
        tripPlanner.addAttraction(a2);
        tripPlanner.addAttraction(a3);
        List<Attraction> result = tripPlanner.filterByCategory(Categories.HISTORIA);
        assertEquals(2, result.size());
        assertTrue(result.contains(a1));
        assertTrue(result.contains(a2));
        assertFalse(result.contains(a3));
    }

    @Test
    void filterByCategory_notFoundMatch(){
        tripPlanner.addAttraction(a1);
        tripPlanner.addAttraction(a2);
        List<Attraction> result = tripPlanner.filterByCategory(Categories.ROZRYWKA);
        assertTrue(result.isEmpty());
    }

}
