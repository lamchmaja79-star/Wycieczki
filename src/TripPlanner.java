import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TripPlanner {
    private List<Attraction> allAttractions;

    public TripPlanner() {
        this.allAttractions = new ArrayList<>();
        allAttractions = DataManager.loadFromFile();
        if (allAttractions == null || allAttractions.isEmpty()) {
            System.out.println("Generowanie domyślnej bazy danych...");
            allAttractions = DataManager.data();
            DataManager.saveToFile(allAttractions);
        }
    }

    public void addAttraction(Attraction a) {
        if (!allAttractions.contains(a)) {
            allAttractions.add(a);
        }
    }

    public List<Attraction> getAllAttractions() {
        return allAttractions;
    }

    public List<Attraction> filterByCategory(Categories category) {
        List<Attraction> filtered = new ArrayList<>();
        for (Attraction a : allAttractions) {
            if(a.getCategoryList().contains(category)) {
                filtered.add(a);
            }
        }
        return filtered;
    }

    public List<Attraction> sortByDuration() {
        List<Attraction> filtered = new ArrayList<>(allAttractions);
        filtered.sort(Comparator.comparingInt(Attraction::getDurationMinutes));
    return filtered;
    }






}
