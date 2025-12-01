package rumaps;

import java.util.*;

/**
 * This class represents the information that can be attained from the Rutgers University Map.
 * 
 * The RUMaps class is responsible for initializing the network, streets, blocks, and intersections in the map.
 * 
 * It contains methods to calculate the length of a block, the traffic factor of a block, and the traffic on a block.
 * The class also contains methods to calculate the total traffic on a path, the path with the least number of intersections from the start to end block, 
 * and the path with the least traffic (fastest path) from the start to end block.
 * 
 * Provided is a Network object that contains all the streets and intersections in the map
 * 
 * @author Vian Miranda
 * @author Anna Lu
 */
public class RUMaps {
    private Network rutgers;

    /**
     * **DO NOT MODIFY THIS METHOD**
     * 
     * Constructor for the RUMaps class. Initializes the streets and intersections in the map.
     * For each block in every street, sets the block's length, traffic factor, and traffic value.
     * 
     * @param mapPanel The map panel to display the map
     * @param filename The name of the file containing the street information
     */
    public RUMaps(MapPanel mapPanel, String filename) {
        rutgers = new Network(mapPanel);
        initializeStreets(filename);
        initializeIntersections();

        for (Street street : rutgers.getStreets()) {
            for (Block block : street.getBlocks()) {
                block.setLength(blockLength(block));
                block.setTrafficFactor(blockTrafficFactor(block));
                block.setTraffic(blockTraffic(block));
            }
        }
    }

    /**
     * **DO NOT MODIFY THIS METHOD**
     * 
     * Overloaded constructor for testing.
     * 
     * @param filename The name of the file containing the street information
     */
    public RUMaps(String filename) {
        this(null, filename);
    }

    /**
     * Initializes the streets in the network by reading street and block information
     * from the specified input file.
     * 
     * 1. Opens the file using StdIn.setFile(filename);
     * 
     * 2. Reads the number of streets.
     * 
     * 3. For each street:
     *    a. Reads the street name, which may consist of multiple words.
     *    b. Creates a Street object with the street's name.
     *    c. Reads the number of blocks in the street.
     * 
     * 4. For each block within a street:
     *    a. Reads the block number.
     *    b. Reads the number of coordinate points and the stroke size for the block.
     *    - Creates a Block object, setting its stroke size.
     * 
     * 5. For each coordinate point in a block:
     *    a. Reads the x and y values.
     *    b. If it is the first point, sets it as the block's start point.
     *    c. Otherwise, adds it as the next point in the block.
     * 
     * 6. Adds the block to the street, using the block number as the key.
     * 
     * 7. Adds the street to the network.
     * 
     * The input file follows the format:
     * - First line: total number of streets
     * - For each street: street name's length, street name, number of blocks
     * - For each block: block number, number of points, stroke size
     * - For each point in the block: x and y coordinates
     * 
     * @param filename the file containing street and block information
     */
    public void initializeStreets(String filename) {
        // WRITE YOUR CODE HERE

        StdIn.setFile(filename);
        int numStreets = StdIn.readInt();
        for (int i = 0; i < numStreets; i++) {
            int streetNameLength = StdIn.readInt();
            String streetName = StdIn.readString();
            for (int j = 1; j < streetNameLength; j++) {
               streetName += " " + StdIn.readString();
            }
            Street street = new Street(streetName);
            int numBlocks = StdIn.readInt();
            for (int j = 0; j < numBlocks; j++) {
                int blockNumber = StdIn.readInt();
                int numPoints = StdIn.readInt();
                double strokeSize = StdIn.readDouble();
                Block block = new Block(strokeSize);
                // block.setStrokeSize(strokeSize);
                for (int k = 0; k < numPoints; k++) {
                    int x = StdIn.readInt();
                    int y = StdIn.readInt();
                    Coordinate coordinate = new Coordinate(x, y);
                    if (k == 0) {
                        block.startPoint(coordinate);
                    } else {
                        block.nextPoint(coordinate);
                    }
                }
                street.addBlock(block, blockNumber);
            }
            rutgers.addStreet(street);
        }   
    }

