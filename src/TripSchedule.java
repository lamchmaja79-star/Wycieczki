import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TripSchedule {
    private List<Attraction> selectedAttractions;
    private LocalTime startTime;
    private LocalTime endTime;
    private static LocalTime DEFAULT_START_TIME = LocalTime.of(8, 0);
    private static LocalTime DEFAULT_END_TIME =  LocalTime.of(22, 0);
    private Location startLocation;
    private static Location DEFAULT_START_LOCATION;

    public TripSchedule(LocalTime startTime, LocalTime endTime,  Location startLocation) {
        if(startTime.isAfter(endTime)){
            throw  new IllegalArgumentException("Start time should be after end time");
        }
        this.selectedAttractions = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = startLocation;
    }
    public TripSchedule() {
        this(DEFAULT_START_TIME, DEFAULT_END_TIME, DEFAULT_START_LOCATION);
    }

    public void addToPlan(Attraction a) {
        if (!selectedAttractions.contains(a)) {
            selectedAttractions.add(a);
        }
    }
    public List<Attraction> getSelectedAttractions() {
        return selectedAttractions;
    }
    public void deleteAttraction(Attraction a) {
        selectedAttractions.remove(a);
    }

    public void printSelectedAttractions() {
        for(Attraction a : selectedAttractions){
            System.out.println(a);
        }
    };

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }

    public int getTimeOfAttraction(Location startingLocation, Attraction attraction) {
        return startingLocation.travelTime(attraction.getLocation()) + attraction.getDurationMinutes();
    }

    public Attraction findNearestNeighbour(Attraction prev) {
        Attraction closestNeighbour = selectedAttractions.getFirst();
        if(prev.equals(closestNeighbour)) { closestNeighbour = selectedAttractions.get(1); }
        int bestTime = getTimeOfAttraction(prev.getLocation(), closestNeighbour);
        for(Attraction a : selectedAttractions) {
            if(!(a.equals(prev))) {
                int time = getTimeOfAttraction(prev.getLocation(), a);
                if (time < bestTime) {
                    bestTime = time;
                    closestNeighbour = a;
                }
            }
        }
        return closestNeighbour;
    }

    //metoda do napisania, ma sprawdzać czy możemy dodać daną atrakcję (czy zmieścimy się w określonym czasie)
    public boolean verify(Attraction attraction, LocalTime currentTime) {
        return true;
    }

    public Attraction findFirstAttraction(Location location) {
        int bestTime = Integer.MAX_VALUE;
        Attraction bestAttraction = selectedAttractions.getFirst();
        for(Attraction a : selectedAttractions) {
            int time = getTimeOfAttraction(location, a);
            if(time < bestTime) {
                bestTime = time;
                bestAttraction = a;
            }
        }
        return bestAttraction;
    }

    //dokończyć
    public List<Attraction> createSchedual() {
        List<Attraction> schedual = new ArrayList<>();
        Attraction first = findFirstAttraction(startLocation);
        for(Attraction a : selectedAttractions){

        }
        return schedual;
    }
}