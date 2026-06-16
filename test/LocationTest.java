import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationTest {
    Location location;

    @BeforeEach
    void setUp() {
        location = new Location(10,11);
    }

    @Test
    void getX() {
        Assertions.assertEquals(10, location.getX(), 0.001);
    }

    @Test
    void getY() {
        Assertions.assertEquals(11, location.getY(), 0.001);
    }

    @Test
    void setX() {
        location.setX(100);
        Assertions.assertEquals(100, location.getX(), 0.001);
    }

    @Test
    void setY() {
        location.setY(110);
        Assertions.assertEquals(110, location.getY(), 0.001);
    }

    @Test
    void getDistance() {
        Location t1 = new Location(0,0);
        Location t2 = new Location(10,10);
        Assertions.assertEquals(20, t1.getDistance(t2), 0.001);
    }

    @Test
    void getDistanceNegative() {
        Location t1 = new Location(-4,-18);
        Location t2 = new Location(10,10);
        Assertions.assertEquals(42, t1.getDistance(t2), 0.001);
    }

    @Test
    void getDistanceToSelf() {
        Location t1 = new Location(10,10);
        Assertions.assertEquals(0, t1.getDistance(t1), 0.001);
    }

    @Test
    void travelTimeDriving() {
        Location start = new Location(0, 0);
        Location end = new Location(15, 15);
        Assertions.assertEquals(16, start.travelTime(end));
    }

    @Test
    void travelTimeAtBoundary() {
        Location start = new Location(0, 0);
        Location end = new Location(10, 10);
        Assertions.assertEquals(30, start.travelTime(end));
    }

    @Test
    void testToString() {
        Location loc = new Location(50.06, -19.94);
        Assertions.assertEquals("(50.06,-19.94)", loc.toString());
    }
}