import org.w3c.dom.Attr;

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

    public Attraction findNearestNeighbour(Attraction prev, List<Attraction> attractions) {
        Attraction closestNeighbour = attractions.getFirst();
        if(prev.equals(closestNeighbour)) { closestNeighbour = attractions.get(1); }
        int bestTime = getTimeOfAttraction(prev.getLocation(), closestNeighbour);
        for(Attraction a : attractions) {
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
    public boolean verify(Location startLocation,  Attraction attraction, LocalTime currentTime) {
        return endTime.isAfter(currentTime.plusMinutes(getTimeOfAttraction(startLocation, attraction)));
    }

    public Attraction findFirstAttraction(Location location, List<Attraction> attractions) {
        int bestTime = Integer.MAX_VALUE;
        Attraction bestAttraction = attractions.getFirst();
        for(Attraction a : attractions) {
            int time = getTimeOfAttraction(location, a);
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
        //lepiej na kopii niz oryginalnej bo jak bedziemy chcialy pozniej wypisac liste to bedzie pusta
        //skoro usuwamy te elementy i nie bedziemy mogly wyswietlic jej w interfejsie

        LocalTime currentTime = startTime;
        Location currentLocation = startLocation;

        Attraction first = findFirstAttraction(currentLocation,temporary);

        if(verify(startLocation,first,startTime)) {
            schedule.add(first);
            temporary.remove(first);
            currentTime = currentTime.plusMinutes(getTimeOfAttraction(startLocation, first));
            currentLocation = first.getLocation();
        }

        Attraction prev = first;

        while(currentTime.isBefore(endTime) && temporary.size() != 0){
            Attraction next = findNearestNeighbour(prev, temporary);

            if(verify(prev.getLocation(),next,currentTime)) {
                schedule.add(next);
                currentTime = currentTime.plusMinutes(getTimeOfAttraction(prev.getLocation(), next));
                temporary.remove(next);
                prev = next;
            }
            else {
                break; //nie wiem jak do końca tą pętlę dodać więc na razie robię break
            }
        }
        return schedule;
    }
}