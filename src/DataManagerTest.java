import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataManagerTest {
    @BeforeEach
    void setUp() {
        DataManager.attractions.clear();
    }

    @Test
    void dataManagerTest() {
        DataManager.data();

        Assertions.assertFalse(DataManager.attractions.isEmpty());
    }



}