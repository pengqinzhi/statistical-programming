// Source: https://www.andrew.cmu.edu/course/95-702/examples/javadoc/blockchaintask0/BlockChain.html#computeHashesPerSecond()
package blockchaintask0;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

/* This BlockChain has exactly three instance members - an ArrayList to hold Blocks,
 * and a chain hash to hold a SHA256 hash of the most recently added Block,
 * and an instance variable holding the approximate number of hashes per second on this computer.
 */
public class BlockChain {
    private ArrayList<Block> blockList;
    private String lastHash;
    private int hashesPerSec;

    /**
     * This routine acts as a test driver for your Blockchain.
     * Finding: As the difficulty increases, typically the system needs more time to execute.
     * When the difficulty is bigger than 5, all execution time will increase dramatically.
     * To be specific, when the difficulty is 2, the total execution time to add a block was 5 milliseconds, the time to verify was 1 milliseconds, the time to repair was 7 milliseconds;
     * when the difficulty is 3, the total execution time to add a block was 67 milliseconds, the time to verify was 21 milliseconds, the time to repair was 166 milliseconds;
     * when the difficulty is 4, the total execution time to add a block was 97 milliseconds, the time to verify was 197 milliseconds, the time to repair was 217 milliseconds;
     * when the difficulty is 5, the total execution time to add a block was 1830 milliseconds, the time to verify was 2946 milliseconds, the time to repair was 41609 milliseconds;
     * when the difficulty is 6, the total execution time to add a block was 78768 milliseconds, the time to verify was 25977 milliseconds, the time to repair was 52005 milliseconds;
     */
    public static void main(String[] args) {
        // It will begin by creating a BlockChain object and then adding the Genesis block to the chain.
        BlockChain blockChain = new BlockChain();

        // The Genesis block will be created with an empty string as the previous hash and a difficulty of 2.
        Block genesisBlock = new Block(0, blockChain.getTime(), "Genesis", 2);
        blockChain.addBlock(genesisBlock);

        // On start up, the routine will also establish the hashes per second instance member.
        blockChain.computeHashesPerSecond();

        // Set the menu, and it will continuously provide the user with seven options.
        Scanner input = new Scanner(System.in);
        System.out.println("Block Chain Menu");
        while (true) {
            System.out.println("0. View basic blockchain status.");
            System.out.println("1. Add a transaction to the blockchain.");
            System.out.println("2. Verify the blockchain.");
            System.out.println("3. View the blockchain.");
            System.out.println("4. corrupt the chain.");
            System.out.println("5. Hide the corruption by repairing the chain.");
            System.out.println("6. Exit");
            int selection = input.nextInt();
            input.nextLine();

            // If the user selects option 0, the program will display:
            // The number of blocks on the chain
            // Difficulty of most recent block.
            // The total difficulty for all blocks Approximate hashes per second on this machine.
            // Expected total hashes required for the whole chain.
            // The computed nonce for most recent block.
            // The chain hash (hash of the most recent block).
            if (selection == 0) {
                System.out.println("Current size of chain: " + blockChain.getChainSize());
                System.out.println("Difficulty of most recent block: " + blockChain.getLatestBlock().getDifficulty());
                System.out.println("Total difficulty for all blocks: " + blockChain.getTotalDifficulty());
                System.out.println("Approximate hashes per second on this machine: " + blockChain.getHashesPerSecond());
                System.out.println("Expected total hashes required for the whole chain: " + blockChain.getTotalExpectedHashes());
                System.out.println("Nonce for most recent block: " + blockChain.getLatestBlock().getNonce());
                System.out.println("Chain hash: " + blockChain.getChainHash());
            }

            // If the user selects option 1, the program will add a transaction to the blockchain.
            if (selection == 1) {
                // the program will prompt for and then read the difficulty level for this bloc
                System.out.println("Enter difficulty (> 0):");
                int newDifficulty = input.nextInt();
                input.nextLine();

                // It will then prompt for and then read a line of data from the user (representing a transaction).
                System.out.println("Enter transaction:");
                String newData = input.nextLine();

                // Get new block's index, timeStamp
                int newIndex = blockChain.getChainSize();
                Timestamp newTimeStamp = blockChain.getTime();
                Block newBlock = new Block(newIndex, newTimeStamp, newData, newDifficulty);

                // Then add a block containing that transaction to the blockchain.
                long start = System.currentTimeMillis();
                blockChain.addBlock(newBlock);
                long end = System.currentTimeMillis();

                // Display the time it took to add this block.
                System.out.printf("Total execution time to add this block was %d milliseconds\n", end - start);
            }

            // If the user selects option 2, then call the isChainValid method and display the results.
            if (selection == 2) {
                long start = System.currentTimeMillis();
                String isChainValid = blockChain.isChainValid();
                long end = System.currentTimeMillis();

                if (!isChainValid.equals("TRUE")) {
                    System.out.println("Chain verification: FALSE");
                    System.out.println(isChainValid);
                } else {
                    System.out.println("Chain verification: TRUE");
                }

                // Display the number of milliseconds it took for validation.
                System.out.printf("Total execution time to verify the chain was %d milliseconds\n", end - start);
            }

            // If the user selects option 3, then display the entire Blockchain contents as a correctly formed JSON document.
            if (selection == 3) {
                System.out.println("View the Blockchain");
                System.out.println(blockChain);
            }

            // If the user selects option 4, then corrupt the chain.
            if (selection == 4) {
                System.out.println("Corrupt the Blockchain");

                // Ask for the block index
                System.out.println("Enter block ID of block to corrupt:");
                int blockID = input.nextInt();
                input.nextLine();

                // Ask for the new data that will be placed in the block.
                System.out.println("Enter new data for block " + blockID);
                String newData = input.nextLine();

                // Set new data for the specified block
                blockChain.getBlock(blockID).setData(newData);
                System.out.printf("Block %d now holds %s\n", blockID, newData);
            }

            // If the user selects 5, then repair the chain.
            if (selection == 5) {
                long start = System.currentTimeMillis();
                blockChain.repairChain();
                long end = System.currentTimeMillis();

                // Display the number of milliseconds it took for repairing the chain.
                System.out.printf("Total execution time required to repair the chain was %d milliseconds\n", end - start);
            }

            // If the user selects 6, then exit the program.
            if (selection == 6) {
                System.exit(0);
            }
        }
    }

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
        int totalDifficulty = 0;
        for (Block block : blockList) {
            totalDifficulty += Math.pow(16, block.getDifficulty());
        }
        return totalDifficulty;
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
