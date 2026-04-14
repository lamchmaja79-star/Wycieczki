import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripPlannerTest {
    private List<Attraction> allAttractions;
    @BeforeEach
    void setUp() {
        Attraction a1 = new Attraction("Muzeum Narodowe", "history", LocalTime.of(9, 0), LocalTime.of(20,0), 120);
        Attraction a2 = new Attraction("Wawel", "history", LocalTime.of(10, 0), LocalTime.of(22,0), 45);
        Attraction a3 = new Attraction("Kino", "entertainment", LocalTime.of(9, 0), LocalTime.of(20,0), 100);
        allAttractions = new ArrayList<>();
        allAttractions.add(a1);
        allAttractions.add(a2);
        allAttractions.add(a3);
        List<Attraction> empty =  new ArrayList<>();
    }
    @Test
    void constructor() {
        TripPlanner tripPlanner = new TripPlanner();

    }
}