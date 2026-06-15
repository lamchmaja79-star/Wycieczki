import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class TripPlanner
 * Provides functionality for managing and organizing attractions, including filtering, sorting, and loading default data if needed.
 *
 * @author Karolina Kolarz
 * @author Maja Lamch
 * @version 1.0
 */
public class TripPlanner {
    private List<Attraction> allAttractions;

    /**
     * Creates a TripPlanner instance and loads attractions from file.
     * If file data is empty or missing, default data is generated.
     */
    public TripPlanner() {
        this.allAttractions = new ArrayList<>();
        allAttractions = DataManager.loadFromFile();
        if (allAttractions == null || allAttractions.isEmpty()) {
            System.out.println("Generowanie domyślnej bazy danych...");
            allAttractions = DataManager.data();
            DataManager.saveToFile(allAttractions);
        }
    }

    /**
     * Adds a new attraction if it does not already exist in the list.
     *
     * @param a attraction to add
     */
    public void addAttraction(Attraction a) {
        if (!allAttractions.contains(a)) {
            allAttractions.add(a);
        }
    }

    /**
     * Returns all stored attractions.
     *
     * @return list of all attractions
     */
    public List<Attraction> getAllAttractions() {
        return allAttractions;
    }

    /**
     * Filters attractions by category.
     *
     * @param category category to filter by
     * @return list of attractions matching the category
     */
    public List<Attraction> filterByCategory(Categories category) {
        List<Attraction> filtered = new ArrayList<>();
        for (Attraction a : allAttractions) {
            if(a.getCategoryList().contains(category)) {
                filtered.add(a);
            }
        }
        return filtered;
    }

    /**
     * Sorts attractions by visit duration in ascending order.
     *
     * @return sorted list of attractions
     */
    public List<Attraction> sortByDuration() {
        List<Attraction> filtered = new ArrayList<>(allAttractions);
        filtered.sort(Comparator.comparingInt(Attraction::getDurationMinutes));
    return filtered;
    }


}
