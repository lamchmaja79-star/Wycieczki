import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripScheduleTest {
    private TripSchedule  tripSchedule;
    List<Categories> category1;
    List<Categories> category2;
    private Attraction a1;
    private Attraction a2;
    private Attraction a3;
    @BeforeEach
    void setUp() {
        tripSchedule = new TripSchedule();
        category1 = new ArrayList<>();
        category2 = new ArrayList<>();
        category1.add(Categories.HISTORY);
        category2.add(Categories.ENTERTAINMENT);
        a1 = new Attraction("Muzeum Narodowe", category1, LocalTime.of(9, 0), LocalTime.of(20,0), 120, new Location(1,2));
        a2 = new Attraction("Wawel", category1, LocalTime.of(10, 0), LocalTime.of(22,0), 45, new Location(1,1));
        a3 = new Attraction("Kino", category2, LocalTime.of(9, 0), LocalTime.of(20,0), 100,  new Location(7,12));
    }
    @Test
    void constructor() {
        assertNotNull(tripSchedule.getSelectedAttractions(), "Konstruktor powinien tworzyć listę");
        assertTrue(tripSchedule.getSelectedAttractions().isEmpty(), "Konstruktor powinien tworzyć pustą listę");

    }
    @Test
    void addToPlan() {
        tripSchedule.addToPlan(a1);
        assertEquals(1, tripSchedule.getSelectedAttractions().size());
        assertTrue(tripSchedule.getSelectedAttractions().contains(a1));
        tripSchedule.addToPlan(a1);
        assertEquals(1, tripSchedule.getSelectedAttractions().size());
        tripSchedule.addToPlan(a2);
        assertEquals(2, tripSchedule.getSelectedAttractions().size());
        assertTrue(tripSchedule.getSelectedAttractions().contains(a2) && tripSchedule.getSelectedAttractions().contains(a1));
    }
    @Test
    void getSelectedAttractions() {
        tripSchedule.addToPlan(a1);
        tripSchedule.addToPlan(a2);
        tripSchedule.addToPlan(a3);
        assertEquals(3, tripSchedule.getSelectedAttractions().size());
        assertTrue(tripSchedule.getSelectedAttractions().contains(a1));
        assertTrue(tripSchedule.getSelectedAttractions().contains(a2));
        assertTrue(tripSchedule.getSelectedAttractions().contains(a3));
    }
}