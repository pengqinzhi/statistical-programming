package blockchaintask1;

import java.math.BigInteger;
import java.util.ArrayList;

public class ResponseMessage {
    int selection;
    int size;
    int diff;
    int totalDiff;
    int hps;
    double totalHashes;
    BigInteger recentNonce;
    String chainHash;
    String isChainValid;
    long executionTime;

    /**
     * the ResponseMessage constructor.
     *
     * @param selection        - the client's selection for the menu
     * @param size             - current size of chain
     * @param diff             - difficulty of most recent block
     * @param totalDiff        - total difficulty for all blocks
     * @param hps              - approximate hashes per second on this machine
     * @param totalHashes      - expected total hashes required for the whole chain
     * @param recentNonce      - nonce for most recent block
     * @param chainHash        - chain hash i.e. the hash of the last block
     * @param isChainValid     - a string returned from Chain verification
     * @param executionTime    - total execution time to execute a specified routine
     */
    public ResponseMessage(int selection, int size, int diff, int totalDiff,
                           int hps, double totalHashes, BigInteger recentNonce,
                           String chainHash, String isChainValid, BlockChain blockChain, long executionTime) {
        this.selection = selection;
        this.size = size;
        this.diff = diff;
        this.totalDiff = totalDiff;
        this.hps = hps;
        this.totalHashes = totalHashes;
        this.recentNonce = recentNonce;
        this.chainHash = chainHash;
        this.isChainValid = isChainValid;
        this.executionTime = executionTime;
    }

    @Override
    public String toString() {
        return "{\"selection\": " + selection +
                ",\"size\": " + size +
                ",\"chainHash\": \"" + chainHash +
                "\",\"totalHashes\": " + totalHashes +
                ",\"totalDiff\": " + totalDiff +
                ",\"recentNonce\": " + recentNonce +
                ",\"diff\": " + diff +
                ",\"hps\": " + hps +
                ",\"isChainValid\": \"" + isChainValid +
                "\",\"executionTime\": " + executionTime +
                "}";
    }

}
