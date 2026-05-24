import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class Attraction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<Categories> categoryList;
    private LocalTime open;
    private LocalTime closed;
    private int durationMinutes;
    private Location location;


    public Attraction(String name, List<Categories> categoryList, LocalTime open, LocalTime closed, int durationMinutes, Location location) {
        this.name = name;
        this.categoryList = categoryList;
        this.open = open;
        this.closed = closed;
        this.durationMinutes = durationMinutes;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public List<Categories> getCategoryList() {
        return categoryList;

    }

    public LocalTime getOpen() { return open; }

    public LocalTime getClosed() { return closed; }

    public int getDurationMinutes(){
        return durationMinutes; }

    public Location getLocation() { return location; }

    @Override
    public String toString() {
        String categoriesString = "";
        for( Categories c : categoryList){
            categoriesString += c.toString() ;
        }
        return String.format("%s: %s, open: %s, closed: %s, czas trwania: %d min",
                categoriesString, name, open, closed, durationMinutes);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Attraction)) return false;
        Attraction other = (Attraction) o;
        return durationMinutes == other.durationMinutes &&
                Objects.equals(name, other.name) &&
                Objects.equals(categoryList, other.categoryList) &&
                Objects.equals(open, other.open) &&
                Objects.equals(closed, other.closed);
    }

    @Override
    public int  hashCode() {
        return Objects.hash(name, categoryList, open, closed, durationMinutes);
    }

    public boolean isOpen(LocalTime time) {
        LocalTime latestStart = closed.minusMinutes(durationMinutes);
        return !time.isBefore(open) && !time.isAfter(latestStart);
    }
}
