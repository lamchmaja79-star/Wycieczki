import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {
    @BeforeEach
    void setUp() {
        DataManager.attractions.clear();
    }

    @Test
    void dataManagerTest() {
        DataManager.data();

        assertFalse(DataManager.attractions.isEmpty());
    }



}