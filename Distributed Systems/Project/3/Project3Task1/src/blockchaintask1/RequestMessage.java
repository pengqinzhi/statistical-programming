package blockchaintask1;

public class RequestMessage {
    int selection;
    int difficulty;
    int blockID;
    String data;

    /**
     * the RequestMessage constructor.
     *
     * @param selection  - the client's selection for the menu
     * @param difficulty - from the client's input
     * @param blockID    - the block index
     * @param data       - the transaction to be included on the blockchain from the client's input.
     */
    public RequestMessage(int selection, int difficulty, int blockID, String data) {
        this.selection = selection;
        this.difficulty = difficulty;
        this.blockID = blockID;
        this.data = data;
    }

    @Override
    public String toString() {
        return "{\"selection\" : " + selection +
                ",\"difficulty\" : " + difficulty +
                ",\"blockID\" : " + blockID +
                ",\"data\" : \"" + data +
                "\"}";
    }

}
