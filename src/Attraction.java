import java.time.LocalTime;
import java.util.Objects;

public class Attraction {
    private String name;
    private String category;
    private LocalTime open;
    private LocalTime closed;
    private int durationMinutes;

    public Attraction(String name, String category, LocalTime open, LocalTime closed, int durationMinutes) {
        this.name = name;
        this.category = category;
        this.open = open;
        this.closed = closed;
        this.durationMinutes = durationMinutes;
    }
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;

    }
    public LocalTime getOpen() { return open; }
    public LocalTime getClosed() { return closed; }
    public int getDurationMinutes(){
        return durationMinutes; }

    @Override
    public String toString() {
        return String.format("%s: %s, open: %s, closed: %s, czas trwania: %d min",
                category.toUpperCase(), name, open, closed, durationMinutes);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Attraction)) return false;
        Attraction other = (Attraction) o;
        return durationMinutes == other.durationMinutes &&
                Objects.equals(name, other.name) &&
                Objects.equals(category, other.category) &&
                Objects.equals(open, other.open) &&
                Objects.equals(closed, other.closed);
    }
    @Override
    public int  hashCode() {
        return Objects.hash(name, category, open, closed, durationMinutes);
    }

    public boolean isOpen(LocalTime time){
        if(time.isAfter(open) && time.isBefore(closed.minusMinutes(durationMinutes)) ){
            return true;
        }
        return false;
    }
}
