import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {
    private static final File TARGET_FILE = new File("atrakcje.ser");
    private static File backupFile;

    @BeforeAll
    static void backupOriginalFile() throws IOException {
        // Przed uruchomieniem testów robimy kopię zapasową prawdziwego pliku, żeby go nie nadpisać
        if (TARGET_FILE.exists()) {
            backupFile = File.createTempFile("atrakcje_backup", ".ser");
            Files.copy(TARGET_FILE.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @AfterAll
    static void restoreOriginalFile() throws IOException {
        // Po zakończeniu wszystkich testów przywracamy oryginalny plik użytkownika
        if (backupFile != null && backupFile.exists()) {
            Files.copy(backupFile.toPath(), TARGET_FILE.toPath(), StandardCopyOption.REPLACE_EXISTING);
            backupFile.delete();
        } else {
            // Jeśli plik nie istniał przed testami, usuwamy plik stworzony przez testy, żeby nie śmiecić
            Files.deleteIfExists(TARGET_FILE.toPath());
        }
    }

    @BeforeEach
    void setUp() {
        DataManager.attractions.clear();
    }

    @Test
    void testGetFileName() {
        assertEquals("atrakcje.ser", DataManager.getFileName(), "File name should match the expected default string.");
    }

    @Test
    void testDataisCorrect(){
        List<Attraction> result = DataManager.data();
        assertEquals(28, result.size(), "Should generate exactly 28 sample attractions.");
        assertEquals(28, DataManager.attractions.size(), "Static global list size should match the returned list size.");

        Attraction wawel = result.get(0);
        assertEquals("Zamek Królewski na Wawelu", wawel.getName());
        assertEquals(120, wawel.getDurationMinutes());
        assertTrue(wawel.getCategoryList().contains(Categories.HISTORIA), "Wawel should have HISTORIA category.");

        Attraction zakrzowek = result.get(14);
        assertEquals("Zalew Zakrzówek", zakrzowek.getName());
        assertTrue(zakrzowek.getCategoryList().contains(Categories.REKREACJA));
    }

    @Test
    void testSaveAndLoadFromFileIntegration() {
        // Przygotowujemy kontrolowaną, małą listę obiektów do testu serializacji
        List<Attraction> testList = new ArrayList<>();
        List<Categories> categories = List.of(Categories.ROZRYWKA);
        Location testLocation = new Location(50.0, 20.0);
        testList.add(new Attraction("Test Room", categories, LocalTime.of(10, 0), LocalTime.of(18, 0), 60, testLocation));

        DataManager.saveToFile(testList);

        List<Attraction> loadedList = DataManager.loadFromFile();

        assertNotNull(loadedList, "Loaded list should not be null.");
        assertEquals(1, loadedList.size(), "Loaded list should contain exactly 1 attraction.");

        Attraction loadedAttraction = loadedList.getFirst();
        assertEquals("Test Room", loadedAttraction.getName());
        assertEquals(60, loadedAttraction.getDurationMinutes());
    }

    @Test
    void testLoadFromFileWhenFileDoesNotExist() {
        if (TARGET_FILE.exists()) {
            assertTrue(TARGET_FILE.delete(), "Should be able to delete the temporary test file.");
        }

        List<Attraction> result = DataManager.loadFromFile();

        assertNotNull(result, "Method should return an empty list object instead of null when file is missing.");
        assertTrue(result.isEmpty(), "Returned list should be completely empty.");
    }
}