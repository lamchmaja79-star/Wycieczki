import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Class DataManager
 * Manages attraction data by creating sample data and saving/loading it from a file.
 *
 * @author Maja Lamch
 * @version 1.0
 */
public class DataManager {
    private static String fileName = "atrakcje.ser";

    /**
     * Returns the name of the file used to store attractions.
     *
     * @return file name
     */
    public static String getFileName() {
        return fileName;
    }

    /** List containing all available attractions */
    public static List<Attraction> attractions = new ArrayList<>();

    /**
     * Creates and returns a list of sample attractions.
     *
     * @return list of sample attractions
     */
    public static List<Attraction> data(){
        List<Categories> h_a_s_Categories = new ArrayList<>();
        h_a_s_Categories.add(Categories.HISTORIA);
        h_a_s_Categories.add(Categories.SZTUKA);
        h_a_s_Categories.add(Categories.PAMIATKI);
        List<Categories> s_Categories = new ArrayList<>();
        s_Categories.add(Categories.PAMIATKI);
        List<Categories> h_Categories = new ArrayList<>();
        h_Categories.add(Categories.HISTORIA);
        List<Categories> h_s_Categories = new ArrayList<>();
        h_s_Categories.add(Categories.HISTORIA);
        h_s_Categories.add(Categories.PAMIATKI);
        List<Categories> sp_e_Categories = new ArrayList<>();
        sp_e_Categories.add(Categories.PAMIATKI);
        sp_e_Categories.add(Categories.ROZRYWKA);
        List<Categories> h_e_Categories = new ArrayList<>();
        h_e_Categories.add(Categories.HISTORIA);
        h_e_Categories.add(Categories.ROZRYWKA);
        List<Categories> r_e_Categories = new ArrayList<>();
        r_e_Categories.add(Categories.REKREACJA);
        r_e_Categories.add(Categories.ROZRYWKA);
        List<Categories> r_e_sp_Categories = new ArrayList<>();
        r_e_sp_Categories.add(Categories.REKREACJA);
        r_e_sp_Categories.add(Categories.ROZRYWKA);
        r_e_sp_Categories.add(Categories.SPORT);
        List<Categories> d_Categories = new ArrayList<>();
        d_Categories.add(Categories.POSIŁKI);

        attractions.add(new Attraction("Zamek Królewski na Wawelu", h_a_s_Categories, LocalTime.of(9,0), LocalTime.of(17, 0), 120, new Location(-4, -18)));
        attractions.add(new Attraction("Dworzec Główny PKP", s_Categories, LocalTime.MIDNIGHT, LocalTime.MAX, 5, new Location(0,0)));
        attractions.add(new Attraction("Muzeum Narodowe", h_a_s_Categories, LocalTime.of(10,0), LocalTime.of(18, 0), 180, new Location(-12,-7)));
        attractions.add(new Attraction("Brama Floriańska", h_Categories, LocalTime.of(8, 0),  LocalTime.of(18, 0), 30, new Location(-2,-3)));
        attractions.add(new Attraction("Sukiennice", h_s_Categories, LocalTime.of(10, 0), LocalTime.of(18, 0), 20, new Location(-4,-8)));
        attractions.add(new Attraction("Stadion Miejski im. Henryka Reymana", sp_e_Categories, LocalTime.of(8,0),  LocalTime.of(22, 0), 100, new Location(-25,-4)));
        attractions.add(new Attraction("Kościół Mariacki", h_Categories, LocalTime.of(11, 30), LocalTime.of(17, 45), 45, new Location(-3.5, -7.5)));
        attractions.add(new Attraction("Smok Wawelski", h_e_Categories, LocalTime.MIDNIGHT, LocalTime.MAX, 15, new Location(-6,-19)));
        attractions.add(new Attraction("Kopiec Kościuszki", h_s_Categories, LocalTime.of(9,0), LocalTime.of(19, 0), 60, new Location(-45, -10)));
        attractions.add(new Attraction("Muzeum Lotnictwa Polskiego", h_Categories, LocalTime.of(9,0), LocalTime.of(17, 0), 120, new Location(40,5)));
        attractions.add(new Attraction("Kopalnia soli w Wieliczce", h_a_s_Categories, LocalTime.of(8,0), LocalTime.of(18, 0), 200, new Location(60, -110)));
        attractions.add(new Attraction("Fabryka 'Emalia' Oskara Schindlera", h_Categories, LocalTime.of(9,0), LocalTime.of(20, 0), 60, new Location(12,-23)));
        attractions.add(new Attraction("Barbakan", h_Categories, LocalTime.of(10,30), LocalTime.of(18, 0), 30, new Location(-2,-2)));
        attractions.add(new Attraction("Muzeum Uniwersytetu Jagiellońskiego Collegium Maius", h_Categories, LocalTime.of(9,0), LocalTime.of(16, 30), 30, new Location(-7,-7)));
        attractions.add(new Attraction("Zalew Zakrzówek", r_e_Categories, LocalTime.of(8,00), LocalTime.of(19, 0), 90, new Location(-25,-32)));
        attractions.add(new Attraction("VIRAL Kebab", d_Categories, LocalTime.of(12,0), LocalTime.of(22, 0), 30, new Location(-13,2)));
        attractions.add(new Attraction("Pixel Planet", r_e_sp_Categories, LocalTime.of(10, 0), LocalTime.of(22, 0), 60, new Location(-4, -7)));
        attractions.add(new Attraction("Podziemia Rynku", List.of(Categories.HISTORIA, Categories.ROZRYWKA), LocalTime.of(10, 0), LocalTime.of(19, 0), 60, new Location(-4, -8)));
        attractions.add(new Attraction("Koło młyńskie", List.of(Categories.ROZRYWKA, Categories.HARDCORE), LocalTime.of(11, 0), LocalTime.of(21, 0), 60, new Location(-8, -23)));
        attractions.add(new Attraction("Kino Pod Baranami", List.of(Categories.ROZRYWKA), LocalTime.of(11, 0), LocalTime.of(23, 0), 130, new Location(-4.8, -8.3)));
        attractions.add(new Attraction("Escape Room", List.of(Categories.ROZRYWKA), LocalTime.of(8, 0), LocalTime.of(21, 0), 60, new Location(-3, -5)));
        attractions.add(new Attraction("Galeria Sztuki Współczesnej", List.of(Categories.SZTUKA), LocalTime.of(11, 0), LocalTime.of(19, 0), 90, new Location(-5.8, -4.2)));
        attractions.add(new Attraction("Teatr im. J. Słowackiego", List.of(Categories.SZTUKA), LocalTime.of(18, 0), LocalTime.of(23, 0), 150, new Location(-1, -3.5)));
        attractions.add(new Attraction("Park Wodny", List.of(Categories.SPORT, Categories.WELLNESS, Categories.REKREACJA), LocalTime.of(8, 0), LocalTime.of(22, 0), 120, new Location(25, 25)));
        attractions.add(new Attraction("Skok na Bungee", List.of(Categories.HARDCORE), LocalTime.of(12, 0), LocalTime.of(18, 30), 45, new Location(20.0, -10.0)));
        attractions.add(new Attraction("Zoo", List.of(Categories.REKREACJA), LocalTime.of(9, 0), LocalTime.of(19, 0), 180, new Location(-67.2, -2.2)));
        attractions.add(new Attraction("Restaurację Wierzynek", List.of(Categories.POSIŁKI), LocalTime.of(13, 0), LocalTime.of(23, 0), 60, new Location(-3.6, -8.2)));
        attractions.add(new Attraction("Masaż", List.of(Categories.WELLNESS, Categories.REKREACJA), LocalTime.of(9, 0), LocalTime.of(20, 0), 180, new Location(-3.0, -15.0)));

        return attractions;
    }

    /**
     * Saves a list of attractions to a file using serialization.
     *
     * @param attractionsList list of attractions to save
     */
    public static void saveToFile(List<Attraction> attractionsList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(attractionsList);
            System.out.println("Pomyślnie zapisano listę atrakcji do pliku: " + fileName);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisywania do pliku: " + e.getMessage());
        }
    }

    /**
     * Loads a list of attractions from a file using deserialization.
     *
     * @return list of loaded attractions
     */
    public static List<Attraction> loadFromFile() {
        List<Attraction> loadedAttractions = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            loadedAttractions = (List<Attraction>) ois.readObject();
            System.out.println("Pomyślnie wczytano atrakcje z pliku: " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("Plik " + fileName + " nie istnieje. Zostanie utworzona nowa baza.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
        }
        return loadedAttractions;
    }
}
