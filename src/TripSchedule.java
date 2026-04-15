import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TripSchedule {
    private List<Attraction> selectedAttractions;
    private LocalTime startTime;
    private LocalTime endTime;
    private static LocalTime DEFAULT_START_TIME = LocalTime.of(8, 0);
    private static LocalTime DEFAULT_END_TIME =  LocalTime.of(22, 0);

    public TripSchedule(LocalTime startTime, LocalTime endTime) {
        if(startTime.isAfter(endTime)){
            throw  new IllegalArgumentException("Start time should be after end time");
        }
        this.selectedAttractions = new ArrayList<>();
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public TripSchedule() {
        this(DEFAULT_START_TIME, DEFAULT_END_TIME);
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
}