import java.time.Duration;
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
    ////dalam (0,0) ale jestem open
    /// mozemy tez zaczynac od konkretnego miesjca typu dworzec PKP(i to by moglo byc akurat niezle)
    private static Location DEFAULT_START_LOCATION = new Location(0.0, 0.0);

    public TripSchedule(LocalTime startTime, LocalTime endTime,  Location startLocation) {
        if(startTime.isAfter(endTime)){
            throw  new IllegalArgumentException("Start time should be before end time");
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

    public int getWaitingTime(Attraction attraction, LocalTime currentTime) {
        int waitTime = 0;
        if(attraction.getOpen().isAfter(currentTime)){
            waitTime = (int) Duration.between( currentTime, attraction.getOpen()).toMinutes();
        }
        return waitTime;
    }
    public int getTimeOfAttraction(Location startingLocation, Attraction attraction, LocalTime currentTime) {
        int waitTime = getWaitingTime(attraction, currentTime);
        return waitTime + startingLocation.travelTime(attraction.getLocation()) + attraction.getDurationMinutes();
    }

    public Attraction findNearestNeighbour(Attraction prev, List<Attraction> attractions, LocalTime currentTime) {
        Attraction closestNeighbour = null;
        int bestTime = Integer.MAX_VALUE;
        for(Attraction a : attractions) {
            if(!(a.equals(prev))) {
                if (!(verify(prev.getLocation(), a, currentTime))) continue;
                int time = getTimeOfAttraction(prev.getLocation(), a,  currentTime);
                if (time < bestTime) {
                    bestTime = time;
                    closestNeighbour = a;
                }
            }
        }
        return closestNeighbour;
    }

    ///metoda do napisania, ma sprawdzać czy możemy dodać daną atrakcję (czy zmieścimy się w określonym czasie) + czy jest otwarta


    public boolean verify(Location startLocation, Attraction attraction, LocalTime currentTime) {

        int travel = startLocation.travelTime(attraction.getLocation());
        LocalTime arrival = currentTime.plusMinutes(travel);

        int wait = getWaitingTime(attraction, arrival);
        LocalTime start = arrival.plusMinutes(wait);

        LocalTime finish = start.plusMinutes(attraction.getDurationMinutes());

        return attraction.isOpen(start) && !finish.isAfter(endTime);
    }

    public Attraction findFirstAttraction(Location location, List<Attraction> attractions, LocalTime currentTime) {
        int bestTime = Integer.MAX_VALUE;
        Attraction bestAttraction = null;
        for(Attraction a : attractions) {
            int travelTime = location.travelTime(a.getLocation()) + getWaitingTime(a, currentTime);
            LocalTime arrival = currentTime.plusMinutes(travelTime);
            if(!a.isOpen(arrival)) continue;
            if(arrival.plusMinutes(a.getDurationMinutes()).isAfter(endTime)) continue;
            int time = getTimeOfAttraction(location, a, currentTime);
            if(time < bestTime) {
                bestTime = time;
                bestAttraction = a;
            }
        }
        return bestAttraction;
    }



    public List<Attraction> createSchedule() {
        List<Attraction> schedule = new ArrayList<>();
        List<Attraction> temporary = new ArrayList<>(selectedAttractions);


        LocalTime currentTime = startTime;
        Location currentLocation = startLocation;

        Attraction first = findFirstAttraction(currentLocation,temporary, currentTime);
        if (first == null) return schedule;
        schedule.add(first);
        temporary.remove(first);
        currentTime = currentTime.plusMinutes(getTimeOfAttraction(startLocation, first, currentTime));
        Attraction prev = first;

        while(currentTime.isBefore(endTime) && !temporary.isEmpty()){
            Attraction next = findNearestNeighbour(prev, temporary, currentTime);
            if (next == null) break;
            schedule.add(next);
            currentTime = currentTime.plusMinutes(getTimeOfAttraction(prev.getLocation(), next,  currentTime));
            temporary.remove(next);
            prev = next;

        }
        return schedule;
    }
}