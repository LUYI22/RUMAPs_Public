package rumaps;

import java.util.*;

/**
 * The Street class is used to represent a street in the map.
 * It contains the following attributes:
 * - blockNumbers - list of block numbers with corresponding blocks
 * - blockList - list of blocks (the blocks that make up the street)
 * - name - name of the street
 * - numBlocks - number of blocks in the street
 * 
 * @author Vian Miranda
 * @author Anna Lu
 */
public class Street {
    // Parallel ArrayLists (index i in blockNumbers list corresponds (or maps) to index i in the blockList list)
    // This can be represented in a more efficient manner. Any ideas?
    private ArrayList<Integer> blockNumbers;
    private ArrayList<Block> blockList;

    private String name;
    private int numBlocks;

    public Street(String name) {
        blockNumbers = new ArrayList<>();
        blockList = new ArrayList<>();
        this.name = name;
        numBlocks = 0;
    }

    public void addBlock(Block block, int blockNumber) {
        if (!blockNumbers.contains(blockNumber)) {
            numBlocks++;
        }
        blockNumbers.add(blockNumber);
        blockList.add(block);

        block.setStreetName(name);
        block.setBlockNumber(blockNumber);
    }

    public Block getBlock(int blockNumber) {
        return blockList.get(blockNumbers.indexOf(blockNumber));
    }

    public ArrayList<Block> getBlocks() {
        return blockList;
    }

    public String getName() {
        return name;
    }

    public int getNumBlocks() {
        return numBlocks;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((blockNumbers == null) ? 0 : blockNumbers.hashCode());
        result = prime * result + ((blockList == null) ? 0 : blockList.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + numBlocks;
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
        Street other = (Street) obj;
        if (blockNumbers == null) {
            if (other.blockNumbers != null)
                return false;
        } else if (!blockNumbers.equals(other.blockNumbers))
            return false;
        if (blockList == null) {
            if (other.blockList != null)
                return false;
        } else if (!blockList.equals(other.blockList))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (numBlocks != other.numBlocks)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Street [blockNumbers=" + blockNumbers + ", blockList=" + blockList + ", name=" + name + ", numBlocks="
                + numBlocks + "]";
    }
}
