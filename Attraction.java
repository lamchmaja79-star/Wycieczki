import java.time.LocalTime;

public class Attraction {
    private String name;
    private String category;
    private LocalTime open;
    private LocalTime closed;
    private int durationMinutes; // czas zwiedzania [cite: 16]

    public Attraction(String name, String category, LocalTime open, LocalTime closed, int durationMinutes) {
        this.name = name;
        this.category = category;
        this.open = open;
        this.closed = closed;
        this.durationMinutes = durationMinutes;
    }

    // Gettery (potrzebne do filtrowania i harmonogramu)
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
        return String.format(
                category.toUpperCase(), name, open, openToclosed, durationMinutes);
    }
}