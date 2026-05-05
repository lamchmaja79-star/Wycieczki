import java.util.ArrayList;
import java.util.List;

public class RouteAlgorithm {
    private List<Attraction> attractions;

    public Attraction findNearestNeighbour(Attraction prev, List<Attraction> availableAttractions) {
        Attraction closestNeighbour = availableAttractions.get(0);
        int bestTime = prev.travelTime(closestNeighbour)+closestNeighbour.getDurationMinutes();
        for(Attraction a : availableAttractions) {
            int time = prev.travelTime(a)+a.getDurationMinutes();
            if(time < bestTime) {
                bestTime = time;
                closestNeighbour = a;
            }
        }
        return closestNeighbour;
    }

    public List<Attraction> pathInOrder() {
        List<Attraction> attractions = new ArrayList<Attraction>();

        return attractions;
    }
}
