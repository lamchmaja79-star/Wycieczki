import java.util.ArrayList;
import java.util.List;

public class TripPlanner {
    private List<Attraction> allAttractions;

    public TripPlanner() {
        this.allAttractions = new ArrayList<>();
    }

    public void addAttraction(Attraction a) {
        allAttractions.add(a);
    }

    public List<Attraction> getAllAttractions() {
        return allAttractions;
    }

    public List<Attraction> filterByCategory(String category) {
        List<Attraction> filtered = new ArrayList<>();

        for (Attraction a : allAttractions) {
            if (a.getCategory().equals(category)) {
                filtered.add(a);
            }
        }
        return filtered;
    }


}