    /**
     * Initializes intersections in the network by identifying where blocks intersect.
     * Make sure to complete initializeStreets before this.
     * 
     * 1. Iterate through all streets and blocks.
     * 
     * 2. Check the endpoint coordinate (start and end of coordinatePoints ArrayList) of each block.
     * 
     * 3. If an intersection does not already exist at a coordinate, creates a new intersection at that coordinate.
     *    a. Adds this newly created intersection to the network.
     * 
     * 4. Adds the block to the list of other intersecting blocks at the intersection.
     * 
     * 5. Sets the intersection as the endpoint intersection of the block. 
     */
    public void initializeIntersections() {
        // WRITE YOUR CODE HERE

        // iterate through all streets
        // for each street, iterate through all blocks
        // check if the block start/end exists in the network coordinate:intersection hashmap
        // if it doesn't, initialize an intersection (for simplicity, we will allow intersections with one edge, intersections will be nodes)
        // add the block to the network coordinate:intersection hashmap
    
        //Iterate through all streets
        for (Street street : rutgers.getStreets()) {
            //Iterate through all blocks in each street
            for (Block block : street.getBlocks()) {
                //Check start point of the block
                Coordinate start = block.getCoordinatePoints().get(0);
                //check if intersection at this coordinate already exist
                if (!rutgers.containsIntersection(start)) {
                    rutgers.addIntersection(start, new Intersection(start));
                }
                Intersection startIntersection = rutgers.getIntersection(start);
                startIntersection.addIntersectingBlock(block);
                block.setIntersectionA(startIntersection);

                //Check end point of the block
                Coordinate end = block.getCoordinatePoints().get(block.getCoordinatePoints().size() - 1);
                if (!rutgers.containsIntersection(end)) {
                    rutgers.addIntersection(end, new Intersection(end));
                }
                Intersection endIntersection = rutgers.getIntersection(end);
                endIntersection.addIntersectingBlock(block);
                block.setIntersectionB(endIntersection);
            }
        }
    }

