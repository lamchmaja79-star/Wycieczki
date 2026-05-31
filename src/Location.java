import java.io.Serializable;

public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    private double x;
    private double y;
    private static final double WALK_SPEED = 2.0/3.0; //ok. 5km/h
    private static final double CAR_SPEED = 5.0; //ok. 30km/h


    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDistance(Location other){
        double x = this.x - other.x;
        double y = this.y - other.y;
        return Math.sqrt(x*x + y*y); //////jesli chcialas tutaj x+y to sorki !!!
    }

    public int travelTime(Location other) {
        if (getDistance(other) > 20.0) {
            // Czas jazdy + 10 minut na zaparkowanie i dojście
            return (int) Math.ceil(getDistance(other) / CAR_SPEED) + 10;
        } else {
            // Wszędzie poniżej 2 km idziemy żwawym krokiem (5 km/h)
            return (int) Math.ceil(getDistance(other) / WALK_SPEED);
        }
    }

    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }
}
