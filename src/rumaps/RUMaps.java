package rumaps;

import java.util.*;

/**
 * Assignment skeleton for the Rutgers University map data-structure project.
 *
 * The methods below are intentionally left as stubs so students can complete
 * the required graph and map logic as part of the assignment.
 */
public class RUMaps {
    private Network rutgers;

    /**
     * Constructor for the RUMaps class.
     *
     * @param mapPanel the map panel used by the application
     * @param filename the input file containing the street data
     */
    public RUMaps(MapPanel mapPanel, String filename) {
        // TODO: initialize the network and load the map data
    }

    /**
     * Overloaded constructor for testing or simpler initialization.
     *
     * @param filename the input file containing the street data
     */
    public RUMaps(String filename) {
        // TODO: initialize the RUMaps instance using the filename
    }

    /**
     * Reads the street and block data from the input file.
     *
     * @param filename the input file containing the street data
     */
    public void initializeStreets(String filename) {
        // TODO: read the streets, blocks, and coordinates from the file
    }

    /**
     * Creates the intersections for the network and associates blocks with them.
     */
    public void initializeIntersections() {
        // TODO: build intersections and connect each block to its endpoints
    }

    /**
     * Computes the Euclidean distance between two coordinates.
     *
     * @param a the first coordinate
     * @param b the second coordinate
     * @return the Euclidean distance between a and b
     */
    private double coordinateDistance(Coordinate a, Coordinate b) {
        // TODO: calculate the distance between the two coordinates
        return 0.0;
    }

    /**
     * Computes the total length of a block.
     *
     * @param block the block to evaluate
     * @return the total length of the block
     */
    public double blockLength(Block block) {
        // TODO: sum the segment lengths along the block's coordinate points
        return 0.0;
    }

    /**
     * Computes the traffic factor for a block.
     *
     * @param block the block to evaluate
     * @return the traffic factor for the block
     */
    public double blockTrafficFactor(Block block) {
        // TODO: generate or assign a traffic factor for the block
        return 1.0;
    }

    /**
     * Computes the total traffic value on a block.
     *
     * @param block the block to evaluate
     * @return the traffic value for the block
     */
    public double blockTraffic(Block block) {
        // TODO: calculate traffic using the block's length and traffic factor
        return 0.0;
    }

    /**
     * Computes aggregate information for a path.
     *
     * @param path the list of blocks along the path
     * @return an array containing total length, average traffic factor, and total traffic
     */
    public double[] pathInformation(ArrayList<Block> path) {
        // TODO: compute total length, average experienced traffic, and total traffic
        return new double[] {0.0, 0.0, 0.0};
    }

    /**
     * Finds a path from the start intersection to the end intersection using the
     * fewest number of intersections/blocks along the route.
     *
     * @param start the starting intersection
     * @param end the destination intersection
     * @return the path with the minimum number of intersections, or an empty list if no path exists
     */
    public ArrayList<Block> minimizeIntersections(Intersection start, Intersection end) {
        // TODO: implement a shortest-path search over the intersection graph
        return new ArrayList<>();
    }

    /**
     * Finds the path from the start intersection to the end intersection with the
     * lowest total traffic cost.
     *
     * @param start the starting intersection
     * @param end the destination intersection
     * @return the least-traffic path, or an empty list if no path exists
     */
    public ArrayList<Block> fastestPath(Intersection start, Intersection end) {
        // TODO: implement a weighted shortest-path algorithm using traffic as the cost
        return new ArrayList<>();
    }
}
