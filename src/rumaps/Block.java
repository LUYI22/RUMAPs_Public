package rumaps;

import java.awt.geom.Path2D;
import java.util.*;

/**
 * The Block class is used to represent a street block in the map and stores information such as the start point, end point, length, traffic factor, traffic, street name, and block number.
 * It is also used to draw the block to the map GUI. It contains the following attributes:
 * - path - purely for GUI 
 * - coordinatePoints - coordinates of the block (inclusive of start and end points, which are coords of intersection A and B) 
 * - strokeSize - size of the stroke 
 * - intersecetionA - starting (can also be considered end) intersection of the block 
 * - intersecetionB - ending (can also be considered start) intersection of the block 
 * - length - length of the block 
 * - trafficFactor - traffic factor of the block 
 * - traffic - traffic of the block 
 * - streetName - name of the street block is on
 * - blockNumber - number of the block 
 * 
 * @author Vian Miranda
 * @author Anna Lu
 */
public class Block {
    private Path2D path;
    private ArrayList<Coordinate> coordinatePoints;
    private float strokeSize;

    private Intersection intersectionA;
    private Intersection intersectionB;

    private double length;
    private double trafficFactor;
    private double traffic;

    private String streetName;
    private int blockNumber;

    /**
     * Constructor for Block class
     * 
     * Must set strokeSize after creating a new Block object
     */
    public Block() {
        path = new Path2D.Double();
    }
    
    /**
     * Constructor for Block class
     * 
     * @param strokeSize - size of the stroke (don't have to set after creating object)
     */
    public Block(double strokeSize) {
        this();
        this.strokeSize = (float) strokeSize;
    }

    /* GUI attributes */
    public void startPoint(Coordinate coordinate) {
        coordinatePoints = new ArrayList<>();
        coordinatePoints.add(coordinate);
        path.moveTo(coordinate.getX(), coordinate.getY());
    }

    public void nextPoint(Coordinate coordinate) {
        coordinatePoints.add(coordinate);
        path.lineTo(coordinate.getX(), coordinate.getY());
    }

    public Path2D getPath() {
        return path;
    }

    public void setStrokeSize(double strokeSize) {
        this.strokeSize = (float) strokeSize;
    }

    public float getStrokeSize() {
        return strokeSize;
    }

    /* Measurable Attributes */
    public ArrayList<Coordinate> getCoordinatePoints() {
        return coordinatePoints;
    }

    public void setIntersectionA(Intersection intersectionA) {
        this.intersectionA = intersectionA;
    }

    public Intersection getIntersectionA() {
        return intersectionA;
    }

    public void setIntersectionB(Intersection intersectionB) {
        this.intersectionB = intersectionB;
    }

    public Intersection getIntersectionB() {
        return intersectionB;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getLength() {
        return length;
    }

    public void setTrafficFactor(double trafficFactor) {
        this.trafficFactor = trafficFactor;
    }

    public double getTrafficFactor() {
        return trafficFactor;
    }

    public void setTraffic(double traffic) {
        this.traffic = traffic;
    }

    public double getTraffic() {
        return traffic;
    }

    /* Nominal Attributes */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setBlockNumber(int blockNumber) {
        this.blockNumber = blockNumber;
    }

    public int getBlockNumber() {
        return blockNumber;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((path == null) ? 0 : path.hashCode());
        result = prime * result + ((coordinatePoints == null) ? 0 : coordinatePoints.hashCode());
        result = prime * result + Float.floatToIntBits(strokeSize);
        long temp;
        temp = Double.doubleToLongBits(length);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(trafficFactor);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(traffic);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((streetName == null) ? 0 : streetName.hashCode());
        result = prime * result + blockNumber;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Block other = (Block) obj;
        if (path == null) {
            if (other.path != null)
                return false;
        } else if (!path.equals(other.path))
            return false;
        if (coordinatePoints == null) {
            if (other.coordinatePoints != null)
                return false;
        } else if (!coordinatePoints.equals(other.coordinatePoints))
            return false;
        if (Float.floatToIntBits(strokeSize) != Float.floatToIntBits(other.strokeSize))
            return false;
        if (Double.doubleToLongBits(length) != Double.doubleToLongBits(other.length))
            return false;
        if (Double.doubleToLongBits(trafficFactor) != Double.doubleToLongBits(other.trafficFactor))
            return false;
        if (Double.doubleToLongBits(traffic) != Double.doubleToLongBits(other.traffic))
            return false;
        if (streetName == null) {
            if (other.streetName != null)
                return false;
        } else if (!streetName.equals(other.streetName))
            return false;
        if (blockNumber != other.blockNumber)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Block [path=" + path + ", coordinatePoints=" + coordinatePoints + ", strokeSize=" + strokeSize
                + ", intersectionA=" + intersectionA + ", intersectionB=" + intersectionB + ", length=" + length
                + ", trafficFactor=" + trafficFactor + ", traffic=" + traffic + ", streetName=" + streetName
                + ", blockNumber=" + blockNumber + "]";
    }
}
