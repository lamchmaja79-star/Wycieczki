import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public void data(){
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
        List<Attraction> attractions = new ArrayList<>();
        attractions.add(new Attraction("Zamek Królewski na Wawelu", h_a_s_Categories, LocalTime.of(9,0), LocalTime.of(17, 0), 120, new Location(-4, -18)));
        attractions.add(new Attraction("Dworzec Główny PKP", s_Categories, LocalTime.MIDNIGHT, LocalTime.MAX, 5, new Location(0,0)));
        attractions.add(new Attraction("Muzeum Narodowe", h_a_s_Categories, LocalTime.of(10,0), LocalTime.of(18, 0), 180, new Location(-12,-7)));
        attractions.add(new Attraction("Brama Floriańska", h_Categories, LocalTime.of(8, 0),  LocalTime.of(18, 0), 30, new Location(-2,-3)));
        attractions.add(new Attraction("Sukiennice", h_s_Categories, LocalTime.of(10, 0), LocalTime.of(18, 0), 20, new Location(-4,-8)));
        attractions.add(new Attraction("Stadion Miejski im. Henryka Reymana", sp_e_Categories, LocalTime.of(8,0),  LocalTime.of(22, 0), 100, new Location(-25,-4)));
        attractions.add(new Attraction("Kościół Mariacki", h_Categories, LocalTime.of(11, 30), LocalTime.of(17, 45), 45, new Location(-3, -7)));
        attractions.add(new Attraction("Smok Wawelski", h_e_Categories, LocalTime.MIDNIGHT, LocalTime.MAX, 15, new Location(-6,-19)));
        attractions.add(new Attraction("Kopiec kościuszki", h_s_Categories, LocalTime.of(9,0), LocalTime.of(19, 0), 60, new Location(-45, -10)));
    }
}
