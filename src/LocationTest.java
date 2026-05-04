import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    Location location;

    @BeforeEach
    void setUp() {
        location = new Location(10,11);
    }

    @org.junit.jupiter.api.Test
    void getX() {
        location.setX(10);
        assertEquals(10,location.getX());
    }

    @org.junit.jupiter.api.Test
    void getY() {
        location.setY(11);
        assertEquals(11, location.getY());
    }

    @org.junit.jupiter.api.Test
    void setX() {
        location.setX(100);
        assertEquals(100,location.getX());
    }

    @org.junit.jupiter.api.Test
    void setY() {
        location.setY(110);
        assertEquals(110,location.getY());
    }

    @org.junit.jupiter.api.Test
    void getDistance() {
        Location t1 = new Location(0,0);
        Location t2 = new Location(10,10);
        assertEquals(10, t1.getDistance(t2), 0.001);
    }
}