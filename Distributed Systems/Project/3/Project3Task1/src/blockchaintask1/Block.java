// Source: https://www.andrew.cmu.edu/course/95-702/examples/javadoc/blockchaintask0/Block.html#getNonce()
package blockchaintask1;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

public class Block {
    private int index;
    private Timestamp timestamp;
    private String data;
    private String previousHash;
    private BigInteger nonce;
    private int difficulty;

    /**
     * the Block constructor.
     *
     * @param index      - This is the position within the chain. Genesis is at 0.
     * @param timestamp  - This is the time this block was added.
     * @param data       - This is the transaction to be included on the blockchain.
     * @param difficulty - This is the number of leftmost nibbles that need to be 0.
     */
    public Block(int index, Timestamp timestamp, String data, int difficulty) {
        this.index = index;
        this.timestamp = timestamp;
        this.data = data;
        this.previousHash = "";
        this.nonce = BigInteger.valueOf(0);
        this.difficulty = difficulty;
    }

    /**
     * Simple getter method
     *
     * @return index of block
     */
    public int getIndex() {
        return index;
    }

    /**
     * Simple getter method
     *
     * @return timestamp of this block
     */
    public Timestamp getTimestamp() {
        return timestamp;
    }

    /**
     * Simple getter method
     *
     * @return this block's transaction
     */
    public String getData() {
        return data;
    }

    /**
     * Simple getter method
     *
     * @return previous hash for this block.
     */
    public String getPreviousHash() {
        return previousHash;
    }

    /**
     * This method gets nonce for this block.
     * The nonce is a number that has been found to cause the hash of this block
     * to have the correct number of leading hexadecimal zeroes.
     *
     * @return a BigInteger representing the nonce for this block.
     */
    public BigInteger getNonce() {
        return nonce;
    }

    /**
     * Simple getter method
     *
     * @return difficulty from this block.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Simple setter method
     *
     * @param index - the index of this block in the chain
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Simple setter method
     *
     * @param timestamp - of when this block was created
     */
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Simple setter method
     *
     * @param data - represents the transaction held by this block
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Simple setter method
     *
     * @param previousHash - a hash pointer to this block's parent
     */
    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    /**
     * Simple setter method
     *
     * @param difficulty - determines how much work is required to produce a proper hash
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * This method gets finds a good hash for this block.
     * It increments the nonce until it produces a good hash.
     *
     * @return a String with a hash that has the appropriate number of leading hex zeroes.
     */
    public String proofOfWork() {
        String hash = calculateHash();
        String prezeroes = new String(new char[difficulty]).replace('\0', '0');
        while (!hash.substring(0, difficulty).equals(prezeroes)) {
            nonce = nonce.add(new BigInteger("1"));
            hash = calculateHash();
        }
        return hash;
    }

    /**
     * This method computes a hash of the concatenation of the index,
     * timestamp, data, previousHash, nonce, and difficulty.
     *
     * @return a String holding Hexadecimal characters
     */
    public String calculateHash() {
        String concat = index + timestamp.toString() + data + previousHash + nonce + difficulty;
        String hash = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(concat.getBytes(StandardCharsets.UTF_8));
            hash = bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }

    // Code from stack overflow
    // https://stackoverflow.com/questions/9655181/how-to-convert-a-byte-array-to-a-hex-string-in-java
    // Returns a hex string given an array of bytes
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * This method overrides Java's toString method.
     *
     * @return a JSON representation of all of this block's data
     */
    @Override
    public String toString() {
        return "{" +
                "\"index\": " + index + "," +
                "\"time stamp\": " + timestamp + "," +
                "\"Tx\": \"" + data + "\"," +
                "\"PrevHash\": \"" + previousHash + "\"," +
                "\"nonce\": " + nonce + "," +
                "\"difficulty\": " + difficulty +
                "}";
    }

}
