import java.io.Serializable;


/**
 * Class Location
 * Represents a point in 2D and provides methods for distance and travel time calculation.
 *
 * @author Karolina Kolarz
 * @author Maja Lamch
 * @version 1.0
 */
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    private double x;
    private double y;
    private static final double WALK_SPEED = 2.0/3.0; //ok. 5km/h
    private static final double CAR_SPEED = 5.0; //ok. 30km/h

    /**
     * Class constructor
     * Creates a new Location object.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns x coordinate.
     *
     * @return x value
     */
    public double getX() {
        return x;
    }

    /**
     * Returns y coordinate.
     *
     * @return y value
     */
    public double getY() {
        return y;
    }

    /**
     * Sets x coordinate.
     *
     * @param x new x value
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Sets y coordinate.
     *
     * @param y new y value
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Calculates distance between this location and another location.
     *
     * @param other another location
     * @return distance between locations
     */
    public double getDistance(Location other){
        return Math.abs(this.x - other.x)+Math.abs(this.y - other.y);
    }

    /**
     * Estimates travel time between two locations and selects the best transport method based on distance.
     *
     * - walking: short distances (up to 2km)
     * - car and parking buffer: long distances
     *
     * @param other destination location
     * @return travel time in minutes
     */
    public int travelTime(Location other) {
        if (getDistance(other) > 20.0) {
            return (int) Math.ceil(getDistance(other) / CAR_SPEED) + 10;
        } else {
            return (int) Math.ceil(getDistance(other) / WALK_SPEED);
        }
    }

    /**
     * Returns string representation of the location.
     *
     * @return formatted coordinates (x, y)
     */
    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }

    /**
     * Checks whether this location is equal to another object.
     * The comparison is based on the exact mathematical equality of the X and Y coordinates.
     *
     * @param o the object to compare
     * @return true if the objects are of class Location and have identical coordinates
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location other = (Location) o;
        // Porównujemy wartości współrzędnych x oraz y
        return Double.compare(other.x, x) == 0 && Double.compare(other.y, y) == 0;
    }

    /**
     * Returns a hash code value for the location based on its X and Y coordinates.
     * Guarantees that objects considered equal by the equals method will return the same hash code.
     *
     * @return the hash code value for this location
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(x, y);
    }
}
