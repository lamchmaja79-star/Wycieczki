import java.util.ArrayList;
import java.util.List;

public class TripSchedule {
    private List<Attraction> selectedAttractions;

    public TripSchedule() {
        this.selectedAttractions = new ArrayList<>();
    }

    public void addToPlan(Attraction a) {
        selectedAttractions.add(a); 
    }

    public void printSchedule();
