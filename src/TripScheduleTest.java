import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripScheduleTest {
    private TripSchedule tripSchedule;
    List<Categories> category1;
    List<Categories> category2;
    private Attraction a1;
    private Attraction a2;
    private Attraction a3;
    private Attraction a4;

    @BeforeEach
    void setUp() {
        tripSchedule = new TripSchedule(LocalTime.of(8,0), LocalTime.of(18,30), new Location(0,0));
        category1 = new ArrayList<>();
        category2 = new ArrayList<>();
        category1.add(Categories.HISTORY);
        category2.add(Categories.ENTERTAINMENT);
        a1 = new Attraction("Muzeum Narodowe", category1, LocalTime.of(9, 0), LocalTime.of(20, 0), 90, new Location(1, 2));
        a2 = new Attraction("Wawel", category1, LocalTime.of(10, 0), LocalTime.of(22, 0), 120, new Location(1, 1));
        a3 = new Attraction("Kino", category2, LocalTime.of(9, 0), LocalTime.of(20, 0), 50, new Location(7, 12));
        a4 = new Attraction("Daleka Atrakcja", category2, LocalTime.of(9, 0), LocalTime.of(20, 0), 50, new Location(500, 500));
    }

    @Test
    void constructor() {
        assertNotNull(tripSchedule.getSelectedAttractions(), "Konstruktor powinien tworzyć listę");
        assertTrue(tripSchedule.getSelectedAttractions().isEmpty(), "Konstruktor powinien tworzyć pustą listę");
        assertThrows(IllegalArgumentException.class, () -> {new TripSchedule(LocalTime.of(15, 0), LocalTime.of(10, 0), new Location(0,0));}, "Konstruktor powinien zwracać Illegal ArgumentException gdy startTime jest po endTime");
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
    @Test
    void testGetWaitingTime() {
        LocalTime arrivalTime = LocalTime.of(8, 30);
        int wait = tripSchedule.getWaitingTime(a1, arrivalTime);
        assertEquals(30, wait, "Czas oczekiwania powinien wynosić 30 minut");
        assertEquals(0, tripSchedule.getWaitingTime(a1, LocalTime.of(10, 0)));
    }

    @Test
    void testVerify() {
        tripSchedule.setEndTime(LocalTime.of(10, 0));
        assertFalse(tripSchedule.verify(new Location(0,0), a1, LocalTime.of(9,30)));
    }
    @Test
    void testCreateSchedule_ShouldSkipUnreachableAttractions() {
        // Dodajemy atrakcję, która jest fizycznie za daleko, by zdążyć w oknie czasowym
        tripSchedule.addToPlan(a4);

        List<Attraction> result = tripSchedule.createSchedule();

        assertTrue(result.isEmpty(), "Harmonogram powinien być pusty, jeśli atrakcja jest nieosiągalna");
    }

    @Test
    void createSchedual() {
        tripSchedule.addToPlan(a1);
        tripSchedule.addToPlan(a2);

        List<Attraction> result = tripSchedule.createSchedule();
        assertTrue(result.contains(a1) || result.contains(a2));
        assertFalse(result.isEmpty());
    }

    @Test
    void deleteAttraction() {
        tripSchedule.addToPlan(a1);
        tripSchedule.deleteAttraction(a1);
        assertEquals(0, tripSchedule.getSelectedAttractions().size());
    }

}