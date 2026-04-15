import java.util.ArrayList;
import java.util.List;

public class TripSchedule {
    private List<Attraction> selectedAttractions;

    public TripSchedule() {
        this.selectedAttractions = new ArrayList<>();
    }

    public void addToPlan(Attraction a) {
        if (!selectedAttractions.contains(a)) {
            selectedAttractions.add(a);
        }
    }
    public List<Attraction> getSelectedAttractions() {
        return selectedAttractions;
    }

    public void createSchedule(){
        for(Attraction a : selectedAttractions){
            System.out.println(a);
        }
    };
}