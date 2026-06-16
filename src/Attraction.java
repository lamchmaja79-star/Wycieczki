import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * Class Attraction
 * It stores information about name, categories, opening/closing hours, duration and location.
 *
 * @author Karolina Kolarz
 * @author Maja Lamch
 * @version 1.0
 */
public class Attraction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private List<Categories> categoryList;
    private LocalTime open;
    private LocalTime closed;
    private int durationMinutes;
    private Location location;

    /**
     * Class constructor
     * It creates new object of Attraction.
     *
     * @param name name of the attraction
     * @param categoryList list of categories
     * @param open opening time of the attraction
     * @param closed closing time of the attraction
     * @param durationMinutes estimated visit duration in minutes
     * @param location location of the atttracion
     */
    public Attraction(String name, List<Categories> categoryList, LocalTime open, LocalTime closed, int durationMinutes, Location location) {
        this.name = name;
        this.categoryList = categoryList;
        this.open = open;
        this.closed = closed;
        this.durationMinutes = durationMinutes;
        this.location = location;
    }

    /**
     * Returns name of the attraction.
     *
     * @return attraction name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns list of Categories.
     *
     * @return list of Categories
     */
    public List<Categories> getCategoryList() {
        return categoryList;

    }

    /**
     * Returns the opening time of the attraction.
     *
     * @return opening time of attraction
     */
    public LocalTime getOpen() { return open; }

    /**
     * Returns the closing time of the attraction.
     *
     * @return closing time of attraction
     */
    public LocalTime getClosed() { return closed; }

    /**
     * Returns estimated duration of a visit.
     *
     * @return duration in minutes
     */
    public int getDurationMinutes(){ return durationMinutes; }

    /**
     * Returns the location of the attraction.
     *
     * @return location
     */
    public Location getLocation() { return location; }


    /**
     * Returns string representation od the attraction
     *
     * @return string describing attraction
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < categoryList.size(); i++) {
            sb.append(categoryList.get(i).toString());
            if (i < categoryList.size() - 1) {
                sb.append(", ");
            }
        }
        return String.format("[%s]: %s, open: %s, closed: %s, czas trwania: %d min",
                sb.toString(), name, open, closed, durationMinutes);
    }

    /**
     * Checks whether this attraction is equal to another object.
     *
     * @param o object to compare
     * @return true if objects are equal
     */
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

    /**
     * Returns hash code of the attraction.
     *
     * @return hash code value
     */
    @Override
    public int  hashCode() {
        return Objects.hash(name, categoryList, open, closed, durationMinutes);
    }

    /**
     * Checks whether the attraction is open at a given time.
     *
     * @param time time to check
     * @return true if attraction is open at the given time
     */
    public boolean isOpen(LocalTime time) {
        LocalTime latestStart = closed.minusMinutes(durationMinutes);
        return !time.isBefore(open) && !time.isAfter(latestStart);
    }
}
