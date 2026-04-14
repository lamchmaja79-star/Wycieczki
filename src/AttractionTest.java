import org.junit.jupiter.api.BeforeEach;
import org.w3c.dom.Attr;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AttractionTest {

    @BeforeEach
    void setUp() {
        Attraction attraction = new Attraction("Kino Kijów", "entertainment", LocalTime.of(9,0),LocalTime);
    }

    @org.junit.jupiter.api.Test
    void getName() {
    }

    @org.junit.jupiter.api.Test
    void getCategory() {
    }

    @org.junit.jupiter.api.Test
    void getOpen() {
    }

    @org.junit.jupiter.api.Test
    void getClosed() {

    }

    @org.junit.jupiter.api.Test
    void getDurationMinutes() {

    }

    @org.junit.jupiter.api.Test
    void testToString() {

    }
}