    /**
     * Calculates the Euclidean distance between two coordinates.
     * 
     * @param a The first coordinate
     * @param b The second coordinate
     * @return The Euclidean distance between the two coordinates
     */
    private double coordinateDistance(Coordinate a, Coordinate b) {
        // WRITE YOUR CODE HERE

        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Calculates the length of a block by summing the distances between consecutive points for all points in the block.
     * 
     * @param block The block whose length is being calculated
     * @return The total length of the block
     */
    public double blockLength(Block block) {
        // WRITE YOUR CODE HERE

        ArrayList<Coordinate> points = block.getCoordinatePoints();
        double totalLength = 0.0;
        for (int i = 1; i < points.size(); i++) {
            totalLength += coordinateDistance(points.get(i-1), points.get(i));
        }
        return totalLength;
    }
    

    /**
     * **DO NOT MODIFY THIS METHOD**
     * 
     * Calculates and returns a randomized traffic factor for the block based on a Gaussian distribution.
     * 
     * This method generates a random traffic factor to simulate varying traffic conditions for each block:
     * - < 1 for good (faster) conditions
     * - = 1 for normal conditions
     * - > 1 for bad (slower) conditions
     * 
     * The traffic factor is generated with a Gaussian distribution centered at 1, with a standard deviation of 0.2.
     * 
     * Constraints:
     * - The traffic factor is capped between a minimum of 0.5 and a maximum of 1.5 to avoid extreme values.
     * 
     * @param block The block for which the traffic factor is calculated
     * @return A randomized traffic factor for the block
     */
    public double blockTrafficFactor(Block block) {
        double rand = StdRandom.gaussian(1, 0.2);
        rand = Math.max(rand, 0.5);
        rand = Math.min(rand, 1.5);
        return rand;
    }


    /**
     * Calculates the traffic on a block by the product of its length and its traffic factor.
     * 
     * @param block The block for which traffic is being calculated
     * @return The calculated traffic value on the block
     */
    public double blockTraffic(Block block) {
        // WRITE YOUR CODE HERE
        
        return block.getTrafficFactor() * block.getLength();
    }

    /**
     * Calculates the total length, average experienced traffic factor, and total traffic for a given path of blocks.
     * 
     * Compute the average experienced traffic factor by dividing total traffic by total length.
     *  
     * @param path The list of blocks representing the path
     * @return A double array containing the total length, average experienced traffic factor, and total traffic of the path (in that order)
     */
    public double[] pathInformation(ArrayList<Block> path) {
        // WRITE YOUR CODE HERE

        double totalLength = 0.0;
        double totalTraffic = 0.0;
        for (Block block : path) {
            totalLength += block.getLength();
            totalTraffic += block.getTraffic();
        }
        return new double[] {totalLength, totalTraffic / totalLength, totalTraffic};
    }    

    /**
     * Finds and returns the path with the least number of intersections (nodes) from the start to the end intersection.
     * 
     * - If multiple paths with the same number of turns exist, any one of them can be returned.
     * - If no path exists, return an empty ArrayList.
     * - This graph is large. Find a way to eliminate searching through intersections that have already been visited.
     * 
     * @param start The starting intersection
     * @param end The destination intersection
     * @return The path with the least number of turns, or an empty ArrayList if no path exists
     */
    public ArrayList<Block> minimizeIntersections(Intersection start, Intersection end) {
        // WRITE YOUR CODE HERE

        // Store the path to each intersection
        HashMap<Intersection, ArrayList<Block>> paths = new HashMap<>(); 
        // Use a BFS to find the path with the least number of turns
        Queue<Intersection> bfs = new LinkedList<>();
        // Keep track of visited intersections
        HashSet<Intersection> visited = new HashSet<>();

        bfs.offer(start);
        paths.put(start, new ArrayList<>());

        while (!bfs.isEmpty()) {
            Intersection current = bfs.poll();
            visited.add(current);

            if (current.equals(end)) {
                return paths.get(current);
            }

            for (Block block : current.getIntersectingBlocks()) {
                // Determine the next intersection to visit
                Intersection next;
                if (current.equals(block.getIntersectionA())) {
                    next = block.getIntersectionB();
                } else {
                    next = block.getIntersectionA();
                }

                // If the intersection has already been visited, skip it. We already found the shortest path to next.
                if (visited.contains(next)) continue;
                // If the intersection has not been visited or the current path has fewer turns than the previous path, update the path.
                if (!paths.containsKey(next) || paths.get(next).size() > paths.get(current).size()) {
                    ArrayList<Block> path = new ArrayList<>(paths.get(current));
                    path.add(block);
                    paths.put(next, path);
                    bfs.offer(next);
                }
            }
        }

        return new ArrayList<>();
    } 


    /**
     * Finds the path with the least traffic from the start to the end intersection using a variant of Dijkstra's algorithm.
     * The traffic is calculated as the sum of traffic of the blocks along the path.
     * 
     * What is this variant of Dijkstra?
     * - This variant finds the path with the least cost (in this case, least traffic). This is just like what we learned in class!
     * - However, this variant stops as soon as it finds the goal node (end intersection) and does not continue to explore other paths.
     * - This variant guarantees the shortest path because it processes nodes in increasing order of their path cost and uses a consistent cost function.
     * - ** If you are interested in learning more, this variant is called Uniform Cost Search (UCS). ** You'll learn more about UCS in AI-related courses.
     * 
     * - If no path exists, return an empty ArrayList.
     * - This graph is large. Find a way to eliminate searching through intersections that have already been visited.
     * 
     * @param start The starting intersection
     * @param end The destination intersection
     * @return The path with the least traffic, or an empty ArrayList if no path exists
     */
    public ArrayList<Block> fastestPath(Intersection start, Intersection end) {
        // WRITE YOUR CODE HERE
        
        // Store the path to each intersection
        HashMap<Intersection, ArrayList<Block>> paths = new HashMap<>(); 
        // Use Dijkstra's algorithm to find the path with the least traffic
        PriorityQueue<Intersection> dijkstra = new PriorityQueue<>(new Comparator<>() {
            @Override
            public int compare(Intersection a, Intersection b) {
                return Double.compare(pathInformation(paths.get(a))[2], pathInformation(paths.get(b))[2]);
            }
        });
        
        dijkstra.offer(start);
        paths.put(start, new ArrayList<>());

        while (!dijkstra.isEmpty()) {
            Intersection current = dijkstra.poll();

            if (current.equals(end)) {
                return paths.get(current);
            }

            for (Block block : current.getIntersectingBlocks()) {
                // Determine the next intersection to visit
                Intersection next;
                if (current.equals(block.getIntersectionA())) {
                    next = block.getIntersectionB();
                } else {
                    next = block.getIntersectionA();
                }

                // If the intersection has not been visited or the current path is faster than the previous path, update the path.
                if (!paths.containsKey(next) || pathInformation(paths.get(next))[2] > pathInformation(paths.get(current))[2] + block.getTraffic()) {
                    ArrayList<Block> path = new ArrayList<>(paths.get(current));
                    path.add(block);
                    paths.put(next, path);
                    dijkstra.offer(next);
                }
            }
        }

        return new ArrayList<>();
    }
}
