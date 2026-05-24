import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public static List<Attraction> attractions = new ArrayList<>();
    public static void data(){
        List<Categories> h_a_s_Categories = new ArrayList<>();
        h_a_s_Categories.add(Categories.HISTORY);
        h_a_s_Categories.add(Categories.ART);
        h_a_s_Categories.add(Categories.SOUVENIR);
        List<Categories> s_Categories = new ArrayList<>();
        s_Categories.add(Categories.SOUVENIR);
        List<Categories> h_Categories = new ArrayList<>();
        h_Categories.add(Categories.HISTORY);
        List<Categories> h_s_Categories = new ArrayList<>();
        h_s_Categories.add(Categories.HISTORY);
        h_s_Categories.add(Categories.SOUVENIR);
        List<Categories> sp_e_Categories = new ArrayList<>();
        sp_e_Categories.add(Categories.SOUVENIR);
        sp_e_Categories.add(Categories.ENTERTAINMENT);
        List<Categories> h_e_Categories = new ArrayList<>();
        h_e_Categories.add(Categories.HISTORY);
        h_e_Categories.add(Categories.ENTERTAINMENT);
        List<Categories> r_e_Categories = new ArrayList<>();
        r_e_Categories.add(Categories.RECREATION);
        r_e_Categories.add(Categories.ENTERTAINMENT);
        List<Categories> r_e_sp_Categories = new ArrayList<>();
        r_e_sp_Categories.add(Categories.RECREATION);
        r_e_sp_Categories.add(Categories.ENTERTAINMENT);
        r_e_sp_Categories.add(Categories.SPORT);
        List<Categories> d_Categories = new ArrayList<>();
        d_Categories.add(Categories.DINING);

        attractions.add(new Attraction("Zamek Królewski na Wawelu", h_a_s_Categories, LocalTime.of(9,0), LocalTime.of(17, 0), 120, new Location(-4, -18)));
        attractions.add(new Attraction("Dworzec Główny PKP", s_Categories, LocalTime.MIDNIGHT, LocalTime.MAX, 5, new Location(0,0)));
        attractions.add(new Attraction("Muzeum Narodowe", h_a_s_Categories, LocalTime.of(10,0), LocalTime.of(18, 0), 180, new Location(-12,-7)));
        attractions.add(new Attraction("Brama Floriańska", h_Categories, LocalTime.of(8, 0),  LocalTime.of(18, 0), 30, new Location(-2,-3)));
        attractions.add(new Attraction("Sukiennice", h_s_Categories, LocalTime.of(10, 0), LocalTime.of(18, 0), 20, new Location(-4,-8)));
        attractions.add(new Attraction("Stadion Miejski im. Henryka Reymana", sp_e_Categories, LocalTime.of(8,0),  LocalTime.of(22, 0), 100, new Location(-25,-4)));
        attractions.add(new Attraction("Kościół Mariacki", h_Categories, LocalTime.of(11, 30), LocalTime.of(17, 45), 45, new Location(-3, -7)));
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

    }
    public static void saveAttractions() {
    }
}
