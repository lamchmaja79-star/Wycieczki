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
        double x = this.x - other.x;
        double y = this.y - other.y;
        return x+y;
    }

    /**
     * Estimates travel time between two locations and selects the best transport method based on distance.
     *
     * - walking: short distances (up to 2km)
     * - car + parking buffer: long distances
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
}
