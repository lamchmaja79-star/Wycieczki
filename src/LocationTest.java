import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

class LocationTest {
    Location location;

    @BeforeEach
    void setUp() {
        location = new Location(10,11);
    }

    @org.junit.jupiter.api.Test
    void getX() {
        location.setX(10);
        Assertions.assertEquals(10,location.getX());
    }

    @org.junit.jupiter.api.Test
    void getY() {
        location.setY(11);
        Assertions.assertEquals(11, location.getY());
    }

    @org.junit.jupiter.api.Test
    void setX() {
        location.setX(100);
        Assertions.assertEquals(100,location.getX());
    }

    @org.junit.jupiter.api.Test
    void setY() {
        location.setY(110);
        Assertions.assertEquals(110,location.getY());
    }

    @org.junit.jupiter.api.Test
    void getDistance() {
        Location t1 = new Location(0,0);
        Location t2 = new Location(10,10);
        Assertions.assertEquals(10, t1.getDistance(t2), 0.001);
    }
}