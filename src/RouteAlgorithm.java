import java.util.ArrayList;
import java.util.List;

public class RouteAlgorithm {
    private List<Attraction> attractions;

    public Attraction findNearestNeighbour(Attraction prev, List<Attraction> availableAttractions) {
        Attraction closestNeighbour = null;
        for(Attraction a : availableAttractions) {
            prev.getLocation().getDistance(a.getLocation());
        }
        return closestNeighbour;
    }

    public List<Attraction> pathInOrder() {
        List<Attraction> attractions = new ArrayList<Attraction>();

        return attractions;
    }
}
