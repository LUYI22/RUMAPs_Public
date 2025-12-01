package rumaps;

import java.util.*;

/**
 * The Network class is used to represent the street network in the map.
 * It contains the following attributes:
 * - coordinates - list of coordinates with corresponding intersections
 * - intersections - list of intersections
 * - streets - list of streets
 * - mapPanel - map panel for GUI changes
 * 
 * @author Vian Miranda
 * @author Anna Lu
 */
public class Network {
    // Parallel ArrayLists (index i in coordinates list corresponds (or maps) to index i in the intersections list)
    // This can be represented in a more efficient manner. Any ideas?
    private ArrayList<Coordinate> coordinates;
    private ArrayList<Intersection> intersections;
    
    // Streets are made up of blocks
    private ArrayList<Street> streets;
    
    // Purely for GUI
    private MapPanel mapPanel;

    /**
     * For interactive GUI
     * 
     * @param mapPanel
     */
    public Network(MapPanel mapPanel) {
        coordinates = new ArrayList<>();
        intersections = new ArrayList<>();
        streets = new ArrayList<>();
        this.mapPanel = mapPanel;
    }

    /**
     * For testing (no GUI)
     */
    public Network() {
        this(null);
    }

    public void addIntersection(Coordinate coordinate, Intersection intersection) {
        coordinates.add(coordinate);
        intersections.add(intersection);
        if (mapPanel != null) mapPanel.addInteresection(intersection);
    }

    public Intersection getIntersection(Coordinate coordinate) {
        return intersections.get(coordinates.indexOf(coordinate));
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public ArrayList<Intersection> getIntersections() {
        return intersections;
    }

    public boolean containsIntersection(Coordinate coordinate) {
        return coordinates.contains(coordinate);
    }

    public void addStreet(Street street) {
        streets.add(street);

        if (mapPanel == null) return;
        for (Block block : street.getBlocks()) {
            mapPanel.addBlock(block);
        }
    }

    public ArrayList<Street> getStreets() {
        return streets;
    }
}
