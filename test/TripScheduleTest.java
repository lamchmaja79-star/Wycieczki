import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripScheduleTest {
    private TripSchedule tripSchedule;
    List<Categories> category1;
    List<Categories> category2;
    private Attraction a1;
    private Attraction a2;
    private Attraction a3;
    private Attraction a4;

    @BeforeEach
    void setUp() {
        tripSchedule = new TripSchedule(LocalTime.of(8,0), LocalTime.of(18,30), new Location(0,0));
        category1 = new ArrayList<>();
        category2 = new ArrayList<>();
        category1.add(Categories.HISTORIA);
        category2.add(Categories.REKREACJA);
        a1 = new Attraction("Muzeum Narodowe", category1, LocalTime.of(9, 0), LocalTime.of(20, 0), 90, new Location(1, 2));
        a2 = new Attraction("Wawel", category1, LocalTime.of(10, 0), LocalTime.of(22, 0), 120, new Location(1, 1));
        a3 = new Attraction("Kino", category2, LocalTime.of(9, 0), LocalTime.of(20, 0), 50, new Location(7, 12));
        a4 = new Attraction("Daleka Atrakcja", category2, LocalTime.of(9, 0), LocalTime.of(20, 0), 50, new Location(5000, 5000));
    }

    @Test
    void constructor() {
        assertNotNull(tripSchedule.getSelectedAttractions(), "Konstruktor powinien tworzyć listę");
        assertTrue(tripSchedule.getSelectedAttractions().isEmpty(), "Konstruktor powinien tworzyć pustą listę");
        assertThrows(IllegalArgumentException.class, () -> {new TripSchedule(LocalTime.of(15, 0), LocalTime.of(10, 0), new Location(0,0));}, "Konstruktor powinien zwracać Illegal ArgumentException gdy startTime jest po endTime");
    }


    @Test
    void addToPlan() {
        tripSchedule.addToPlan(a1);
        assertEquals(1, tripSchedule.getSelectedAttractions().size());
        assertTrue(tripSchedule.getSelectedAttractions().contains(a1));
        tripSchedule.addToPlan(a1);
        assertEquals(1, tripSchedule.getSelectedAttractions().size());
        tripSchedule.addToPlan(a2);
        assertEquals(2, tripSchedule.getSelectedAttractions().size());
        assertTrue(tripSchedule.getSelectedAttractions().contains(a2) && tripSchedule.getSelectedAttractions().contains(a1));
    }

    @Test
    void getSelectedAttractions() {
        tripSchedule.addToPlan(a1);
        tripSchedule.addToPlan(a2);
        tripSchedule.addToPlan(a3);
        assertEquals(3, tripSchedule.getSelectedAttractions().size());
        assertTrue(tripSchedule.getSelectedAttractions().contains(a1));
        assertTrue(tripSchedule.getSelectedAttractions().contains(a2));
        assertTrue(tripSchedule.getSelectedAttractions().contains(a3));
    }

    @Test
    void testDeleteAttraction() {
        tripSchedule.addToPlan(a1);
        tripSchedule.deleteAttraction(a1);
        assertEquals(0, tripSchedule.getSelectedAttractions().size());
    }

    @Test
    void testGetWaitingTime() {
        LocalTime arrivalTime = LocalTime.of(8, 30);
        int wait = tripSchedule.getWaitingTime(a1, arrivalTime);
        assertEquals(30, wait, "Czas oczekiwania powinien wynosić 30 minut");
        assertEquals(0, tripSchedule.getWaitingTime(a1, LocalTime.of(10, 0)));
    }

    @Test
    void testVerify() {
        tripSchedule.setEndTime(LocalTime.of(10, 0));
        assertFalse(tripSchedule.verify(new Location(0,0), a1, LocalTime.of(9,30)));
    }

    @Test
    void testVerify_AttractionDurationTooLong() {
        Attraction ultraLongAttraction = new Attraction("Całodniowy Festiwal", category1, LocalTime.of(8, 0), LocalTime.of(23, 0), 720, new Location(-1, -1));

        assertFalse(tripSchedule.verify(new Location(0, 0), ultraLongAttraction, LocalTime.of(8, 0)),
                "Metoda verify musi odrzucić atrakcję, której czas trwania uniemożliwia powrót przed endTime wycieczki.");
    }

    @Test
    void testVerify_AttractionWaitTimeTooLong() {
        Attraction ultraLongWaitAttraction = new Attraction("Późna atrakcja", category1, LocalTime.of(18, 10), LocalTime.of(23, 0), 30, new Location(1, 1));

        assertFalse(tripSchedule.verify(new Location(0, 0), ultraLongWaitAttraction, LocalTime.of(8, 0)),
                "Metoda verify musi odrzucić atrakcję, w której czas czekania na otwarcie uniemożliwia powrót przed endTime wycieczki.");
    }

    @Test
    void testFindNearestNeighbour_ShouldSelectClosestByDistance() {
        Attraction closeAttraction = new Attraction("Bliska Atrakcja", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(1, 1));
        Attraction farAttraction = new Attraction("Daleka Atrakcja", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(10, 10));

        List<Attraction> available = List.of(closeAttraction, farAttraction);

        Attraction prev = new Attraction("Start", category1, LocalTime.of(8,0), LocalTime.of(20,0), 30, new Location(0,0));

        Attraction chosen = tripSchedule.findNearestNeighbour(prev, available, LocalTime.of(9, 0));
        assertNotNull(chosen);
        assertEquals(closeAttraction, chosen, "Algorytm najbliższego sąsiada musi priorytetyzować obiekt o mniejszym dystansie.");
    }

    @Test
    void testCreateSchedule_ShouldFollowLogicalSequenceOrder() {
        Attraction first = new Attraction("Pierwszy Przystanek", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 40, new Location(1, 1));
        Attraction second = new Attraction("Drugi Przystanek", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 40, new Location(3, 3));
        Attraction third = new Attraction("Trzeci Przystanek", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 40, new Location(5, 5));

        tripSchedule.addToPlan(third);
        tripSchedule.addToPlan(first);
        tripSchedule.addToPlan(second);

        List<Attraction> schedule = tripSchedule.createSchedule();

        assertEquals(3, schedule.size(), "Wszystkie 3 atrakcje powinny zmieścić się czasowo.");
        assertEquals(first, schedule.get(0), "Najbliższa punktu startowego (0,0) powinna być odwiedzona jako pierwsza.");
        assertEquals(second, schedule.get(1), "Kolejna w łańcuchu odległości powinna być druga.");
        assertEquals(third, schedule.get(2), "Najdalej wysunięta atrakcja powinna zamknąć harmonogram wycieczki.");
    }

    @Test
    void testCreateSchedule_ShouldSkipUnreachableAttractions() {
        // Dodajemy atrakcję, która jest fizycznie za daleko, by zdążyć w oknie czasowym
        tripSchedule.addToPlan(a4);

        List<Attraction> result = tripSchedule.createSchedule();

        assertTrue(result.isEmpty(), "Harmonogram powinien być pusty, jeśli atrakcja jest nieosiągalna");
    }

    @Test
    void testCreateSchedual() {
        tripSchedule.addToPlan(a1);
        tripSchedule.addToPlan(a2);

        List<Attraction> result = tripSchedule.createSchedule();
        assertTrue(result.contains(a1) || result.contains(a2));
        assertFalse(result.isEmpty());
    }

    @Test
    void testCreateSchedule_RhombusLayout() {
        // Startujemy w centrum (0,0)
        tripSchedule.setStartLocation(new Location(0, 0));

        // Tworzymy 4 atrakcje tworzące "okrąg" (w metryce Manhattan to idealny romb)
        // Wszystkie trwają tyle samo (30 min) i są otwarte w tych samych godzinach,
        // aby o wyborze decydowała wyłącznie odległość (punkty heurystyki).
        Attraction poludnie = new Attraction("A1 - Południe", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(2, 0));
        Attraction wschod   = new Attraction("A2 - Wschód",   category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(4, 2));
        Attraction polnoc   = new Attraction("A3 - Północ",   category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(2, 4));
        Attraction zachod   = new Attraction("A4 - Zachód",   category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(0, 2));

        // Dodajemy je do planu w specjalnej kolejności, wymuszającej pokazanie cechy algorytmu
        tripSchedule.addToPlan(wschod);
        tripSchedule.addToPlan(poludnie);
        tripSchedule.addToPlan(zachod);
        tripSchedule.addToPlan(polnoc);

        List<Attraction> result = tripSchedule.createSchedule();

        // Wypisujemy wynik w konsoli testów
        System.out.println("\n--- WYNIK DLA UKŁADU NA ROMBIE---");
        for (int i = 0; i < result.size(); i++) {
            System.out.println((i + 1) + ". " + result.get(i).getName() + " na pozycji ("
                    + result.get(i).getLocation().getX() + ", " + result.get(i).getLocation().getY() + ")");
        }

        assertEquals(4, result.size(), "Wszystkie 4 atrakcje powinny zostać odwiedzone.");
    }

    @Test
    void testCreateSchedule_OctagonLayout() {
        tripSchedule.setStartLocation(new Location(0, 0));

        // Tworzymy 8 punktów dookoła centrum (0,0)
        Attraction p1 = new Attraction("P1", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(0, 4));
        Attraction p2 = new Attraction("P2", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(3, 3));
        Attraction p3 = new Attraction("P3", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(4, 0));
        Attraction p4 = new Attraction("P4", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(3, -3));
        Attraction p5 = new Attraction("P5", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(0, -4));
        Attraction p6 = new Attraction("P6", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(-3, -3));
        Attraction p7 = new Attraction("P7", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(-4, 0));
        Attraction p8 = new Attraction("P8", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(-3, 3));

        //(Ważne jest aby P2 było przed P8, aby trasa nie szła w przeciwną stronę)
        tripSchedule.addToPlan(p1);
        tripSchedule.addToPlan(p3);
        tripSchedule.addToPlan(p5);
        tripSchedule.addToPlan(p2);
        tripSchedule.addToPlan(p6);
        tripSchedule.addToPlan(p4);
        tripSchedule.addToPlan(p8);
        tripSchedule.addToPlan(p7);

        List<Attraction> result = tripSchedule.createSchedule();

        System.out.println("\n--- WYNIK DLA OŚMIOKĄTA ---");
        for (int i = 0; i < result.size(); i++) {
            System.out.println((i + 1) + ". " + result.get(i).getName() + " -> ("
                    + result.get(i).getLocation().getX() + ", " + result.get(i).getLocation().getY() + ")");
        }

        // Sprawdzenie czy zachowano idealną sekwencję P1 -> P2 -> P3 ... -> P8
        assertEquals(8, result.size());
        assertEquals("P1", result.get(0).getName());
        assertEquals("P2", result.get(1).getName());
        assertEquals("P3", result.get(2).getName());
        assertEquals("P4", result.get(3).getName());
        assertEquals("P5", result.get(4).getName());
        assertEquals("P6", result.get(5).getName());
        assertEquals("P7", result.get(6).getName());
        assertEquals("P8", result.get(7).getName());
    }

    @Test
    void testCreateSchedule_TimeTrapScenario() {
        tripSchedule.setStartLocation(new Location(0, 0));

        // Atrakcja Bardzo Bliska, ale otwiera się dopiero o 16:00
        Attraction closeButClosed = new Attraction("Bliska-Zamknięta", category1, LocalTime.of(16, 0), LocalTime.of(20, 0), 30, new Location(1, 0));

        // Atrakcja Dalsza, ale otwarta od samego rana (8:00)
        Attraction farButOpen = new Attraction("Dalsza-Otwarta", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(5, 5));

        tripSchedule.addToPlan(closeButClosed);
        tripSchedule.addToPlan(farButOpen);
        List<Attraction> result = tripSchedule.createSchedule();
        assertNotNull(result);
        assertFalse(result.isEmpty(), "Harmonogram nie powinien być pusty.");

        // Krytyczna asercja: Pierwsza musi być atrakcja dalsza, bo bliska marnuje 8 godzin dnia
        assertEquals("Dalsza-Otwarta", result.get(0).getName(), "Algorytm powinien najpierw wybrać dalszą, ale otwartą atrakcję, zamiast czekać caly dzień.");
    }

    @Test
    void testVerify_ExactEndTimeBoundary() {
        tripSchedule.setStartLocation(new Location(0, 0));
        tripSchedule.setEndTime(LocalTime.of(12, 0));

        Attraction exactFitAttraction = new Attraction("Atrakcja na styk", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 117, new Location(0, 2));
        boolean canVisit = tripSchedule.verify(new Location(0, 0), exactFitAttraction, LocalTime.of(10, 0));
        assertTrue(canVisit, "Metoda verify powinna dopuścić atrakcję, która kończy się dokładnie o endTime wycieczki.");
    }

    @Test
    void testCreateSchedule_IdenticalLocations() {
        tripSchedule.setStartLocation(new Location(0, 0));

        Attraction wystawa1 = new Attraction("Muzeum - Część 1", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(2, 2));
        Attraction wystawa2 = new Attraction("Muzeum - Część 2", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(2, 2));
        Attraction wystawa3 = new Attraction("Muzeum - Część 3", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 30, new Location(2, 2));

        tripSchedule.addToPlan(wystawa3);
        tripSchedule.addToPlan(wystawa1);
        tripSchedule.addToPlan(wystawa2);

        List<Attraction> result = tripSchedule.createSchedule();

        assertEquals(3, result.size(), "Algorytm powinien zaliczyć wszystkie atrakcje, nawet jeśli mają dystans 0 między sobą.");
        assertTrue(result.contains(wystawa1));
        assertTrue(result.contains(wystawa2));
        assertTrue(result.contains(wystawa3));
    }

    @Test
    void testCreateSchedule_DeadEndScenario() {
        tripSchedule.setStartLocation(new Location(0, 0));

        Attraction bliskaA = new Attraction("Bliska A", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 60, new Location(2, 0));
        Attraction bliskaB = new Attraction("Bliska B", category1, LocalTime.of(8, 0), LocalTime.of(20, 0), 60, new Location(4, 0));
        Attraction dalekaC = new Attraction("Daleka C (Pilna)", category1, LocalTime.of(8, 0), LocalTime.of(9, 30), 30, new Location(15, 0));

        tripSchedule.addToPlan(bliskaA);
        tripSchedule.addToPlan(bliskaB);
        tripSchedule.addToPlan(dalekaC);

        List<Attraction> result = tripSchedule.createSchedule();

        System.out.println("\n--- DIAGNOSTYKA: ŚLEPY ZAUŁEK ---");
        for (Attraction attr : result) {
            System.out.println("Odwiedzono: " + attr.getName());
        }

        assertNotNull(result);
    }
}