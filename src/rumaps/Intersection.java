package rumaps;

import java.util.*;

/**
 * The Intersection class is used to represent a street intersection.
 * It contains the following attributes:
 * - coordinate - coordinate of the intersection
 * - blocks - list of intersecting blocks
 * 
 * @author Vian Miranda
 * @author Anna Lu
 */
public class Intersection {
    private Coordinate coordinate;
    private ArrayList<Block> blocks; // an intersecting street will have 2+ blocks, one for each side of the intersection
                                    // if there is only 1 block, then the street ends at the intersection

    public Intersection(Coordinate coordinate) {
        this.coordinate = coordinate;   
        blocks = new ArrayList<>();
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void addIntersectingBlock(Block block) {
        blocks.add(block);
    }

    public ArrayList<Block> getIntersectingBlocks() {
        return blocks;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((coordinate == null) ? 0 : coordinate.hashCode());
        result = prime * result + ((blocks == null) ? 0 : blocks.hashCode());
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
        Intersection other = (Intersection) obj;
        if (coordinate == null) {
            if (other.coordinate != null)
                return false;
        } else if (!coordinate.equals(other.coordinate))
            return false;
        if (blocks == null) {
            if (other.blocks != null)
                return false;
        } else if (!blocks.equals(other.blocks))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Intersection [coordinate=" + coordinate + ", blocks=" + blocks + "]";
    }
}
