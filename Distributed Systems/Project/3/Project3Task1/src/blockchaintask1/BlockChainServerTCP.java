package blockchaintask1;

import com.google.gson.Gson;

import java.math.BigInteger;
import java.net.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public class BlockChainServerTCP {
    // create a result array with 1000 size
    static int[] result = new int[1000];

    public static void main(String args[]) {
        Socket clientSocket = null;

        // Create a BlockChain object and then adding the Genesis block to the chain.
        BlockChain blockChain = new BlockChain();

        // The Genesis block will be created with an empty string as the previous hash and a difficulty of 2.
        Block genesisBlock = new Block(0, blockChain.getTime(), "Genesis", 2);
        blockChain.addBlock(genesisBlock);

        // On start up, the routine will also establish the hashes per second instance member.
        blockChain.computeHashesPerSecond();

        // declare and initialize variables that will be used for the RequestMessage object
        int selection = 0;
        int size = 0;
        int diff = 0;
        int totalDiff = 0;
        int hps = 0;
        double totalHashes = 0;
        BigInteger recentNonce = BigInteger.valueOf(0);
        String chainHash = "";
        String isChainValid = "";
        long executionTime = 0;

        try {
            // show the announcement
            System.out.println("Blockchain server running");

            // Set a port number for the server to listen to
            int serverPort = 7777;

            // Create a new server socket
            ServerSocket listenSocket = new ServerSocket(serverPort);

            /*
             * Block waiting for a new connection request from a client.
             * When the request is received, "accept" it, and the rest
             * the tcp protocol handshake will then take place, making
             * the socket ready for reading and writing.
             */
            clientSocket = listenSocket.accept();
            // If we get here, then we are now connected to a client.

            // Set up "in" to read from the client socket
            Scanner in;
            in = new Scanner(clientSocket.getInputStream());

            // Set up "out" to write to the client socket
            PrintWriter out;
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));

            System.out.println("We have a visitor");
            // loop forever to accept the request and return the result to the client
            while (true) {
                if (in.hasNextLine()) {
                    // read the data from the client
                    String jsonString = in.nextLine();

                    // parse the JSON messages from the client
                    Gson g = new Gson();
                    RequestMessage requestMessage = g.fromJson(jsonString, RequestMessage.class);

                    selection = requestMessage.selection;

                    if (selection == 0) {
                        size = blockChain.getChainSize();
                        diff = blockChain.getLatestBlock().getDifficulty();
                        totalDiff = blockChain.getTotalDifficulty();
                        hps = blockChain.getHashesPerSecond();
                        totalHashes = blockChain.getTotalExpectedHashes();
                        recentNonce = blockChain.getLatestBlock().getNonce();
                        chainHash = blockChain.getChainHash();

                        // display Json response
                        System.out.println("Response : {\"selection\": " + selection +
                                ",\"size\": " + size +
                                ",\"chainHash\": \"" + chainHash +
                                "\",\"totalHashes: " + (int) totalHashes +
                                ",\"totalDiff\": " + totalDiff +
                                ",\"recentNonce\": " + recentNonce +
                                ",\"diff\": " + diff +
                                ",\"hps\": " + hps +
                                "}");
                        }

                    if (selection == 1) {
                        System.out.println("Adding a block");

                        // Get new block's index, timeStamp
                        Block newBlock = new Block(blockChain.getChainSize(), blockChain.getTime(), requestMessage.data, requestMessage.difficulty);

                        // Then add a block containing that transaction to the blockchain.
                        long start = System.currentTimeMillis();
                        blockChain.addBlock(newBlock);
                        long end = System.currentTimeMillis();

                        // set execution time required to add the block
                        executionTime = end - start;

                        // Display the time
                        System.out.printf("Setting response to Total execution time to add this block was %d milliseconds\n", end - start);

                        // display Json response
                        System.out.printf("...{\"selection\": %d,\"response\":\"Total execution time to add this block was %d milliseconds\n", selection, end - start);
                    }

                    if (selection == 2) {
                        System.out.println("Verifying entire chain");
                        long start = System.currentTimeMillis();
                        isChainValid = blockChain.isChainValid();
                        long end = System.currentTimeMillis();

                        if (!isChainValid.equals("TRUE")) {
                            System.out.println("Chain verification: FALSE");
                            System.out.println(isChainValid);
                        } else {
                            System.out.println("Chain verification: TRUE");
                        }

                        // Set execution time required to verify the chain
                        executionTime = end - start;

                        // Display the number of milliseconds it took for validation
                        System.out.printf("Total execution time to verify the chain was %d milliseconds\n", executionTime);
                        System.out.printf("Setting response to Total execution time to verify the chain was %d milliseconds\n", executionTime);
                    }

                    if (selection == 3) {
                        System.out.println("View the Blockchain");
                        System.out.println("Setting response to " + blockChain);

                        out.println(blockChain + "\nend");
                        out.flush();
                    }

                    if (selection == 4) {
                        System.out.println("Corrupt the Blockchain");

                        // Set new data for the specified block
                        blockChain.getBlock(requestMessage.blockID).setData(requestMessage.data);
                        System.out.printf("Block %d now holds %s\n", requestMessage.blockID, requestMessage.data);
                    }

                    if (selection == 5) {
                        System.out.println("Repairing the entire chain");
                        long start = System.currentTimeMillis();
                        blockChain.repairChain();
                        long end = System.currentTimeMillis();

                        // Set execution time required to repair the chain
                        executionTime = end - start;

                        // Display the number of milliseconds it took for repairing the chain.
                        System.out.printf("Setting response to Total execution time required to repair the chain was %d milliseconds\n", executionTime);
                    }

                    if (selection != 3) {
                        ResponseMessage responseMessage = new ResponseMessage(selection, size, diff, totalDiff, hps, totalHashes, recentNonce,
                                chainHash, isChainValid, blockChain, executionTime);

                        // send message to the client
                        out.println(responseMessage + "\nend");
                        out.flush();
                    }
                } else {
                    // continue to accept requests after previous connection is closed
                    clientSocket = listenSocket.accept();
                    in = new Scanner(clientSocket.getInputStream());
                    out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())));
                }
            }

            // Handle exceptions
        } catch (IOException e) {
            System.out.println("IO Exception:" + e.getMessage());

            // If quitting (typically by you sending quit signal) clean up sockets
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                // ignore exception on close
            }
        }
    }

}