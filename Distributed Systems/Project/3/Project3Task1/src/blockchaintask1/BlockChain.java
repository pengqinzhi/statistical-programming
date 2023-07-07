// Source: https://www.andrew.cmu.edu/course/95-702/examples/javadoc/blockchaintask0/BlockChain.html#computeHashesPerSecond()
package blockchaintask1;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;

/* This BlockChain has exactly three instance members - an ArrayList to hold Blocks,
 * and a chain hash to hold a SHA256 hash of the most recently added Block,
 * and an instance variable holding the approximate number of hashes per second on this computer.
 */
public class BlockChain {
    private ArrayList<Block> blockList;
    private String lastHash;
    private int hashesPerSec;

    /**
     * the BlockChain constructor creates an empty ArrayList for Block storage,
     * sets the chain hash to the empty string,
     * and sets hashes per second to 0.
     */
    public BlockChain() {
        blockList = new ArrayList<Block>();
        lastHash = "";
        hashesPerSec = 0;
    }

    /**
     * @return the chain hash.
     */
    public String getChainHash() {
        return lastHash;
    }

    /**
     * @return the current system time
     */
    public Timestamp getTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * @return a reference to the most recently added Block
     */
    public Block getLatestBlock() {
        return blockList.get(blockList.size() - 1);
    }

    /**
     * @return the size of the chain in blocks
     */
    public int getChainSize() {
        return blockList.size();
    }

    /**
     * This method computes exactly 2 million hashes and times how long that process takes.
     * It is run on start up and sets the instance variable hashesPerSecond.
     */
    public void computeHashesPerSecond() {
        try {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 2000000; i++) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");

                // It uses a simple string - "00000000" to hash.
                md.update("00000000".getBytes("UTF-8"));
                md.digest();
            }
            long end = System.currentTimeMillis();
            float time = (end - start) / 1000F;

            // hashes per second is approximated as (2 million / number of seconds).
            hashesPerSec = (int) (2000000 / time);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the instance variable approximating the number of hashes per second.
     */
    public int getHashesPerSecond() {
        return hashesPerSec;
    }


    /**
     * This method adds a new Block to the BlockChain.
     *
     * @param newBlock - is added to the BlockChain as the most recent block
     */
    public void addBlock(Block newBlock) {
        // If the chain has more blocks than zero, new block's previous hash must hold the hash of the most recently added block.
        if (blockList.size() > 0) {
            newBlock.setPreviousHash(lastHash);
        }

        // The hash of every block must have the requisite number of leftmost 0's defined by its difficulty.
        newBlock.proofOfWork();

        // Add the new block.
        blockList.add(newBlock);

        // After add new block, the lastHash will be updated
        lastHash = newBlock.calculateHash();
    }

    /**
     * @param i - position
     * @return block at position i
     */
    public Block getBlock(int i) {
        return blockList.get(i);
    }

    /**
     * This method computes and return the total difficulty of all blocks on the chain.
     *
     * @return totalDifficulty
     */
    public int getTotalDifficulty() {
        int totalDifficulty = 0;
        for (Block block : blockList) {
            totalDifficulty += block.getDifficulty();
        }
        return totalDifficulty;
    }

    /**
     * This method computes and return the expected number of hashes required for the entire chain.
     *
     * @return totalExpectedHashes
     */
    public double getTotalExpectedHashes() {
        int totalExpectedHashes = 0;
        for (Block block : blockList) {
            totalExpectedHashes += Math.pow(16, block.getDifficulty());
        }
        return totalExpectedHashes;
    }

    /**
     * This method checks the chain.
     *
     * @return "TRUE" if the chain is valid, otherwise return a string with an appropriate error message
     */
    public String isChainValid() {
        // Begin checking from block zero. Continue checking until validating the entire chain.
        for (int i = 0; i < blockList.size(); i++) {
            // First check whether a block’s hash has required number of leading zeroes.
            if (!blockList.get(i).calculateHash().equals(blockList.get(i).proofOfWork())) {
                return "Improper hash on node " + i + " Does not begin with "
                        + new String(new char[blockList.get(i).getDifficulty()]).replace('\0', '0');
            }

            // Then check whether a block's computed hash is equal to the next block's hash pointer (if not the last block)
            if (i != blockList.size() - 1) {
                if (!blockList.get(i).calculateHash().equals(blockList.get(i + 1).getPreviousHash())) {
                    return "Computed hash on node " + i + " is not equal to the hash pointer on node " + i + 1;
                }
            } else {
                // At the end, check that the chain hash is equal to this computed hash.
                if (!lastHash.equals(blockList.get(i).calculateHash())) {
                    return "Computed hash on node " + i + " is not equal to the chain hash";
                }
            }
        }

        // If all checks succeed, return "TRUE"
        return "TRUE";
    }

    /**
     * This method repairs the chain.
     * It checks the hashes of each block and ensures that any illegal hashes are recomputed.
     * After this method is run, the chain will be valid.
     * The method does not modify any difficulty values.
     * It computes new proof of work based on the difficulty specified in the Block.
     */
    public void repairChain() {
        for (int i = 0; i < blockList.size(); i++) {
            // First check whether a block’s hash has required number of leading zeroes.
            if (!blockList.get(i).calculateHash().equals(blockList.get(i).proofOfWork())) {
                blockList.get(i).proofOfWork();
            }

            // Then check whether a block's computed hash is equal to the next block's hash pointer (if not the last block)
            if (i != blockList.size() - 1) {
                if (!blockList.get(i).calculateHash().equals(blockList.get(i + 1).getPreviousHash())) {
                    blockList.get(i + 1).setPreviousHash(blockList.get(i).calculateHash());
                }
            } else {
                // check that the chain hash is equal to this computed hash.
                if (!lastHash.equals(blockList.get(i).calculateHash())) {
                    lastHash = blockList.get(i).calculateHash();
                }
            }
        }
    }

    /**
     * This method uses the toString method defined on each individual block.
     *
     * @return a String representation of the entire chain is returned.
     */
    @Override
    public String toString() {
        StringBuilder JsonString = new StringBuilder("{\"ds_chain\": [ ");
        for (Block block : blockList) {
            if (!block.equals(getLatestBlock())) {
                JsonString.append(block).append(",\n");
            } else {
                JsonString.append(block);
            }
        }
        JsonString.append("\n ],");
        JsonString.append("chainHash: \"");
        JsonString.append(lastHash);
        JsonString.append("\"}");
        return JsonString.toString();
    }

}
