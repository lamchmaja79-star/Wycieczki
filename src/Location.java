public class Location {
    private double x;
    private double y;
    private static final double SPEED = 0.833;


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
        return Math.sqrt(x*x + y*y);
    }

    public int travelTime(Location other) {
        return (int)(this.getDistance(other)/SPEED);
    }

    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }
}
