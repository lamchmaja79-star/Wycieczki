# 🗺️ Wycieczki — Kraków Trip Planner

> A JavaFX desktop application for building, scheduling, and optimizing sightseeing trips around Kraków.

**Wycieczki** helps users turn a list of attractions into a realistic day plan. Select the places you want to visit, define your available time and starting point, and the application calculates a feasible route while considering travel time, opening hours, waiting time, and visit duration.

---

## ✨ Highlights

- 🎯 **Trip planning** — select attractions and build a personalized itinerary.
- 🕐 **Time-aware scheduling** — respects trip start/end times and attraction opening hours.
- 🚶 **Travel estimation** — calculates travel time between locations using the project's coordinate model.
- 🧭 **Automatic route generation** — creates a feasible itinerary using a nearest-neighbour heuristic.
- 🔄 **Route optimization** — improves the generated route through local segment-reversal optimization.
- 🗺️ **Visual route map** — displays the resulting itinerary on a coordinate-based map.
- 🏷️ **Category filtering** — organize attractions by type.
- 💾 **Persistence** — stores the attraction database using Java serialization.
- 📄 **Plan export** — saves the generated itinerary as a readable `.txt` file.
- 🧪 **Automated tests** — includes JUnit 5 tests for the core planning logic.

---

## 📸 What it does

The application provides a desktop interface where the user can:

1. Browse the available Kraków attractions.
2. Filter attractions by category.
3. Select the attractions they want to visit.
4. Set the trip's starting and ending time.
5. Define the starting coordinates.
6. Generate an itinerary.
7. Review travel, waiting, and visit times.
8. See the route visually.
9. Save the finished plan to a text file.

The application comes with a built-in dataset containing attractions such as **Wawel Castle, Sukiennice, St. Mary's Basilica, Schindler's Factory, Kościuszko Mound, Wieliczka Salt Mine, Kraków Zoo**, and others.

---

## 🧠 Route planning

The scheduling engine is implemented in `TripSchedule`.

The planner takes into account:

- attraction opening time,
- attraction closing time,
- estimated visit duration,
- travel time,
- waiting time,
- the overall trip time window,
- the starting location.

### Route generation

The initial route is built using a **greedy nearest-neighbour strategy**. Candidate attractions are evaluated using a cost based on travel time and waiting time

The planner then attempts to improve the generated route by reversing route segments and retaining changes that reduce the route cost.

This provides a practical heuristic solution without requiring an exhaustive search of every possible attraction order.

> **Note:** The algorithm is heuristic and does not guarantee a globally optimal route.

---


## 🏛️ Attraction categories

Attractions can belong to categories including:

- `HISTORIA`
- `ROZRYWKA`
- `SZTUKA`
- `SPORT`
- `HARDCORE`
- `REKREACJA`
- `POSIŁKI`
- `WELLNESS`
- `PAMIATKI`

This makes it possible to narrow the attraction list before creating a trip.

---

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| **Java** | Core application logic |
| **JavaFX** | Desktop graphical user interface |
| **JUnit 5** | Automated testing |
| **Java Serialization** | Attraction data persistence |
| **IntelliJ IDEA** | Project configuration and development |

The checked-in IntelliJ project is configured around:

- **OpenJDK 25**
- **JavaFX 26.0.1**
- **JUnit Jupiter 5.14.0**

---

## 🧪 Testing

The project includes JUnit 5 tests covering the application's main domain and planning components.

Test coverage includes:

- attraction construction and equality,
- opening-hour validation,
- distance and travel-time calculations,
- filtering and sorting,
- duplicate prevention,
- trip time validation,
- waiting-time calculations,
- route feasibility,
- nearest-neighbour selection,
- route generation,
- handling attractions that cannot be reached within constraints,
- route optimization scenarios.

## 💾 Data persistence

The attraction database is stored in:

```text
atrakcje.ser
```

Generated trip plans are separate from the attraction database. The **Save plan to file** functionality exports the current itinerary as a human-readable `.txt` file.

---

## 🗺️ Route visualization

After generating a trip, the application presents both the itinerary and a visual representation of the route.

The visualization includes:

- the starting point,
- numbered attractions,
- route connections,
- the order in which attractions are visited.

The map is based on the application's 2D coordinate system rather than an external mapping provider.

---

## 📄 Example workflow

A typical planning session looks like this:

```text
Choose attractions
        ↓
Filter / sort the list
        ↓
Select attractions
        ↓
Set trip time window
        ↓
Set starting location
        ↓
Generate route
        ↓
Check travel + waiting + visit times
        ↓
Review route visualization
        ↓
Save itinerary
```

---

## 🔍 Design considerations

The project focuses on combining a graphical desktop interface with a small route-planning engine.

A key design decision is separating the planning logic from the JavaFX UI. Route calculations are handled by `TripSchedule`, while `UserInterface` is responsible for presenting the results and collecting user input.

The route planner deliberately uses heuristics instead of brute-force permutation search. This keeps route generation practical as the number of selected attractions grows, while the optimization step attempts to improve the initial solution.

---

## 📚 Documentation

Generated Javadoc is available under:

```text
src/doc/
```

It documents the main classes and their public APIs.

