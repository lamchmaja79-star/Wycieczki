import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;



/**
 * Class TripSchedule
 * Responsible for building a trip plan based on selected attractions, travel time, opening hours, and time constraints.
 * Uses a greedy nearest-neighbour strategy to create a schedule.
 *
 * @author Karolina Kolarz
 * @author Maja Lamch
 * @version 1.0
 */
public class TripSchedule {
    private List<Attraction> selectedAttractions;
    private LocalTime startTime;
    private LocalTime endTime;
    private static LocalTime DEFAULT_START_TIME = LocalTime.of(8, 0);
    private static LocalTime DEFAULT_END_TIME =  LocalTime.of(22, 0);
    private Location startLocation;
    private static Location DEFAULT_START_LOCATION = new Location(0.0, 0.0);


    /**
     * Class constructor
     * Creates a TripSchedule with specified start time, end time and starting location.
     *
     * @param startTime start time of the trip
     * @param endTime end time of the trip
     * @param startLocation starting location
     * @throws IllegalArgumentException if startTime is after endTime
     */
    public TripSchedule(LocalTime startTime, LocalTime endTime,  Location startLocation) {
        if(startTime.isAfter(endTime)){
            throw  new IllegalArgumentException("Start time should be before end time");
        }
        this.selectedAttractions = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.startLocation = startLocation;
    }

    /**
     * Creates a TripSchedule with default time range and starting location.
     */
    public TripSchedule() {
        this(DEFAULT_START_TIME, DEFAULT_END_TIME, DEFAULT_START_LOCATION);
    }

    /**
     * Adds an attraction to the trip plan if it is not already included.
     *
     * @param a attraction to add
     */
    public void addToPlan(Attraction a) {
        if (!selectedAttractions.contains(a)) {
            selectedAttractions.add(a);
        }
    }

    /**
     * Returns the list of selected attractions.
     *
     * @return list of selected attractions
     */
    public List<Attraction> getSelectedAttractions() {
        return selectedAttractions;
    }


    /**
     * Removes an attraction from the trip plan.
     *
     * @param a attraction to remove
     */
    public void deleteAttraction(Attraction a) {
        selectedAttractions.remove(a);
    }

    /** Prints all selected attractions to standard output */
    public void printSelectedAttractions() {
        for(Attraction a : selectedAttractions){
            System.out.println(a);
        }
    };

    //public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    //public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    /**
     * Returns start time of the trip.
     *
     * @return start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }
    /**
     * Returns end time of the trip.
     *
     * @return end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Calculates waiting time until attraction opens.
     *
     * @param attraction attraction to check
     * @param currentTime current time
     * @return waiting time in minutes
     */
    public int getWaitingTime(Attraction attraction, LocalTime currentTime) {
        int waitTime = 0;
        if(attraction.getOpen().isAfter(currentTime)){
            waitTime = (int) Duration.between( currentTime, attraction.getOpen()).toMinutes();
        }
        return waitTime;
    }

    /**
     * Calculates total time required to reach and visit an attraction.
     *
     * @param startingLocation current location
     * @param attraction destination attraction
     * @param currentTime current time
     * @return total time (travel + waiting + visit duration)
     */
    public int getTimeOfAttraction(Location startingLocation, Attraction attraction, LocalTime currentTime) {
        int waitTime = getWaitingTime(attraction, currentTime);
        return waitTime + startingLocation.travelTime(attraction.getLocation()) + attraction.getDurationMinutes();
    }


    /**
     * Calculates "cost" of visiting an attraction from a given location.
     * Includes travel time, waiting time, and attraction duration.
     *
     * @param startingLocation current location
     * @param attraction attraction to evaluate
     * @param currentTime current time
     * @return total estimated cost in minutes (weighted travel + wait + visit time)
     */
    public int getPointsOfAttraction(Location startingLocation, Attraction attraction, LocalTime currentTime) {
        int waitTime = getWaitingTime(attraction, currentTime);
        return (5*startingLocation.travelTime(attraction.getLocation())) + attraction.getDurationMinutes() + waitTime;
    }


    /**
     * Finds the nearest valid next attraction based on travel time and constraints.
     *
     * @param prev previous attraction
     * @param attractions list of available attractions
     * @param currentTime current time
     * @return closest valid attraction or null if none found
     */
    public Attraction findNearestNeighbour(Attraction prev, List<Attraction> attractions, LocalTime currentTime) {
        Attraction closestNeighbour = null;
        int bestPoints = Integer.MAX_VALUE;
        for(Attraction a : attractions) {
            if(!(a.equals(prev))) {
                if (!(verify(prev.getLocation(), a, currentTime))) continue;
                int points = getPointsOfAttraction(prev.getLocation(), a,  currentTime);
                if (points < bestPoints) {
                    bestPoints = points;
                    closestNeighbour = a;
                }
            }
        }
        return closestNeighbour;
    }


    /**
     * Checks whether an attraction can be visited within constraints.
     *
     * @param startLocation current location
     * @param attraction attraction to check
     * @param currentTime current time
     * @return true if attraction is visitable within schedule
     */
    public boolean verify(Location startLocation, Attraction attraction, LocalTime currentTime) {

        int travel = startLocation.travelTime(attraction.getLocation());
        LocalTime arrival = currentTime.plusMinutes(travel);

        int wait = getWaitingTime(attraction, arrival);
        LocalTime start = arrival.plusMinutes(wait);

        LocalTime finish = start.plusMinutes(attraction.getDurationMinutes());

        return attraction.isOpen(start) && !finish.isAfter(endTime);
    }

    /**
     * Finds the best first attraction to start the trip.
     *
     * @param location starting location
     * @param attractions list of attractions
     * @param currentTime current time
     * @return best starting attraction or null if none available
     */
    public Attraction findFirstAttraction(Location location, List<Attraction> attractions, LocalTime currentTime) {
        int bestPoints = Integer.MAX_VALUE;
        Attraction bestAttraction = null;
        for(Attraction a : attractions) {
            int travelTime = location.travelTime(a.getLocation()) + getWaitingTime(a, currentTime);
            LocalTime arrival = currentTime.plusMinutes(travelTime);
            if(!a.isOpen(arrival)) continue;
            if(arrival.plusMinutes(a.getDurationMinutes()).isAfter(endTime)) continue;
            int points = getPointsOfAttraction(location, a, currentTime);
            if(points < bestPoints) {
                bestPoints = points;
                bestAttraction = a;
            }
        }
        return bestAttraction;
    }


    /**
     * Creates a full trip schedule using a greedy nearest-neighbour algorithm.
     *
     * @return list of attractions in visiting order
     */
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
