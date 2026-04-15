import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class TripScheduleTest {
    private TripSchedule  tripSchedule;
    private Attraction a1;
    private Attraction a2;
    private Attraction a3;
    @BeforeEach
    void setUp() {
        tripSchedule = new TripSchedule();
        a1 = new Attraction("Muzeum Narodowe", "history", LocalTime.of(9, 0), LocalTime.of(20,0), 120);
        a2 = new Attraction("Wawel", "history", LocalTime.of(10, 0), LocalTime.of(22,0), 45);
        a3 = new Attraction("Kino", "entertainment", LocalTime.of(9, 0), LocalTime.of(20,0), 100);
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